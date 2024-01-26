package com.github.dongguabai.web.server.constant;

/**
 * @author dongguabai
 * @date 2024-01-26 15:41
 */
public class PropertiesConstant {

    public static final String SERVER_PORT = "server.port";

    public static final String SERVER_MAPPING_PREFFIX = "server.mapping.";

    public static final String SERVER_THREADPOOL_COREPOOLSIZE = "server.threadpool.corePoolSize";

    public static final String SERVER_THREADPOOL_MAXIMUMPOOLSIZE = "server.threadpool.maximumPoolSize";

    public static final String SERVER_THREADPOOL_KEEPALIVETIME = "server.threadpool.keepAliveTime";

    public static final String SERVER_THREADPOOL_QUEUECAPACITY = "server.threadpool.queueCapacity";

    public static final String SERVER_THREADPOOL_REJECTIONPOLICY = "server.threadpool.rejectionPolicy";

    public static final String REJECTION_POLICY_CALLER_RUNS = "CallerRunsPolicy";

    public static final String REJECTION_POLICY_ABORT = "AbortPolicy";

    public static final String REJECTION_POLICY_DISCARD_OLDEST = "DiscardOldestPolicy";

    public static final String REJECTION_POLICY_DISCARD = "DiscardPolicy";

    public static final int DEFAULT_PORT = 8888;

}