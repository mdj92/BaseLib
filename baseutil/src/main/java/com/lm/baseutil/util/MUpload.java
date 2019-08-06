package com.lm.baseutil.util;

import android.content.Context;
import android.content.DialogInterface;


import com.github.lzyzsd.circleprogress.DonutProgress;
import com.lm.baseutil.R;
import com.lm.baseutil.http.JsonCallback;
import com.lm.baseutil.inter.IUpload;
import com.lm.baseutil.upload.UploadCallBack;
import com.lm.baseutil.view.CircleProgerssDialog;
import com.luck.picture.lib.compress.Luban;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;



/**
 * @author
 * @description
 * @Date 2019/3/1
 * 图片上传
 */
public class MUpload<T> implements IUpload {

    private Context mContext;

    //上传接口地址
    private String url;

    //回调函数
    private UploadCallBack<T> mUploadCallBack;

    private CircleProgerssDialog dialog;

    private DonutProgress mProgress;

    private Map<String,String> params;

    private Map<String,String> headers;

    private Map<String,List<String>> files;

    //是否需要压缩
    private boolean needCompress;

    private String fileKey;

    private List<String> paths;

    private boolean showDialog;


    public static <E> Builder<E> newBuilder(Context context){
        return new Builder<>(context);
    }

    private MUpload(Builder<T> builder){
        this.mContext = builder.mContext;
        this.url = builder.url;
        this.params = builder.params;
        this.headers = builder.headers;
        this.files = builder.files;
        this.showDialog = builder.showDialog;
        this.needCompress = builder.needCompress;
        this.mUploadCallBack = builder.mUploadCallBack;
        for (String key : files.keySet()){
            fileKey=key;
            paths=files.get(key);
        }

        dialog=new CircleProgerssDialog(mContext);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //取消请求
                OkGo.getInstance().cancelTag(MUpload.this);
            }
        });

    }

    @Override
    public void compress() {
        if (paths != null && !paths.isEmpty()){
            Observable.just(paths)
                    .map(new Function<List<String>, List<File>>() {
                        @Override
                        public List<File> apply(List<String> strings) throws Exception {
                            try {
                                Luban.with(mContext).load(strings).get();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            return null;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<File>>() {
                        @Override
                        public void accept(List<File> files) throws Exception {
                            if (files != null && !files.isEmpty()) {
                                doUpload(files);
                            } else {
                               MToast.showToast(mContext,mContext.getResources().getString(R.string.error_compress));
                            }
                        }
                    });

        }
    }

    @Override
    public void doUpload(List<File> files) {
        if (files == null || files.isEmpty()) return;
        if (url == null || "".equals(url)) {
            MToast.showToast(mContext, "上传地址不能为空!");
            return;
        }

        PostRequest<T> request= OkGo.post(url);
        if (params !=null && !params.keySet().isEmpty()){
            for (String key : params.keySet()){
                request.params(key,params.get(key));
            }
        }
        if (headers != null && !headers.keySet().isEmpty()) {
            for (String key : headers.keySet()) {
                request.headers(key, headers.get(key));
            }
        }
        Type[] genType=mUploadCallBack.getClass().getGenericInterfaces();
        Type type=((ParameterizedType) genType[0]).getActualTypeArguments()[0];
        request.addFileParams(fileKey,files)
                .tag(this)
                .execute(new JsonCallback<T>(type) {
                    @Override
                    public void onSuccess(Response<T> response) {
                        mUploadCallBack.onSuccess(response.body());
                    }

                    @Override
                    public void onStart(Request<T, ? extends Request> request) {
                        super.onStart(request);
                        if (showDialog){
                            dialog.show();
                            mProgress=dialog.getdProgress();
                            mProgress.setMax(100);
                        }
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        super.uploadProgress(progress);
                        if ((int) (progress.fraction * 100) == 100) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        } else {
                            if (dialog != null && dialog.isShowing()) {
                                mProgress.setProgress((int) (progress.fraction * 100));
                            }
                        }
                    }

                    @Override
                    public void onError(Response<T> response) {
                        super.onError(response);
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
    }

    private void upload(){
        if (needCompress){
            compress();
        }else {
            List<File> files=new ArrayList<>();
            for (String path :paths){
                files.add(new File(path));
            }
            doUpload(files);
        }
    }

    public static class Builder<T>{
        //上下文对象~
        private Context mContext;
        //上传接口地址
        private String url;
        //当上传的文件为图片时。是否调用算法进行图片压缩
        private boolean needCompress = false;

        private Map<String, String> params;

        private Map<String, String> headers;

        private Map<String, List<String>> files;

        private UploadCallBack<T> mUploadCallBack;

        private boolean showDialog = true;


        public Builder(Context context){
            this.mContext=context;
            params=new HashMap<>();
            headers=new HashMap<>();
        }

        public Builder<T> addFile(String filekey, List<String> path){
            files=new HashMap<>();
            files.put(filekey,path);
            return this;
        }


        public Builder<T>setUrl(String url){
            this.url=url;
            return this;
        }


        public Builder<T> addParam(String key, String value) {
            if (params != null) {
                params.put(key, value);
            }
            return this;
        }

        public Builder<T> addHeader(String key, String value) {
            if (headers != null) {
                headers.put(key, value);
            }
            return this;
        }

        public Builder<T> setNeedCompress(boolean needCompress) {
            this.needCompress = needCompress;
            return this;
        }

        public void execute(UploadCallBack<T> uploadCallBack){
            this.mUploadCallBack=uploadCallBack;
            new MUpload<>(this).upload();
        }

        public Builder setShowDialog(boolean showDialog) {
            this.showDialog = showDialog;
            return this;
        }
    }
}
