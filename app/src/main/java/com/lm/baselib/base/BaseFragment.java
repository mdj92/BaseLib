package com.lm.baselib.base;

import android.view.View;


import com.lm.baseutil.base.MBaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author
 * @package
 * @description
 * @time
 */
public abstract class BaseFragment extends MBaseFragment {
    private Unbinder mUnbinder;



    @Override
    public void bindButterKnife(View view) {
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void unBindButterKnife() {
        mUnbinder.unbind();
    }

    @Override
    public void init() {

    }

}
