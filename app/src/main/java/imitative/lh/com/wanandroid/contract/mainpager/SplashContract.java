package imitative.lh.com.wanandroid.contract.mainpager;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;

public interface SplashContract {
    interface View extends AbstractView {
        void jumpToMain();
    }

    interface Presenter extends AbstractPresenter<View>{
        void showSplashThenToMain();

    }

}
