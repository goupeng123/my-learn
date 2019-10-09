package com.my.delay.controller;

import com.my.delay.queue.QueueConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by frank.gou on 2019/9/30.
 */
@RestController
@RequestMapping("delay")
public class DelayController {

    @Autowired
    private QueueConsumer queueConsumer;

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
}
