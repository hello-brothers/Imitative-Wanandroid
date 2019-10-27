package imitative.lh.com.wanandroid.base.presenter;

import imitative.lh.com.wanandroid.base.view.AbstractView;

public interface AbstractPresenter<T extends AbstractView>  {

    void attachView(T view);

    void detachView();

    boolean getLoginState();

    String getLoginAccount();

    String getLoginPassword();

    int getCurrentPage();

    void setCurrentPage(int index);

    void setLoginState(boolean loginState);

    void setLoginAccount(String account);

    void setLoginPassword(String password);

    void setNoImageState(Boolean noImageState);

    boolean getNoImageState();

    void setIsLoadTopEssayData(boolean isLoadTopEssayData);

    boolean getIsLoadTopEssayData();

}
