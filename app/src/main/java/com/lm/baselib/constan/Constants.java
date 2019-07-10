package com.lm.baselib.constan;




/**
 * Created by Administrator on 2018/11/19.
 */

public class Constants {


    public static String BASE_URL="http://www.zhihuidingnan.com/zhdn-opt/api/decision/";

    public static String APP_Upload="https://www.krtapp.cn/krtAppCenter/upload/json/version-djbase.json";

    public static String getUrl(String method) {
        return BASE_URL + method;
    }

    public static String getUserUrl(String method){
        return BASE_URL + "user/" + method;
    }


}
