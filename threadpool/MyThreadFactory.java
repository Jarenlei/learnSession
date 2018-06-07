/* 
 *
 * Copyright (C) 1999-2014 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：VIE60
 * 
 * Description：
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0	     jwyuan    2016/1/2610:06	  Create	
 */
package com.iflytek.vie.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jwyuan on 2016/1/26.
 */
public class MyThreadFactory implements ThreadFactory {
    /**
     * 组
     */
    final ThreadGroup group;
    /**
     * 序号
     */
    final AtomicInteger threadNumber = new AtomicInteger(1);
    /**
     * 名称前缀
     */
    final String namePrefix;
    /**
     * 是否守护线程
     */
    private boolean isDaemon;
    /**
     * 构造器
     * @param namePrefix 名称前缀
     */
    public MyThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }

    /**
     * 新建一个线程
     * @param r 线程
     * @return 线程
     */
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + "[T#" + threadNumber.getAndIncrement() + "]",
                0);
        t.setDaemon(isDaemon);
        return t;
    }

    public boolean isDaemon() {
        return isDaemon;
    }

    public void setDaemon(boolean isDaemon) {
        this.isDaemon = isDaemon;
    }
}
