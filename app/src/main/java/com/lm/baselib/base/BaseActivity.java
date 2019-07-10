package com.lm.baselib.base;




import com.lm.baseutil.base.MBaseActivity;
import com.lm.baseutil.bean.MEventBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author
 * @description
 * @Date 2019/2/20
 */
public abstract class BaseActivity extends MBaseActivity {
    private Unbinder mUnbinder;

    @Override
    public void init() {

    }

    @Override
    public void bindButterKnife() {
      mUnbinder=  ButterKnife.bind(this);
    }

    @Override
    public void unbindButterknife() {
        mUnbinder.unbind();
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void baseEvent(MEventBean bean) {

    }


}
