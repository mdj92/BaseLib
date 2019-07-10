package com.lm.baseutil.http;

/**
 * @author
 * @description
 * @Date 2019/2/20
 */
public class Result <T>{

    public int code;

    public String msg;

    public T date;

    public boolean isSuccess() {
        return code==200 || code==0;
    }


}
