package imitative.lh.com.wanandroid.presenter;

import imitative.lh.com.wanandroid.view.AbstractView;

public interface AbstractPresenter<T extends AbstractView>  {

    void attachView(T view);

    void detachView();

    boolean getLoginState();

    void setLoginState(boolean loginState);
}
