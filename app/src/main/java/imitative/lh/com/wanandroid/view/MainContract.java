package imitative.lh.com.wanandroid.view;

import imitative.lh.com.wanandroid.presenter.AbstractPresenter;

public interface MainContract {
    interface View extends AbstractView{

    }

    interface Presenter extends AbstractPresenter<View>{
        void registerEvent();
    }
}
