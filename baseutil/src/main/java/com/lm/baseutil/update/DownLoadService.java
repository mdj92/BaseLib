package com.lm.baseutil.update;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import com.blankj.utilcode.util.AppUtils;

import com.lm.baseutil.util.MUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;

/**
 * @author
 * @description
 * @Date 2019/2/22
 */
public class DownLoadService extends Service {

    private String url;

    private String path;

    private String ver;

    private ProgressDialog mDialog;

    private boolean forceUpdate;

    private NotificationHelper mHelper;

    private ServiceConnection connection;

    private ProgressListener listener;

    private DownBinder mBinder = new DownBinder();


    @Override
    public IBinder onBind(Intent intent) {
        if (intent != null) {
            url = intent.getStringExtra("url");
            ver = intent.getStringExtra("ver");
            startDownload();
        }
        return mBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        getPath();
        mHelper=new NotificationHelper(getApplicationContext());
    }



    /**
     * 获取下载的路径
     */
    private void getPath(){
        if (MUtil.checkSDCard()){
            path=Environment.getExternalStorageDirectory().getPath() +File.separator + AppUtils.getAppName();
        }else {
            path=Environment.getDataDirectory().getPath() +File.separator + AppUtils.getAppName();
        }
    }

    private void startDownload() {
        if (url == null || url.isEmpty())return;;
        OkGo.<File>get(url)
                .tag(this)
                .execute(new FileCallback(path, AppUtils.getAppName() + ver + ".apk") {
                    @Override
                    public void onSuccess(Response<File> response) {
                        //完成下载,自动安装
                        mHelper.showDownloadCompleteNotification(response.body());
                        MUtil.installApp(DownLoadService.this,response.body());
                        stopSelf();
                    }

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        super.onStart(request);
                        //下载开始
                        mHelper.showNotification();
                    }


                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        if (listener != null) listener.getProgress((int) (progress.fraction * 100));
                        //更新通知栏进度
                        mHelper.updateNotification((int)(progress.fraction * 100));
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        //下载失败
                        mHelper.showDownloadFailedNotification();
                        stopSelf();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) {
            mHelper.onDestroy();
        }
        mHelper = null;
    }




    public class DownBinder extends Binder{

        public void setProgressListener(ProgressListener listener){
            DownLoadService.this.listener = listener;
        }

        public void stop(){
            OkGo.getInstance().cancelTag(DownLoadService.this);
            stopSelf();
            mHelper.cancelNotification();
        }

    }

    public interface ProgressListener{
        void getProgress(int progress);
    }

}
