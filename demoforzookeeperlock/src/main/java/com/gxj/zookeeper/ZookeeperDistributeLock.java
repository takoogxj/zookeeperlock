package com.gxj.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

public class ZookeeperDistributeLock extends ZookeeperAbstractLock {


    void waitLock() {

        IZkDataListener iZkDataListener  = new IZkDataListener() {

            //节点改变时触发
            public void handleDataChange(String s, Object o) throws Exception {

            }
            //节点删除时触发
            public void handleDataDeleted(String s) throws Exception {
                //参数自动减1，为0是不再阻塞线程
                countDownLatch.countDown();
            }
        };

        //监听事件通知
        zkClient.subscribeDataChanges(lockPath, iZkDataListener);

        if(zkClient.exists(lockPath)){
            countDownLatch = new CountDownLatch(1);//参数设1，即等待1个线程
            try {

                countDownLatch.await();//监听事件中参数变为0，即放行
            }catch (Exception e){

            }
        }
        //不影响程序效率，删除监听
        zkClient.unsubscribeDataChanges(lockPath,iZkDataListener);
    }

    boolean tryLock() {

        try {
            zkClient.createEphemeral(lockPath);
            return true;
        }catch(Exception e){
            //创建节点失败，走catch
            return false;
        }

    }
}
