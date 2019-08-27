package imitative.lh.com.wanandroid.view;

import imitative.lh.com.wanandroid.presenter.AbstractPresenter;

public interface LoginContract {
    interface View extends AbstractView{
        void showLoginSuccess();
    }

    interface Presenter extends AbstractPresenter<View>{
        void getLoginData(String username, String password);
    }
}
