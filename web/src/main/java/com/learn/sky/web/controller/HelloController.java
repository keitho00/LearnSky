package com.learn.sky.web.controller;

import com.learn.sky.web.validation.LongTimeTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

/**
 * @Author: JiuBuKong
 * @Date: 2018/12/6 下午5:21
 */
@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Resource
    private LongTimeTask longTimeTask;

    @GetMapping("/helloworld")
    public String helloWorldController() {
        logger.info(Thread.currentThread().getName() + " 进入helloWorld方法");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info(Thread.currentThread().getName() + " 从helloWorld方法返回");
        return "helloworld success";
    }

    /**
     * 异步调用restful
     * 当controller返回值是Callable的时候，springmvc就会启动一个线程将Callable交给TaskExecutor去处理
     * 然后DispatcherServlet还有所有的spring拦截器都退出主线程，然后把response保持打开的状态
     * 当Callable执行结束之后，springmvc就会重新启动分配一个request请求，然后DispatcherServlet就重新
     * 调用和处理Callable异步执行的返回结果， 然后返回视图
     * 默认有多少个链接数就建立多少个线程
     * @return
     */
    @GetMapping("/hello")
    public Callable<String> helloController() {
        logger.info(Thread.currentThread().getName() + " 进入helloController方法");
        Callable<String> callable = new Callable<String>() {

            @Override
            public String call() throws Exception {
                logger.info(Thread.currentThread().getName() + " 进入call方法");
                Thread.sleep(5000);
                logger.info(Thread.currentThread().getName() + " 从helloService方法返回");
                return "hello success";
            }
        };
        logger.info(Thread.currentThread().getName() + " 从helloController方法返回");
        return callable;
    }

    /**
     * 带超时时间的异步请求 通过WebAsyncTask自定义客户端超时间
     *
     * @return
     */
    @GetMapping("/world")
    public WebAsyncTask<String> worldController() {
        logger.info(Thread.currentThread().getName() + " 进入helloController方法");

        // 3s钟没返回，则认为超时
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(3000, new Callable<String>() {

            @Override
            public String call() throws Exception {
                logger.info(Thread.currentThread().getName() + " 进入call方法");
                Thread.sleep(4000);
                logger.info(Thread.currentThread().getName() + " 从helloService方法返回");
                return "world success";
            }
        });
        logger.info(Thread.currentThread().getName() + " 从helloController方法返回");

        webAsyncTask.onCompletion(new Runnable() {

            @Override
            public void run() {
                logger.info(Thread.currentThread().getName() + " 执行完毕");
            }
        });

        webAsyncTask.onTimeout(new Callable<String>() {

            @Override
            public String call() throws Exception {
                logger.info(Thread.currentThread().getName() + " onTimeout");
                // 超时的时候，直接抛异常，让外层统一处理超时异常
                throw new TimeoutException("调用超时");
            }
        });
        return webAsyncTask;
    }

    /**
     * 异步调用，异常处理，详细的处理流程见MyExceptionHandler类
     *
     * @return
     */
    @GetMapping("/exception")
    public WebAsyncTask<String> exceptionController() {
        logger.info(Thread.currentThread().getName() + " 进入helloController方法");
        Callable<String> callable = new Callable<String>() {

            @Override
            public String call() throws Exception {
                logger.info(Thread.currentThread().getName() + " 进入call方法");
                throw new TimeoutException("调用超时!");
            }
        };
        logger.info(Thread.currentThread().getName() + " 从helloController方法返回");
        return new WebAsyncTask<>(20000, callable);
    }

    @GetMapping("/deferred")
    public DeferredResult<String> executeSlowTask() {
        logger.info(Thread.currentThread().getName() + "进入executeSlowTask方法");
        DeferredResult<String> deferredResult = new DeferredResult<>();
        // 调用长时间执行任务
        longTimeTask.execute(deferredResult);
        // 当长时间任务中使用deferred.setResult("world");这个方法时，会从长时间任务中返回，继续controller里面的流程
        logger.info(Thread.currentThread().getName() + "从executeSlowTask方法返回");
        // 超时的回调方法
        deferredResult.onTimeout(new Runnable() {

            @Override
            public void run() {
                logger.info(Thread.currentThread().getName() + " onTimeout");
                // 返回超时信息
                deferredResult.setErrorResult("time out!");
            }
        });

        // 处理完成的回调方法，无论是超时还是处理成功，都会进入这个回调方法
        deferredResult.onCompletion(new Runnable() {

            @Override
            public void run() {
                logger.info(Thread.currentThread().getName() + " onCompletion");
            }
        });

        return deferredResult;
    }

}
