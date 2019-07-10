package com.lm.baseutil.util;

/**
 * @author
 * @description 连续点击
 * @Date 2019/2/21
 */
public class MClickUtil {
    private static long lastClickTime;

    public MClickUtil() {
    }

    public static synchronized boolean isClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500L) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }
}
