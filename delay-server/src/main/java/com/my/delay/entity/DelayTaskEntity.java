package com.my.delay.entity;

import java.util.Date;

/**
 * Created by frank.gou on 2019/9/30.
 */
public class DelayTaskEntity {

    private Long id;
    private Long taskId;
    private String msgBody;
    private Date executeTime;
    private Long executeNum;
    private Byte executeStatus;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public Long getExecuteNum() {
        return executeNum;
    }

    public void setExecuteNum(Long executeNum) {
        this.executeNum = executeNum;
    }

    public Byte getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(Byte executeStatus) {
        this.executeStatus = executeStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
