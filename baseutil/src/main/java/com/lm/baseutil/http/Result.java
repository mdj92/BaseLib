package com.lm.baseutil.http;

import com.lm.baseutil.config.BaseModule;

/**
 * @author
 * @description
 * @Date 2019/2/20
 */
public class Result <T>{

    public int code;

    public String msg;

    public T data;

    public boolean isSuccess() {
        return code == BaseModule.getMbaseModuleConfig().getServerSuccessCode();
    }


}
