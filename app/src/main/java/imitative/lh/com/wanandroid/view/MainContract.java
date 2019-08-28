package imitative.lh.com.wanandroid.view;

import imitative.lh.com.wanandroid.presenter.AbstractPresenter;

public interface MainContract {
    interface View extends AbstractView{

        void showLogoutSuccess();

    }

    interface Presenter extends AbstractPresenter<View>{
        void registerEvent();

        void logout();

    }
}
