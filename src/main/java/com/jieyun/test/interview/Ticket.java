package com.jieyun.test.interview;

public class Ticket implements Runnable {

    private static int ticket = 10;

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            try {
                Thread.sleep(500); //线程休眠500毫秒，以便观察输出
            } catch (InterruptedException e) { //需要处理异常
                e.printStackTrace();
            }
            synchronized (this) { //同步代码块+对象锁（this表示对象锁）
                if (ticket <= 0) {
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " 买了第" + ticket + "张票");
                ticket--;
            }
        }
    }

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "3号窗口").start();

    }
}
