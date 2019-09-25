package imitative.lh.com.wanandroid.core.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.app.WanAndroidApp;

public class PreferenceHelperImpl implements PreferenceHelper {
    private final SharedPreferences mPreferences;

    public PreferenceHelperImpl() {
        this.mPreferences = WanAndroidApp.getInstance().getSharedPreferences(Constants.MY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    @Override
    public void setLoginAccount(String account) {
        mPreferences.edit().putString(Constants.ACCOUNT, account).apply();
    }

    @Override
    public void setLoginPassword(String password) {
        mPreferences.edit().putString(Constants.PASSWORD, password).apply();
    }

    @Override
    public void setLoginState(boolean isLogin) {
        this.mPreferences.edit().putBoolean(Constants.LOGINSTATE, isLogin).apply();
    }

    @Override
    public String getLoginAccount() {
        return mPreferences.getString(Constants.ACCOUNT, "");
    }

    @Override
    public String getLoginPassword() {
        return mPreferences.getString(Constants.PASSWORD, "");
    }

    @Override
    public boolean getLoginState() {
        return mPreferences.getBoolean(Constants.LOGINSTATE, false);
    }

    @Override
    public int getCurrentPage() {
        return mPreferences.getInt(Constants.CURRENT_PAGE, Constants.TYPE_MAIN_PAGER);
    }

    @Override
    public void setCurrentPage(int pageIndex) {
        mPreferences.edit().putInt(Constants.CURRENT_PAGE, pageIndex).apply();
    }

    @Override
    public void setIsLoadTopEssayData(boolean isLoad) {
        mPreferences.edit().putBoolean(Constants.IS_LOAD_TOPESSAYDATA, isLoad).apply();
    }

    @Override
    public boolean isLoadTopEssayData() {
        return mPreferences.getBoolean(Constants.IS_LOAD_TOPESSAYDATA, true);
    }
}
