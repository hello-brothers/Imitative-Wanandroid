package imitative.lh.com.wanandroid.presenter;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.contract.mainpager.LoginContract;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.LoginData;
import imitative.lh.com.wanandroid.network.util.RxUtil;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter{

    private static final String TAG = LoginPresenter.class.getSimpleName();

    @Override
    public void getLoginData(String username, String password) {
        Log.i(TAG, "username = " + username + "password = " + password );
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            getView().showSnackBar(WanAndroidApp.getInstance().getString(R.string.account_password_null_tint));
            return;
        }
        addDisposible(manager.getLoginData(username, password)
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<LoginData>(mView) {
                    @Override
                    public void onNext(LoginData loginData) {
                        super.onNext(loginData);
                        setLoginState(true);
                        setLoginAccount(username);
                        setLoginPassword(password);
                        getView().showLoginSuccess();
                    }
                }));

    }
}
