package com.lm.baseutil.util;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;


/**
 * @author dj
 * @description 权限管理类
 * @Date 2019/2/21
 */
public class MPermissions {

    private MPermissions() {

    }

    private static class SingletionHolder {
        private static final MPermissions instance = new MPermissions();

    }

    public static MPermissions getInstance(){
        return new MPermissions();
//        return SingletionHolder.instance;
    }

    public interface PermissionListener {
        void callBack(boolean value);
    }

    /**
     * 设置单个权限
     */
    @SuppressLint("CheckResult")
    public void request(Activity activity, String[] permissions, final PermissionListener listener) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (listener != null) listener.callBack(aBoolean);
                    }
                });

    }

    /**
     * 设置多个权限
     */
    @SuppressLint("CheckResult")
    public void request(Activity activity, String permissions, final PermissionListener listener) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (listener != null) listener.callBack(aBoolean);
                    }
                });

    }

}
