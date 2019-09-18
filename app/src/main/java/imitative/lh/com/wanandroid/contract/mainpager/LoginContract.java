package imitative.lh.com.wanandroid.contract.mainpager;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;

public interface LoginContract {
    interface View extends AbstractView {
        void showLoginSuccess();
    }

    interface Presenter extends AbstractPresenter<View>{
        void getLoginData(String username, String password);
    }
}
