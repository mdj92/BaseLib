package com.lm.baseutil.util;

import android.app.Activity;
import android.view.View;

import com.jaeger.library.StatusBarUtil;

/**
 * @author
 * @description 设置状态栏
 * @Date 2019/2/21
 */
public class MStatusBarUtil {

    /**
     * 设置状态栏颜色
     *
     * @param activity
     * @param color
     */
    public static void setStatusBar(Activity activity, int color) {
        StatusBarUtil.setColor(activity, color);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity
     * @param color
     * @param alpha
     */
    public static void setStatusBar(Activity activity, int color, int alpha) {
        StatusBarUtil.setColor(activity, color, alpha);
    }

    /**
     * 设置状态栏黑色字体
     */
    public static void setDarkMode(Activity activity) {
        StatusBarUtil.setDarkMode(activity);
    }

    /**
     * 设置状态栏白色字体
     */
    public static void setLightMode(Activity activity) {
        StatusBarUtil.setLightMode(activity);

    }


    /**
     * 设置全透明状态栏
     */
    public static void setTranslucentBar(Activity activity) {
        StatusBarUtil.setTranslucentForImageView(activity, null);
    }

    /**
     * 设置全透明状态栏
     */
    public static void setTranslucentBar(Activity activity, int alpha) {
        StatusBarUtil.setTranslucentForImageView(activity, alpha, null);
    }

    /**
     * 设置全透明状态栏
     */
    public static void setTranslucentBar(Activity activity, int alpha, View view) {
        StatusBarUtil.setTranslucentForImageView(activity, alpha, view);
    }

}
