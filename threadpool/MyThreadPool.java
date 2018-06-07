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
 * 1.0	     jwyuan    2016/1/2610:07	  Create	
 */
package com.iflytek.vie.threadpool;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.iflytek.vie.threadpool.callback.ProcessCallBack;
import com.iflytek.vie.threadpool.task.RunProcess;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by jwyuan on 2016/1/26.
 */
public class MyThreadPool {
    /**
     * 线程池名称
     */
    private String poolName = "platform-dic";
    /**
     * 线程池大小
     */
    private int queueSize = 5;
    /**
     * 队列大小
     */
    private int poolSize = 3;
    /**
     * 线程池服务
     */
    private ListeningExecutorService executorService = null;
    /**
     * 是否守护线程
     */
    private boolean isDaemon = true;

    /**
     * 构造器
     */
    public MyThreadPool() {
        executorService = newExecutor();
    }

    /**
     * 构造器
     * @param poolName 线程池名称
     * @param poolSize 线程池大小
     * @param queueSize 队列大小
     */
    public MyThreadPool(String poolName, int poolSize, int queueSize) {
        this.queueSize = queueSize;
        this.poolSize = poolSize;
        this.poolName = poolName;

        executorService = newExecutor();
    }

    /**
     * 新建线程池
     * @return 新建的线程池
     */
    private ListeningExecutorService newExecutor() {

        BlockingQueue<Runnable> queue;

        if (queueSize < 0) {
            queue = new LinkedTransferQueue<Runnable>();
        } else {
            queue = new SizeBlockingQueue(new LinkedTransferQueue<Runnable>(), queueSize);
        }

        ExecutorService executorService = new MyThreadPoolExecutor(poolSize, poolSize, 0,
                TimeUnit.MILLISECONDS, queue, new MyThreadFactory(poolName));

        return MoreExecutors.listeningDecorator(executorService);

    }

    /**
     * 执行任务
     * @param runProcess 任务
     */
    public void submitTask(RunProcess runProcess) {
        executorService.submit(runProcess);
    }

    /**
     * 执行带有回调的任务
     * @param runProcess 任务
     * @param callback 回调函数
     */
    public void submitTask(RunProcess runProcess, ProcessCallBack callback) {
        ListenableFuture listenableFuture = executorService.submit(runProcess);
        Futures.addCallback(listenableFuture, callback);
    }

    public boolean isDaemon() {
        return isDaemon;
    }

    public void setDaemon(boolean isDaemon) {
        this.isDaemon = isDaemon;
    }
}
