package com.lm.baseutil.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.lm.baseutil.bean.NullBean;
import com.lm.baseutil.inter.IBaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.blankj.utilcode.util.CrashUtils.init;


public abstract class MBaseActivity extends AppCompatActivity implements IBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        bindButterKnife();
        EventBus.getDefault().register(this);
        init();
        initView();
        loadData();
    }



    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unbindButterknife();
    }

    /**
     * 如果不在基类进行注册
     *
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void NullEvent(NullBean bean) {

    }

}
