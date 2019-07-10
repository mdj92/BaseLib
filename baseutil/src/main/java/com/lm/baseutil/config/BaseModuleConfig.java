package com.lm.baseutil.config;

import android.support.annotation.ColorRes;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author
 * @description
 * @Date 2019/2/20
 */
public class BaseModuleConfig {

    /**
     * 请求公共头部参数
     */
    private Map<String,String> headers;

    /**
     * 请求公共参数
     */
    private Map<String,String> params;

    /**
     * 连接超时时间
     */
    private int mConnectTimeout = 8;
    /**
     * 读取超时时间
     */
    private int mReadTimeout = 8;

    /**
     * 写入超时时间
     */
    private int mWriteTimeout = 8;

    /*
     * 服务端接口返回正常code值  默认200
     */
    private int serverSuccessCode;


    /**
     * 默认LoadingView样式
     */
    private String dialogStyle;
    /**
     * 刷新控件的颜色
     */
    private int refreshColor;
    /**
     * LoadingDialog颜色
     *
     * @return
     */
    private int dialogColor;




    public static Builder newBuilder(){
        return new Builder();
    }

    private BaseModuleConfig(Builder builder){
        this.headers=builder.headers;
        this.params = builder.params;
        this.mConnectTimeout = builder.mConnectTimeout;
        this.mReadTimeout = builder.mReadTimeout;
        this.serverSuccessCode=builder.serverSuccessCode;
        this.dialogColor=builder.dialogColor;
        this.dialogStyle=builder.dialogStyle;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public int getmConnectTimeout() {
        return mConnectTimeout;
    }

    public int getmReadTimeout() {
        return mReadTimeout;
    }

    public int getmWriteTimeout() {
        return mWriteTimeout;
    }

    public int getServerSuccessCode() {
        return serverSuccessCode;
    }

    public String getDialogStyle() {
        return dialogStyle;
    }

    public int getRefreshColor() {
        return refreshColor;
    }

    public int getDialogColor() {
        return dialogColor;
    }





    public final static class Builder{
        private Builder(){

        }



        /**
         * 默认LoadingView样式
         */
        private String dialogStyle = "LineScaleIndicator";

        /**
         * LaddingView的颜色
         */
        private int dialogColor = -1;
        /**
         * 请求公共头部参数
         */
        private Map<String,String> headers;

        /**
         * 请求公共参数
         */
        private Map<String,String> params;

        /**
         * 连接超时时间
         */
        private int mConnectTimeout = 8;
        /**
         * 读取超时时间
         */
        private int mReadTimeout = 8;

        /**
         * 写入超时时间
         */
        private int mWriteTimeout = 8;

        /*
         * 服务端接口返回正常code值  默认200
         */
        private int serverSuccessCode=200;


        /**
         * 设置LoadingView样式
         *
         * @param dialogStyle
         */
        public Builder setLoadingViewStyle(String dialogStyle) {
            this.dialogStyle = dialogStyle;
            return this;
        }


        public Builder setLoadingViewColor(@ColorRes int color) {
            this.dialogColor = color;
            return this;
        }

        public Builder addParams(String key, String value) {
            if (params == null) {
                params = new Hashtable<>();
            }
            params.put(key, value);
            return this;
        }

        public Builder addHeaders(String key, String value) {
            if (headers == null) {
                headers = new Hashtable<>();
            }
            headers.put(key, value);
            return this;
        }



        public Builder setServerSuccessCode(int serverSuccessCode) {
            this.serverSuccessCode = serverSuccessCode;
            return this;
        }



        public Builder setConnectTimeout(int connectTimeout) {
            mConnectTimeout = connectTimeout;
            return this;
        }

        public Builder setReadTimeout(int readTimeout) {
            mReadTimeout = readTimeout;
            return this;
        }



        public BaseModuleConfig build() {
            return new BaseModuleConfig(this);
        }


//        public Builder setNeedGetResult(boolean needGetResult) {
//            this.needGetResult = needGetResult;
//            return this;
//        }
//
//        /**
//         * 添加拦截器
//         *
//         * @return
//         */
//        public Builder addInterceptor(Interceptor interceptor) {
//            if (interceptors == null) {
//                interceptors = new ArrayList<>();
//            }
//            interceptors.add(interceptor);
//            return this;
//        }
//        public Builder setNeedPrintLog(boolean needPrintLog) {
//            this.needPrintLog = needPrintLog;
//            return this;
//        }


    }
}
