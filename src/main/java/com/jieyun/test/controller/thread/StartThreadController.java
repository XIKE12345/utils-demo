package com.jieyun.test.controller.thread;

import com.jieyun.test.service.thread.DoSomething;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * @author
 * @desc SpringBoot 开启线程 实例
 */
@RestController
public class StartThreadController {

    private static final Logger logger = LoggerFactory.getLogger(StartThreadController.class);

    @Autowired
    private DoSomething hello;

    @GetMapping("/start")
    public String helloWorldController() {
        logger.info(Thread.currentThread().getName() + " 进入helloWorldController方法");
        String say = hello.sayHello();
        logger.info("请求完成");
        return say;
    }

    /**
     * 线程执行完后，将结果返回给Callable
     *
     * @return
     */
    @GetMapping("/hello1")
    public Callable<String> helloController() {
        logger.info(Thread.currentThread().getName() + " 进入helloController方法");
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info(Thread.currentThread().getName() + " 进入call方法");
                // 从这里写耗时任务的代码
                String say = hello.sayHello();
                //
                logger.info(Thread.currentThread().getName() + " 从helloService方法返回");
                return say;
            }
        };
        logger.info(Thread.currentThread().getName() + " 从helloController方法返回");
        return callable;
    }


    /**
     * 基本的开启线程的方法
     */
    @GetMapping("/start/base")
    public void helloController2() {
        ThreadTest threadTest = new ThreadTest();
        threadTest.start();
    }

    class ThreadTest extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                logger.error("tyuio");
            }
        }
    }


}
