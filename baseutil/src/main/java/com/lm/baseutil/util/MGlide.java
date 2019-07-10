package com.lm.baseutil.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author
 * @description
 * @Date 2019/3/1
 */
public class MGlide {

    /**
     * 基础加载无任何配置
     */
    public static void baseLoad(Context context, Object url, ImageView imageView) {
        baseLoad(context, url, imageView, Priority.NORMAL);
    }


    /**
     * 基础加载无任何配置
     */
    public static void baseLoad(Context context, Object url, ImageView imageView, Priority priority) {
        GlideApp.with(context)
                .load(url)
                .priority(priority)
                .into(imageView);
    }


    /**
     * 带占位符的加载
     */
    public static void loadWithPlaceHolder(Context context, Object url, int ph, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .placeholder(ph)
                .into(imageView);
    }

    /**
     * 带占位符的加载
     */
    public static void loadWithPlaceHolder(Context context, Object url, int ph, int error, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .placeholder(ph)
                .error(error)
                .into(imageView);
    }

    /**
     * 加载圆形图片(使用普通ImageView) Glide会进行裁剪
     */
    public static void loadWithCircle(Context context, Object url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载圆形图片(使用普通ImageView) Glide会进行裁剪
     */
    public static void loadWithCircle(Context context, Object url, int ph, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .placeholder(ph)
                .circleCrop()
                .into(imageView);
    }

    /**
     * 加载圆形图片(使用普通ImageView) Glide会进行裁剪
     */
    public static void loadWithCircle(Context context, Object url, int ph, int error, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .placeholder(ph)
                .error(error)
                .circleCrop()
                .into(imageView);
    }

    /**
     * 加载圆角矩形图片
     */
    public static void loadWithRoundCorner(Context context, Object url, ImageView imageView, int corners) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(context, corners));
        GlideApp.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载圆角矩形图片
     */
    public static void loadWithRoundCorner(Context context, Object url, ImageView imageView, int ph, int corners) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(context, corners));
        GlideApp.with(context)
                .load(url)
                .placeholder(ph)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载圆角矩形图片
     */
    public static void loadWithRoundCorner(Context context, Object url, ImageView imageView, int ph, int error, int corners) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(context, corners));
        GlideApp.with(context)
                .load(url)
                .error(error)
                .placeholder(ph)
                .apply(options)
                .into(imageView);
    }

}
