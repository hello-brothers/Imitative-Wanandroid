package imitative.lh.com.wanandroid.core;

import imitative.lh.com.wanandroid.core.prefs.PreferenceHelper;

/**
 * 代理模式
 */
public class DataManager implements PreferenceHelper {
    private PreferenceHelper preferenceHelper;

    public DataManager(PreferenceHelper preferenceHelper) {
        this.preferenceHelper = preferenceHelper;
    }

    @Override
    public void setLoginAccount(String account) {
        preferenceHelper.setLoginAccount(account);
    }

    @Override
    public void setLoginPassword(String password) {
        preferenceHelper.setLoginPassword(password);
    }

    @Override
    public void setLoginState(boolean isLogin) {
        preferenceHelper.setLoginState(isLogin);
    }

    @Override
    public String getLoginAccount() {
        return preferenceHelper.getLoginAccount();
    }

    @Override
    public String getLoginPassword() {
        return preferenceHelper.getLoginPassword();
    }

    @Override
    public boolean getLoginState() {
        return preferenceHelper.getLoginState();
    }
}
