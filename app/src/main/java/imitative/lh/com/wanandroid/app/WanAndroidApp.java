package imitative.lh.com.wanandroid.app;

import android.app.Application;

import imitative.lh.com.wanandroid.core.DataManager;
import imitative.lh.com.wanandroid.core.prefs.PreferenceHelperImpl;

public class WanAndroidApp extends Application {
    public static boolean isFirstrun = true;


    public static WanAndroidApp instance;
    private DataManager dataManager;

    public static synchronized WanAndroidApp getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dataManager = new DataManager(new PreferenceHelperImpl());
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
