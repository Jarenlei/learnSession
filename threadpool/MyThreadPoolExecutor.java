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
 * 1.0	     jwyuan    2016/1/2610:15	  Create	
 */
package com.iflytek.vie.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jwyuan on 2016/1/26.
 */
public class MyThreadPoolExecutor extends ThreadPoolExecutor {
    /**
     * 构造器
     * @param corePoolSize 线程数
     * @param maximumPoolSize 最大
     * @param keepAliveTime 存活时间
     * @param unit 单位
     * @param workQueue 队列
     * @param threadFactory 线程池
     */
    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }
}
