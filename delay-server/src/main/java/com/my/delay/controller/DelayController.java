package com.my.delay.controller;

import com.my.delay.queue.QueueConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by frank.gou on 2019/9/30.
 */
@RestController
@RequestMapping("delay")
public class DelayController {

    @Autowired
    private QueueConsumer queueConsumer;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 添加延时任务
     *
     * @param id
     * @param msgBody
     * @param executeTime 毫秒值
     * @return
     */
    @RequestMapping(value = "add")
    public Object add(Long id, String msgBody, Long executeTime) {

        queueConsumer.addDelayTask(id, msgBody, executeTime);
        return "ok";
    }

    /**
     * 测试延时任务服务任务数量
     *
     * @return
     */
    @RequestMapping(value = "test")
    public Object test() {

        Integer taskId = 10024;
        for (int i = 0; i < 5000; i++) {

            taskId = taskId + i;
            String msgBody = "测试延时任务 " + (i + 1) * 10 + " 秒后执行...";
            Long executeTime = Long.parseLong(((i + 1) * 10 * 1000) + "");
            restTemplate.getForObject("http://132.232.2.41:8090/delay/add?id=" + taskId + "&msgBody=" + msgBody + "&executeTime=" + executeTime, String.class);
        }
        return "ok";
    }
}
