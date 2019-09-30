package com.my.delay.dao;

import com.my.delay.dao.mapper.DelayTaskMapper;
import com.my.delay.entity.DelayTaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by frank.gou on 2019/9/30.
 */
@Repository
public class DelayTaskDao {

    @Autowired
    private DelayTaskMapper delayTaskMapper;

    public List<DelayTaskEntity> listByExecuteTimeAndExecuteStatus(Date executeTime, Byte executeStatus) {

        return delayTaskMapper.listByExecuteTimeAndExecuteStatus(executeTime, executeStatus);
    }
}
