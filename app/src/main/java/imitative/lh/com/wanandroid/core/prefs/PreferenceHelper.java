package imitative.lh.com.wanandroid.core.prefs;

public interface PreferenceHelper {

    void setLoginAccount(String account);

    void setLoginPassword(String password);

    void setLoginState(boolean isLogin);

    String getLoginAccount();

    String getLoginPassword();

    boolean getLoginState();

    int getCurrentPage();

    void setCurrentPage(int pageIndex);

}
