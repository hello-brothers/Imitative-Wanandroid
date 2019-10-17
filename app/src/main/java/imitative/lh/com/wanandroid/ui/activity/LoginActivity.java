package imitative.lh.com.wanandroid.ui.activity;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.core.event.LoginEvent;
import imitative.lh.com.wanandroid.presenter.LoginPresenter;
import imitative.lh.com.wanandroid.utils.CommonUtils;
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
    @BindView(R.id.btn_showPassword)
    CheckBox btn_showPassword;
    @BindView(R.id.tv_clearaccount)
    TextView tv_clearaccount;
    @BindView(R.id.btn_wb_login)
    ImageView btn_wb_login;
    @BindView(R.id.btn_wx_login)
    ImageView btn_wx_login;
    @BindView(R.id.btn_toregister)
    TextView btn_toregister;

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

        presenter.addDisposible(RxCompoundButton.checkedChanges(btn_showPassword)
                .subscribe(aBoolean -> {
                    if (aBoolean){
                        et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }else {
                        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }));

        presenter.addDisposible(RxView.clicks(tv_clearaccount)
                .filter(o -> et_username.getText().length() > 0)
                .subscribe(o -> et_username.setText("")));


        presenter.addDisposible(RxTextView.textChanges(et_username)
                .subscribe(o -> {
                    int visibility = et_username.getText().length() == 0 ? View.INVISIBLE : View.VISIBLE;
                    tv_clearaccount.setVisibility(visibility);
                }));

        presenter.addDisposible(RxView.clicks(btn_wx_login)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .subscribe(o -> CommonUtils.showMessage(this, getString(R.string.later))));

        presenter.addDisposible(RxView.clicks(btn_wb_login)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .subscribe(o -> CommonUtils.showMessage(this, getString(R.string.later))));

        presenter.addDisposible(RxView.clicks(btn_toregister)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .subscribe(o -> CommonUtils.showMessage(this, getString(R.string.later))));
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
