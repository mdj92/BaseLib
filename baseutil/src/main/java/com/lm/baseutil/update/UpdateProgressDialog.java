package com.lm.baseutil.update;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.lm.baseutil.R;


/**
 * @author
 * @description 下载更新Dialog
 * @Date 2019/5/6
 */
public class UpdateProgressDialog extends Dialog {

    private NumberProgressBar bar;

    private FrameLayout bg;

    public UpdateProgressDialog(@NonNull Context context) {
        super(context, R.style.progressDialog1);
    }

    public UpdateProgressDialog(Context context, int themeResId) {
        super(context, R.style.progressDialog1);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_progress);
        bg = findViewById(R.id.bg);
        bar = findViewById(R.id.progress);
        setCanceledOnTouchOutside(false);
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                YoYo.with(Techniques.BounceInDown)
                        .duration(8 * 100)
                        .playOn(bg);
            }
        });
    }

    public void setProgress(int progress) {
        bar.setProgress(progress);
    }
}
