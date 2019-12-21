package com.jieyun.test.service.thread;

import org.springframework.stereotype.Component;

/**
 * @author huike
 * @desc 开启线程后具体要做的事
 */
@Component
public class DoSomething {
    public String sayHello() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "事情做完啦！！！！";
    }
}
