package com.lm.baselib.base;


import com.lm.baselib.R;
import com.lm.baseutil.base.MApp;
import com.lm.baseutil.config.BaseModule;
import com.lm.baseutil.config.BaseModuleConfig;

/**
 * @author
 * @package
 * @description
 * @time
 */
public class App extends MApp {

    @Override
    protected void init() {
        BaseModule.initialize(BaseModuleConfig.newBuilder()
                .setLoadingViewColor(R.color.color_DA0101).build());

    }

}
