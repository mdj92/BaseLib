package com.lm.baselib;


import com.lm.baseutil.http.Result;

/**
 * @author xzy
 * @package krt.com.zhdn.util
 * @description
 * @time 2018/10/30
 */
public class MResult<T> extends Result<T> {
    @Override
    public boolean isSuccess() {
        return code==200 || code==0;
    }
}
