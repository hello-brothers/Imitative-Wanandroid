package imitative.lh.com.wanandroid.presenter;

import android.os.Handler;

import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.contract.mainpager.SplashContract;

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {

    @Override
    public void showSplashThenToMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().jumpToMain();
            }
        }, 3000);
    }
}
