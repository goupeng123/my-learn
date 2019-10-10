package com.my.delay.queue;

import com.my.delay.dao.DelayTaskDao;
import com.my.delay.entity.DelayTaskEntity;
import com.my.delay.entity.model.ExecuteStatus;
import com.my.delay.utils.TaskThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by frank.gou on 2019/9/30.
 */
@Component
public class QueueConsumer {


    @Autowired
    private DelayTaskDao delayTaskDao;

    private DelayQueue<DelayMessage> queue;
    private Boolean signal;

    public QueueConsumer() {

        this.queue = new DelayQueue<DelayMessage>();
        signal = Boolean.TRUE;
    }

    public void addDelayTask(Long id, String msgBody, Long executeTime) {

        System.out.println("================ 添加任务 ===================");
        DelayTaskEntity delayTaskEntity = create(id, msgBody, executeTime);
        delayTaskDao.insertSelective(delayTaskEntity);
        DelayMessage delayMessage = new DelayMessage(id, msgBody, executeTime);
        queue.offer(delayMessage);
        System.out.println("===============目前任务列表中有：" + queue.size() + " 个任务待执行===============");
    }

    public void finish() {

        this.signal = Boolean.FALSE;
    }


    @PostConstruct
    public void init() {

        System.out.println("================ 队列已经启动 =================");
        ExecutorService exec = Executors.newFixedThreadPool(1);

        exec.execute(new Runnable() {
            @Override
            public void run() {

                while (signal) {

                    try {

                        DelayMessage take = queue.take();
                        exec(take);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // 加载mysql中的延时任务
        List<DelayTaskEntity> delayTaskEntities = delayTaskDao.listByExecuteTimeAndExecuteStatus(new Date(), ExecuteStatus.NOT_EXECUTE.getValue());
        if (!CollectionUtils.isEmpty(delayTaskEntities)) {

            for (DelayTaskEntity delayTaskEntity : delayTaskEntities) {

                Long executeNum = delayTaskEntity.getExecuteTime().getTime() - new Date().getTime();
                DelayMessage delayMessage = new DelayMessage(delayTaskEntity.getTaskId(), delayTaskEntity.getMsgBody(), executeNum);
                queue.offer(delayMessage);
            }
            System.out.println("加载延时任务完毕");
        }
    }


    @PreDestroy
    public void destory() {

        finish();
    }


    private DelayTaskEntity create(Long id, String msgBody, Long executeTime) {

        DelayTaskEntity delayTaskEntity = new DelayTaskEntity();
        delayTaskEntity.setTaskId(id);
        delayTaskEntity.setMsgBody(msgBody);
        Date time = new Date(System.currentTimeMillis() + executeTime);
        delayTaskEntity.setExecuteTime(time);
        delayTaskEntity.setExecuteNum(executeTime);
        delayTaskEntity.setExecuteStatus(ExecuteStatus.NOT_EXECUTE.getValue());
        return delayTaskEntity;
    }

    private void exec(DelayMessage take) {

        TaskThread.tasks.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("消费消息id：" + take.getId() + " 消息体：" + take.getMsgBody() + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                delayTaskDao.updateStatusBytaskId(ExecuteStatus.HAS_EXECUTE.getValue(), take.getId());
            }
        });
    }
}
