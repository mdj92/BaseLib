package com.lm.baseutil.base;

import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;

import com.lm.baseutil.config.BaseModule;
import com.lm.baseutil.config.BaseModuleConfig;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * @author
 * @description
 * @Date 2019/2/20
 */
public abstract class MApp extends MultiDexApplication {

    private static MApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        Utils.init(this);
        init();
        initHttp();

    }

    private void initHttp(){
        BaseModuleConfig config= BaseModule.getMbaseModuleConfig();
        //初始化OkGo
        OkGo builder = OkGo.getInstance().init(this);
        Map<String, String> params = config.getParams();
        Map<String, String> headers = config.getHeaders();
        if (headers != null && !headers.keySet().isEmpty()) {
            HttpHeaders httpHeaders = new HttpHeaders();
            for (String key : headers.keySet()) {
                httpHeaders.put(key, headers.get(key));
            }
            builder.addCommonHeaders(httpHeaders);
        }
        if (params != null && !params.keySet().isEmpty()) {
            HttpParams httpParams = new HttpParams();
            for (String key : params.keySet()) {
                httpParams.put(key, params.get(key));
            }
            builder.addCommonParams(httpParams);
        }
        List<Interceptor> interceptors = config.getInterceptors();
        OkHttpClient.Builder clinet=new OkHttpClient.Builder();
        clinet.addInterceptor(loggingInterceptor());
        if (interceptors != null && !interceptors.isEmpty()) {
            for (Interceptor interceptor : interceptors) {
                clinet.addInterceptor(interceptor);
            }
        }
        //默认使用OkGo的超时时间就是60秒，如果你想改，可以自己设置
        //全局的读取超时时间
        clinet.readTimeout(config.getmReadTimeout(),TimeUnit.SECONDS);
        //全局的写入超时时间
        clinet.writeTimeout(config.getmWriteTimeout(),TimeUnit.SECONDS);
        //全局的写入超时时间
        clinet.connectTimeout(config.getmConnectTimeout(),TimeUnit.SECONDS);

        builder.setOkHttpClient(clinet.build());


    }

    private HttpLoggingInterceptor loggingInterceptor (){
        //OkGo内置的log拦截器打印log,也可以自己写个
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor("OkGoLog");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        return loggingInterceptor;
    }

    /**
     * 获取全局的application对象
     * @return
     */
    public static MApp getInstance(){
        return instance;
    }


    /**
     * 执行相关项目的一些的初始化配置
     */
    protected abstract void init();

}
