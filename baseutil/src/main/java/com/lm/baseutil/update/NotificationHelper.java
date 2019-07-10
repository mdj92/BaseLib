package com.lm.baseutil.update;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;


import com.lm.baseutil.R;

import java.io.File;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * @author
 * @description 通知栏下载显示
 * @Date 2019/2/22
 */
public class NotificationHelper {

    private Context mContext;

    private NotificationCompat.Builder mBuilder;

    //是状态栏通知的管理类，负责发通知、清除通知等操作。
    public NotificationManager manager;

    //成功下载完成
    private boolean isDownloadSuccess = false;

    //下载失败
    private boolean isFailed = false;

    //下载进度
    private int currentProgress = 0;

    private String contentText;

    private final int NOTIFICATION_ID = 0;



    public NotificationHelper(Context mContext){
        this.mContext=mContext;
        currentProgress=0;
    }


    /**
     * 更新通知栏状态的操作
     * @param progress
     */
    public void updateNotification(int progress){
        if ((progress -currentProgress) > 0 && !isDownloadSuccess && !isFailed){
            mBuilder.setContentIntent(null);//设置通知栏点击意图
            mBuilder.setContentText(String.format(contentText,progress));
            mBuilder.setProgress(100,progress,false);
            manager.notify(NOTIFICATION_ID,mBuilder.build());
            currentProgress=progress;
        }
    }

    /**
     * 显示通知栏
     */
    public void showNotification(){
        isDownloadSuccess=false;
        isFailed = false;
        manager= (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        mBuilder=createNotification();
    }

    /**
     * 下载成功
     * @param file
     */
    public void showDownloadCompleteNotification(File file) {
        isDownloadSuccess=true;
        Uri uri;
        Intent intent=new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        //6.0以上需要处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            uri= FileProvider.getUriForFile(mContext,mContext.getPackageName()+".versionProvider",file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else {
            uri=Uri.fromFile(file);
        }
        //设置intent类型
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setContentText(mContext.getString(R.string.download_finish));
        mBuilder.setProgress(100, 100, false);
        manager.cancelAll();
        manager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    /**
     * 下载失败
     */
    public void showDownloadFailedNotification() {
        isDownloadSuccess = false;
        isFailed = true;
        mBuilder.setContentIntent(null);
        mBuilder.setContentText(mContext.getString(R.string.download_fail));
        manager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    /**
     * 取消相关下载
     */
    public void cancelNotification() {
        isDownloadSuccess = false;
        isFailed = false;
        manager.cancelAll();
    }




    /**
     * 创建通知栏
     */
    private NotificationCompat.Builder createNotification(){
        String channelId = "0";
        String channelName = "NOTIFICATION";
        NotificationCompat.Builder builder=null;
        //8.0通知栏显示不出来处理方法
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
            channel.enableLights(false);//是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.RED); //小红点颜色
            channel.enableVibration(true);//是否开启振动
//            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager=(NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
        builder=new NotificationCompat.Builder(mContext,channelId);
        //设置这个标志当用户单击面板就可以让通知将自动取消
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setDefaults()

        //设置通知栏标题
        String contentTitle = mContext.getString(R.string.app_name);
        builder.setContentTitle(contentTitle);

        //设置通知栏显示内容
        contentText = mContext.getString(R.string.download_progress);
        builder.setContentText(String.format(contentText, 0));

        //收到信息后状态栏显示的文字信息
        String ticker = mContext.getString(R.string.download_ticker);
        builder.setTicker(ticker);

        return builder;
    }


    public void onDestroy() {
        if (manager != null) {
            manager.cancelAll();
        }
    }
}
