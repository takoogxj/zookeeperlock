package com.gxj.zookeeper;

import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

public  abstract class ZookeeperAbstractLock implements Extlock{

    private static final String CONNECTION="127.0.0.1:2181";
    protected ZkClient zkClient = new ZkClient(CONNECTION);

    protected String lockPath = "/lockPath";

    protected CountDownLatch countDownLatch =null;

    //获取锁
    public void getLock() {

        if(tryLock()){
            System.out.println("获取锁成功！！！");
        }else{
            waitLock();
            getLock();//递归调用再尝试加锁
        }

    }
    //创建节点失败 进入等待 使用事件通知监听该节点是否被删除
    abstract void waitLock();

    abstract boolean tryLock();

    //释放锁
    public void unLock() {

        if(zkClient != null){
            zkClient.close();
            System.out.println("释放锁成功！！！！！");
        }

    }
}
