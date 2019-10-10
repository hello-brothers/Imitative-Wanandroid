package imitative.lh.com.wanandroid.app;

import android.app.Application;

import com.facebook.stetho.Stetho;

import imitative.lh.com.wanandroid.BuildConfig;
import imitative.lh.com.wanandroid.core.DataManager;
import imitative.lh.com.wanandroid.core.db.DBHelperImpl;
import imitative.lh.com.wanandroid.core.prefs.PreferenceHelperImpl;
import imitative.lh.com.wanandroid.network.core.HttpHelperImpl;
import imitative.lh.com.wanandroid.network.core.NetworkManager;

public class WanAndroidApp extends Application {
    public static boolean isFirstrun = true;


    public static WanAndroidApp instance;
    private DataManager dataManager;
    private NetworkManager networkManager;

    public static synchronized WanAndroidApp getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        networkManager = NetworkManager.getInstance();

        dataManager = new DataManager(new PreferenceHelperImpl(), new HttpHelperImpl(), DBHelperImpl.getInstance(this));

        if (BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this);
        }
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public NetworkManager getNetworkManager(){
        return networkManager;
    }
}
