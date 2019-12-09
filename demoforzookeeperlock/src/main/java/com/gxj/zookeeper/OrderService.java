package com.gxj.zookeeper;

public class OrderService implements Runnable {

    private OrderNumGenerator orderNumGenerator = new OrderNumGenerator();
    private Extlock lock = new ZookeeperDistributeLock();


    public void run() {
      getNumber();
    }



    public void getNumber() {

        try {
            lock.getLock();
            String number = orderNumGenerator.getNumber();
            System.out.println(Thread.currentThread().getName()+",number"+number);

        }catch (Exception e){

        }
        finally{
            lock.unLock();
        }

    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(new OrderService()).start();
        }

    }
}
