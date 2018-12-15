//package com.learn.sky.web.config;
//
//import org.apache.tomcat.util.threads.ThreadPoolExecutor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.context.request.async.CallableProcessingInterceptor;
//import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import java.util.concurrent.Callable;
//
///**
// * @Author: JiuBuKong
// * @Date: 2018/12/7 上午11:42
// */
//@Configuration
//
//public class CustomAsyncPool extends WebMvcConfigurerAdapter {
//
//    /**
//     * 配置线程池
//     *
//     * @return
//     */
//
//    @Bean(name = "asyncPoolTaskExecutor")
//
//    public ThreadPoolTaskExecutor getAsyncThreadPoolTaskExecutor() {
//
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//
//        taskExecutor.setCorePoolSize(20);
//
//        taskExecutor.setMaxPoolSize(40);
//
//        taskExecutor.setQueueCapacity(25);
//
//        taskExecutor.setKeepAliveSeconds(60);
//
//        taskExecutor.setThreadNamePrefix("callable-");
//
//        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为前者
//
//        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
//
//        taskExecutor.initialize();
//
//        return taskExecutor;
//
//    }
//
//    @Override
//
//    public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
//
//        //处理 callable超时
//
//        configurer.setDefaultTimeout(30 * 1000);
//
//        configurer.registerCallableInterceptors(new CallableProcessingInterceptor() {
//
//            @Override
//            public <T> void beforeConcurrentHandling(NativeWebRequest request, Callable<T> task) throws Exception {
//
//            }
//
//            @Override
//            public <T> void preProcess(NativeWebRequest request, Callable<T> task) throws Exception {
//
//            }
//
//            @Override
//            public <T> void postProcess(NativeWebRequest request, Callable<T> task, Object concurrentResult) throws Exception {
//
//            }
//
//            @Override
//            public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task) throws Exception {
//                return null;
//            }
//
//            @Override
//            public <T> void afterCompletion(NativeWebRequest request, Callable<T> task) throws Exception {
//
//            }
//        });
//
//        configurer.setTaskExecutor(getAsyncThreadPoolTaskExecutor());
//
//    }
//
//}
//
