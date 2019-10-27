package imitative.lh.com.wanandroid.app;

import java.io.File;

public class Constants {

    /**
     * Tag fragment classify
     */
    public static final int TYPE_MAIN_PAGER = 0;

    public static final int TYPE_KNOWLEDGE = 1;

    public static final int TYPE_WX_ARTICLE = 2;

    public static final int TYPE_NAVIGATION = 3;

    public static final int TYPE_PROJECT = 4;

    public static final int TYPE_COLLECT = 5;

    public static final int TYPE_SETTING = 6;

    public static final int TYPE_WANANDROID = 7;

    public static final int TYPE_LOGOUT = 8;

    public static final long CLICK_TIME_AREA = 1000;

    public static final String MY_SHARED_PREFERENCE = "my_shared_preference";


    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    public static final String LOGINSTATE = "loginstatus";
    public static final long DOUBLE_INTERVAL_TIME = 2000;
    public static final String ARG_PARAM1 = "param1";
    public static final String CURRENT_PAGE = "current_page";

    public static final int TAB_ONE = 0;
    public static final float KNOLEDGE_TEXT_SIZE = 12;
    public static final int PAGE_SIZE = 14;
    public static final String IS_LOAD_TOPESSAYDATA = "isload_topessaydata";
    public static final String ESSAY_WITHTOP_DATA = "essay_withtop_data";
    public static final String BANNER_DATA = "banner_data";
    public static final String ESSAYLIST_DATA = "essaylist_data";
    public static final String ESSAY_TOPDATA = "essay_topdata";
    public static final int REFRESH_TIME = 1000;
    public static final String ESSEY_TITLE = "essay_title";
    public static final String IS_COLLECTION = "is_collection";
    public static final String ESSAY_LINK = "url_link";
    public static final String ESSAY_ID = "essay_id";
    public static final String IS_PAGECOLLECT = "is_pagecollect";
    public static final String KNOW_TITLE = "know_title";
    public static final String DB_NAME = "wan_android_history.db";
    public static final String ET_USERNAME_LENGTH = "et_username_length";
    public static final String ET_PASSSWORD_LENGTH = "et_password_length";
    public static final String ET_CONFIRMPASSWORD_LENGTH = "et_confirmpassword_legth";
    public static final String REGISTER_NAME = "register_name";
    public static final String IS_NOIMAG_STATE = "is_noimage_state";
    private static final String PATH_DATA = WanAndroidApp.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String PATH_CACHE = Constants.PATH_DATA + "/NetCache";
    public static long delayTime = 1000;

    public static final int TYPE_DEFAULE        = 0;//搜索默认界面

    public static final int TYPE_EFFECTIVE      = 1;//有效数据界面

    public static final int TYPE_INEFFECTIVE    = 2;//无效数据界面

    public static final int TYPE_ERROR          = 3;//因为网络错误等问题界面
}
