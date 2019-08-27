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
    public String getLoginAccount() {
        return mPreferences.getString(Constants.ACCOUNT, "");
    }

    @Override
    public String getLoginPassword() {
        return mPreferences.getString(Constants.PASSWORD, "");
    }
}
