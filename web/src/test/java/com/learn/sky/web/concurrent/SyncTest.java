package com.learn.sky.web.concurrent;


/**
 * @Author: JiuBuKong
 * @Date: 2020/6/15 4:12 PM
 */
public class SyncTest {

    private static volatile boolean flag = true;
    private static Object object = new Object();


    public static void main(String[] args) throws InterruptedException {

        Thread a = new Thread(new waitFun(), "waitFun");
        a.start();
//        Thread.sleep(1000);
        System.out.println("=======");
        Thread b = new Thread(new notifyFun(), "notifyFun");
        b.start();
    }


    public static class waitFun implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread() + "waitFun start");
            synchronized (object) {
                while (flag) {
                    System.out.println(Thread.currentThread() + "waitFun wait");
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread() + "waitFun finish");
            }
        }
    }

    public static class notifyFun implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread() + "notifyFun start");
            synchronized (object) {
                flag = false;
                object.notifyAll();
                System.out.println(Thread.currentThread() + "notifyFun finish");
            }
        }
    }


}
