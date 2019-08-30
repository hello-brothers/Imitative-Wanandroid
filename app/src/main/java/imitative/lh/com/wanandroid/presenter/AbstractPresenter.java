package imitative.lh.com.wanandroid.presenter;

import imitative.lh.com.wanandroid.view.AbstractView;

public interface AbstractPresenter<T extends AbstractView>  {

    void attachView(T view);

    void detachView();

    boolean getLoginState();

    String getLoginAccount();

    String getLoginPassword();

    void setLoginState(boolean loginState);

    void setLoginAccount(String account);

    void setLoginPassword(String password);
}
