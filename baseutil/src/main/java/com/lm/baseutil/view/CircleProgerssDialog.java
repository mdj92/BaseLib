package com.lm.baseutil.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.TextView;


import com.github.lzyzsd.circleprogress.DonutProgress;
import com.lm.baseutil.R;

/**
 * @author
 * @description
 * @Date 2019/3/1
 */
public class CircleProgerssDialog extends Dialog {

    private DonutProgress dProgress;
    private TextView textView;

    public CircleProgerssDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_progressdialog);
        setCanceledOnTouchOutside(false);
        dProgress=findViewById(R.id.donutprogress);
        textView=findViewById(R.id.info);

    }

    public TextView getText(){
       return textView ;
    }



    public DonutProgress getdProgress(){
        return dProgress;
    }
}
