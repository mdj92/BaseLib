package com.lm.baseutil.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.lm.baseutil.download.DownLoadCallBack;
import com.lm.baseutil.view.CircleProgerssDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;

/**
 * @author
 * @description
 * @Date 2019/3/1
 * 下载类工具
 */
public class MDownLoad {

    private Context mContext;

    private String url;

    private String path;

    private boolean showDialog;

    private CircleProgerssDialog dialog;

    private DonutProgress mProgress;

    private DownLoadCallBack downLoadListener;

    private String filePath;

    private String fileName;

    private TextView textView;


    private MDownLoad(MDownLoad.Builder builder){
        this.mContext = builder.mContext;
        this.url = builder.url;
        this.downLoadListener = builder.mCallBack;
        this.showDialog = builder.showDialog;
        this.fileName=builder.fileName;
        dialog = new CircleProgerssDialog(mContext);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                OkGo.getInstance().cancelTag(this);
            }
        });
        //如果有sd卡，则获取sd卡的路径/storage/sdcard；如果没有，获取Android目录/data
        if (MUtil.checkSDCard()){
            path = Environment.getExternalStorageDirectory().getPath() + File.separator + AppUtils.getAppName();
        }else {
            path = Environment.getDataDirectory().getPath() + File.separator + AppUtils.getAppName();
        }

        File filepath=new File(path);
        if (filepath.exists()){
            filepath.mkdirs();
        }
        checkPermission();
    }

    private void start(){
        final File file=new File(path,fileName);
        File file1= new File(file.getAbsolutePath());
        if (file1.exists()){
            //判断文件是否存在
            openFiles(file);
        }else {
            OkGo.<File>get(url)
                    .tag(this)
                    .execute(new FileCallback(path,fileName) {
                        @Override
                        public void onSuccess(Response<File> response) {
                            if (downLoadListener != null){
                                downLoadListener.onSuccess(response.body());
                            }
                            File file2=response.body();
                            if ((new File(file.getAbsolutePath())).exists()){
                                openFiles(response.body());
                            }

                        }

                        @Override
                        public void onStart(Request<File, ? extends Request> request) {
                            super.onStart(request);
                            if (showDialog) {
                                dialog.show();
                                textView=dialog.getText();
                                textView.setText("正在下载中...");
                                mProgress = dialog.getdProgress();
                                mProgress.setMax(100);
                            }
                        }

                        @Override
                        public void onError(Response<File> response) {
                            super.onError(response);
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void downloadProgress(Progress progress) {
                            super.downloadProgress(progress);
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
                    });
        }
    }


    private void openFiles(File file){
        //Intent.ACTION_VIEW
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", file);
            intent.setDataAndType(contentUri, "application/vnd.ms-powerpoint");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.ms-powerpoint");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (mContext.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            mContext.startActivity(intent);
        }
    }

    /**
     * 权限验证
     *
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            MPermissions.getInstance().request((Activity) mContext, permissions, new MPermissions.PermissionListener() {
                @Override
                public void callBack(boolean value) {
                    if (value) {
                        start();
                    } else {
                        MToast.showToast(mContext, "您没有授权该权限，请在设置中打开授权!");
                    }
                }
            });
        } else {
            start();
        }
    }

    public static final class Builder {
        /**
         * 上下文参数
         */
        private Context mContext;
        /**
         * 更新的地址
         */
        private String url;
        /**
         * 是否需要显示进度对话框
         */
        private boolean showDialog;

        private String fileName;

        private DownLoadCallBack mCallBack;

        public Builder(Context context) {
            mContext = context;
        }


        public Builder setFileName(String fileName){
            this.fileName = fileName;
            return this;
        }


        public Builder setUrl(String url){
            this.url = url;
            return this;
        }


        public Builder setShowDialog(boolean showDialog) {
            this.showDialog = showDialog;
            return this;
        }

        public void execute(DownLoadCallBack downLoadCallBack) {
            this.mCallBack = downLoadCallBack;
            new MDownLoad(this);
        }


    }


}
