package imitative.lh.com.wanandroid.presenter;

import android.text.TextUtils;
import android.util.Log;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.view.BaseActivity;
import imitative.lh.com.wanandroid.view.LoginContract;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter{

    private static final String TAG = LoginPresenter.class.getSimpleName();

    @Override
    public void getLoginData(String username, String password) {
        Log.i(TAG, "username = " + username + "password = " + password );
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            getView().showSnackBar(WanAndroidApp.getInstance().getString(R.string.account_password_null_tint));
            return;
        }
        setLoginState(true);
        setLoginAccount(username);
        setLoginPassword(password);
        setLoginState(true);
        getView().showLoginSuccess();
    }
}
