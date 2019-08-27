package imitative.lh.com.wanandroid.core;

import imitative.lh.com.wanandroid.core.prefs.PreferenceHelper;

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
    public String getLoginAccount() {
        return preferenceHelper.getLoginAccount();
    }

    @Override
    public String getLoginPassword() {
        return preferenceHelper.getLoginPassword();
    }
}
