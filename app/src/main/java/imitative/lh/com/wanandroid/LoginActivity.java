package imitative.lh.com.wanandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import imitative.lh.com.wanandroid.presenter.BasePresenter;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;
import imitative.lh.com.wanandroid.view.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected BasePresenter createPresneter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initToolbar() {
        StatusBarUtils.immersive(this);
    }

    @Override
    protected void initDataAndEvent() {

    }
}
