package com.lm.baseutil.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lm.baseutil.bean.NullBean;
import com.lm.baseutil.inter.IBaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author dj
 * @description
 * @Date 2019/2/20
 */
public abstract class MBaseFragment extends Fragment implements IBaseFragment {

    //不建议使用getActivity方法
    protected Context mContext;

    //判断UI是否已经加载完成。因为setUserVisibleHint()会在onCreateView()之前调用
    protected boolean isPrepard=false;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isPrepard=true;
        return inflater.inflate(bindLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        bindButterKnife(view);
        init();
        initView(view);
        lazLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            lazLoad();
        }
    }


    /**
     * 懒加载数据
     */
    private void lazLoad(){
        boolean visable=getUserVisibleHint();
        if (visable && isPrepard){
            loadData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindButterknife();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void NullEvent(NullBean bean) {

    }

    public abstract void unBindButterKnife();
}
