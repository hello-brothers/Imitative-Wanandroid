package imitative.lh.com.wanandroid.presenter;

import android.os.Handler;

import imitative.lh.com.wanandroid.view.SplashContract;

public class SplashPresenter extends BasePresenter<SplashContract.View> {

    @Override
    public void attachView(SplashContract.View view) {
        super.attachView(view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().jumpToMain();
            }
        }, 3000);    }
}
