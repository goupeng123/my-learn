package com.my.delay.queue;

import com.my.delay.dao.DelayTaskDao;
import com.my.delay.entity.DelayTaskEntity;
import com.my.delay.entity.model.ExecuteStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
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

    public QueueConsumer() {

        this.queue = new DelayQueue<DelayMessage>();
    }

    public void addDelayTask(Long id, String msgBody, Long executeTime) {

        System.out.println("================ 添加任务 ===================");
        DelayMessage delayMessage = new DelayMessage(id, msgBody, executeTime);
        queue.offer(delayMessage);
        System.out.println("===============目前任务列表中有：" + queue.size() + " 个任务待执行===============");
    }

    @PostConstruct
    public void init() {

        System.out.println("================ 队列已经启动 =================");
        ExecutorService exec = Executors.newFixedThreadPool(1);

        exec.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {

                        DelayMessage take = queue.take();
                        System.out.println("消费消息id：" + take.getId() + " 消息体：" + take.getMsgBody() + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
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

                DelayMessage delayMessage = new DelayMessage(delayTaskEntity.getId(), delayTaskEntity.getMsgBody(), delayTaskEntity.getExecuteNum());
                queue.offer(delayMessage);
                System.out.println("加载延时任务完毕");
            }
        }
    }
}
