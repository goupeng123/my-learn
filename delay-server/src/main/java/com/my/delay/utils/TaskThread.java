package com.my.delay.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by frank.gou on 2019/10/9.
 */
public class TaskThread {

    public static ExecutorService tasks = Executors.newFixedThreadPool(3);
}
