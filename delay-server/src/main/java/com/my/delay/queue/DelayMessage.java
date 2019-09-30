package com.my.delay.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by frank.gou on 2019/9/30.
 */
public class DelayMessage implements Delayed {

    private Long id;
    private String msgBody;
    private Long executeTime;

    public DelayMessage(Long id, String msgBody, Long expireTime) {

        this.id = id;
        this.msgBody = msgBody;
        this.executeTime = TimeUnit.NANOSECONDS.convert(expireTime, TimeUnit.MILLISECONDS) + System.nanoTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public Long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Long executeTime) {
        this.executeTime = executeTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {

        return unit.convert(this.executeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {

        DelayMessage msg = (DelayMessage) o;
        return this.executeTime > msg.executeTime ? 1 : -1;
    }
}
