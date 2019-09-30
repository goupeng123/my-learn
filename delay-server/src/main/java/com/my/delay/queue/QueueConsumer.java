package com.my.delay.queue;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by frank.gou on 2019/9/30.
 */
@Component
public class QueueConsumer {


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
    }
}
