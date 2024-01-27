package com.github.dongguabai.web.server.threadpool;

import com.github.dongguabai.web.server.conf.PropertiesLoader;
import com.github.dongguabai.web.server.constant.PropertiesConstant;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dongguabai
 * @date 2024-01-26 22:19
 */
public class ThreadPoolManager {

    private ExecutorService executorService;

    ThreadFactory namedThreadFactory = new ThreadFactory() {
        private final AtomicInteger poolNumber = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "dongguabai-server-" + poolNumber.getAndIncrement());
        }
    };

    public ThreadPoolManager(PropertiesLoader propertiesLoader) {
        // 获取线程池的配置
        int corePoolSize = propertiesLoader.getThreadPoolCorePoolSize();
        corePoolSize = corePoolSize == -1 ? 10 : corePoolSize;
        int maximumPoolSize = propertiesLoader.getThreadPoolMaximumPoolSize();
        maximumPoolSize = maximumPoolSize == -1 ? 100 : maximumPoolSize;
        long keepAliveTime = propertiesLoader.getThreadPoolKeepAliveTime();
        keepAliveTime = keepAliveTime == -1 ? 60 : keepAliveTime;
        int queueCapacity = propertiesLoader.getThreadPoolQueueCapacity();
        queueCapacity = queueCapacity == -1 ? 500 : queueCapacity;
        String rejectionPolicy = propertiesLoader.getThreadPoolRejectionPolicy();
        RejectedExecutionHandler handler;
        switch (rejectionPolicy) {
            case PropertiesConstant.REJECTION_POLICY_CALLER_RUNS:
                handler = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
            case PropertiesConstant.REJECTION_POLICY_ABORT:
                handler = new ThreadPoolExecutor.AbortPolicy();
                break;
            case PropertiesConstant.REJECTION_POLICY_DISCARD_OLDEST:
                handler = new ThreadPoolExecutor.DiscardOldestPolicy();
                break;
            case PropertiesConstant.REJECTION_POLICY_DISCARD:
                handler = new ThreadPoolExecutor.DiscardPolicy();
                break;
            default:
                handler = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
        }
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(queueCapacity);
        executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue, namedThreadFactory, handler);
    }

    public void execute(Runnable task) {
        executorService.execute(task);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}