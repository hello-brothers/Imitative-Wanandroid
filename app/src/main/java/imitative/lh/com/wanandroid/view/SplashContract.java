package imitative.lh.com.wanandroid.view;

import imitative.lh.com.wanandroid.presenter.AbstractPresenter;

public interface SplashContract {
    interface View extends AbstractView{
        void jumpToMain();
    }

    interface Presenter extends AbstractPresenter<View>{
        void showSplashThenToMain();

    }

}
