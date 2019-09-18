package imitative.lh.com.wanandroid.contract.mainpager;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;

public interface MainContract {
    interface View extends AbstractView {

        void showLogoutSuccess();

    }

    interface Presenter extends AbstractPresenter<View>{
        void registerEvent();

        void logout();

    }
}
