package com.lm.baseutil.update;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.lm.baseutil.R;


/**
 * @author
 * @description
 * @Date 2019/2/22
 */
public class ProgressDialog extends Dialog implements DialogInterface.OnDismissListener{

    private CustomProgress mProgressBar;

    private View view;


    public ProgressDialog(@NonNull Context context) {
        super(context);
    }

    public ProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_progress, null, false);
        mProgressBar = findViewById(R.id.progress_layout);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setOnDismissListener(this);
        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                YoYo.with(Techniques.BounceInDown)
                        .duration(8 * 100)
                        .playOn(view);
            }
        });
    }

    public void setProgress(int progress) {
        if (progress == 100) {
            this.dismiss();
        } else {
            mProgressBar.setProgress(progress);
        }
    }

    public int getProgress() {
        return mProgressBar.getProgress();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mProgressBar.setProgress(0);
    }
}
