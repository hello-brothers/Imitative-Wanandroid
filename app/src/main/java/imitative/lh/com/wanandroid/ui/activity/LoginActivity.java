package imitative.lh.com.wanandroid.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.core.event.LoginEvent;
import imitative.lh.com.wanandroid.presenter.LoginPresenter;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;
import imitative.lh.com.wanandroid.base.activity.BaseActivity;
import imitative.lh.com.wanandroid.contract.mainpager.LoginContract;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.login_toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @Override
    protected LoginPresenter createPresneter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
        StatusBarUtils.immersive(this, toolbar, true);

    }

    @Override
    protected void initDataAndEvent() {
        subscribeLoginClickEvent();
    }

    private void subscribeLoginClickEvent() {
        presenter.addDisposible(RxView.clicks(btn_login)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .filter(o -> presenter != null)
                .subscribe(o ->presenter.getLoginData(et_username.getText().toString().trim(), et_password.getText().toString().trim())));
    }

    @Override
    public void showLoginSuccess() {
        showToast(WanAndroidApp.getInstance().getString(R.string.login_success));
        setResult(0);
        finish();
        RxBus.getDefault().post(new LoginEvent(true));
    }

    @Override
    public void showErrorView() {
        super.showErrorView();

    }
}
