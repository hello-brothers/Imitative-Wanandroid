package imitative.lh.com.wanandroid.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;

public class AboutUsActivity extends BaseActivity {


    @Override
    protected AbstractPresenter createPresneter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_aboutus;
    }

    @Override
    protected void initToolbar() {
        StatusBarUtils.immersive(this, null, true);
    }

    @Override
    protected void initDataAndEvent() {

    }
}
