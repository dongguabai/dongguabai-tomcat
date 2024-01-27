package com.github.dongguabai.server.config;

/**
 * @author dongguabai
 * @date 2024-01-26 15:41
 */
public class PropertiesConstant {

    public static class Config {

        public static final String SERVER_PORT = "server.port";

        public static final String SERVER_MAPPING_PREFFIX = "server.mapping.";

        public static final String SERVER_THREADPOOL_COREPOOLSIZE = "server.threadpool.corePoolSize";

        public static final String SERVER_THREADPOOL_MAXIMUMPOOLSIZE = "server.threadpool.maximumPoolSize";

        public static final String SERVER_THREADPOOL_KEEPALIVETIME = "server.threadpool.keepAliveTime";

        public static final String SERVER_THREADPOOL_QUEUECAPACITY = "server.threadpool.queueCapacity";

        public static final String SERVER_THREADPOOL_REJECTIONPOLICY = "server.threadpool.rejectionPolicy";

        public static final String SERVER_CONNECTORTYPE = "server.ConnectorType";

        public static final String SERVER_SOTIMEOUT = "server.soTimeout";

    }

    public static class ThreadPool {

        public static final String REJECTION_POLICY_CALLER_RUNS = "CallerRunsPolicy";

        public static final String REJECTION_POLICY_ABORT = "AbortPolicy";

        public static final String REJECTION_POLICY_DISCARD_OLDEST = "DiscardOldestPolicy";

        public static final String REJECTION_POLICY_DISCARD = "DiscardPolicy";

    }

    public static class ConnectorType {

        public static final String BIO = "bio";

        public static final String NIO = "nio";

    }

    public static class Default {

        public static final int DEFAULT_PORT = 80;

        public static final int DEFAULT_COREPOOLSIZE = 10;

        public static final int DEFAULT_MAXIMUMPOOLSIZE = 200;

        public static final int DEFAULT_KEEPALIVETIME = 60;

        public static final int DEFAULT_QUEUECAPACITY = 500;

        public static final int DEFAULT_SOTIMEOUT = 0;

        public static final String DEFAULT_REJECTIONPOLICY = ThreadPool.REJECTION_POLICY_CALLER_RUNS;

        public static final String DEFAULT_CONNECTORTYPE = ConnectorType.BIO;
    }

}