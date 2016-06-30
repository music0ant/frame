package com.ant.myhome.thread;

import com.ant.myhome.utils.Print;

public class WaitTest extends Print{
	 
    public static void main(String[] args) {
        ThreadB b = new ThreadB();
        b.start();
        synchronized (b) {
            try {
                System.out.println("Waiting for b to complete...");
                print("Thread status :"+b.getState());
                b.wait();
                print("Thread status :"+b.getState());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Total is: " + b.total);
        }
    }
}
 
class ThreadB extends Thread {
    int total;
     
    public void run() {
//        synchronized (this) {
            System.out.println("ThreadB");
            for (int i = 0; i < 50; i++) {
                total += i;
                try {
                    Thread.sleep(100);
                    System.out.print(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            notify();
            System.out.println();
        }
//    }
}