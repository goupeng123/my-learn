package com.my.delay.dao.mapper;

import com.my.delay.entity.DelayTaskEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by frank.gou on 2019/9/30.
 */
public interface DelayTaskMapper {

    List<DelayTaskEntity> listByExecuteTimeAndExecuteStatus(@Param("executeTime") Date executeTime,
                                                            @Param("executeStatus") Byte executeStatus);

    int insertSelective(DelayTaskEntity delayTaskEntity);

    int updateStatusBytaskId(@Param("executeStatus") Byte executeStatus, @Param("taskId") Long taskId);
}
