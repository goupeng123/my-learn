package com.my.delay.entity.model;

import com.my.delay.utils.EnumUtil;

import java.util.function.Function;

/**
 * Created by frank.gou on 2019/9/30.
 */
public enum ExecuteStatus {

    NOT_EXECUTE((byte) 0, "未执行"), HAS_EXECUTE((byte) 1, "已执行");

    private byte value;
    private String desc;

    ExecuteStatus(byte value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private static final Function<Byte, ExecuteStatus> function = EnumUtil.lookupMap(ExecuteStatus.class, e -> e.getValue());

    public static ExecuteStatus findByValue(byte value) {
        return function.apply(value);
    }

    public byte getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
