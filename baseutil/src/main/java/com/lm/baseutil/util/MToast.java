package com.lm.baseutil.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author dj
 * @description 土司类
 * @Date 2019/2/21
 */
public class MToast {

    private static Toast toast;

    public static void showToast(Context context, String info) {
        if (toast == null) {
            toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
        } else {
            toast.setText(info);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

}
