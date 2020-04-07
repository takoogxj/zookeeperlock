package com.gxj.zookeeper.config;

public class ZkConfigTest {

    public static void main(String[] args) {

        ZkConfigMag mag = new ZkConfigMag();
        //从数据库加载配置
        Config config = mag.downLoadConfigFromDB();
        System.out.println("....加载数据库配置...."+config.toString());
        //同步到zk
        mag.syncConfigToZk();
        System.out.println("....同步配置文件到zookeeper....");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mag.upLoadConfigToDB("cwhcc", "passwordcc");
        System.out.println("....修改配置文件...."+config.toString());

        mag.syncConfigToZk();
        System.out.println("....同步配置文件到zookeeper....");
    }

}
