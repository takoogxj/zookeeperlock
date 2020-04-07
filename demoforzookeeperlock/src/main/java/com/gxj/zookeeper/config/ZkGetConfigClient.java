package com.gxj.zookeeper.config;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ZkGetConfigClient {

    private Config config;

    public Config getConfig(){
        ZkClient zkClient = new ZkClient("localhost:2181");
        config = (Config)zkClient.readData("/zkConfig");
        System.out.println("加载到配置："+config.toString());

        //监听配置文件修改
        zkClient.subscribeDataChanges("/zkConfig", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                config = (Config)o;
                System.out.println("监听到配置文件被修改："+config.toString());
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                config = null;
                System.out.println("监听到配置文件被删除");
            }
        });
        return  config;
    }

    public static void main(String[] args) {
        ZkGetConfigClient zkGetConfigClient = new ZkGetConfigClient();
        zkGetConfigClient.getConfig();
        System.out.println(zkGetConfigClient.config.toString());

        for (int i = 0; i < 10; i++) {
            System.out.println(zkGetConfigClient.config.toString());
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
