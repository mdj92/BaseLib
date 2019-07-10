package com.lm.baseutil.config;

/**
 * @author
 * @description
 * @Date 2019/2/20
 */
public class BaseModule {

    private static BaseModuleConfig mbaseModuleConfig;

    private BaseModule(){

    }

    public static void initialize(BaseModuleConfig baseModuleConfig){
        mbaseModuleConfig=baseModuleConfig;
    }

    public static BaseModuleConfig getMbaseModuleConfig(){
        if (mbaseModuleConfig == null){
            return BaseModuleConfig.newBuilder().build();
        }else {
            return mbaseModuleConfig;
        }
    }

}
