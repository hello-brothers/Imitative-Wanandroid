package imitative.lh.com.wanandroid.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.activity.BaseActivity;
import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.contract.mainpager.RegisterContract;
import imitative.lh.com.wanandroid.network.bean.BannerData;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.presenter.RegisterPresenter;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.utils.EditWatch;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View, EditWatch.TextWatcher {
    @BindView(R.id.register_toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_showPassword)
    CheckBox btn_showPassword;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_show_confirm_password)
    CheckBox btn_show_confirm_password;
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;
    @BindView(R.id.btn_register)
    Button btn_register;



    @Override
    protected RegisterPresenter createPresneter() {
        return new RegisterPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
        StatusBarUtils.immersive(this, toolbar, true);
    }

    @Override
    protected void initDataAndEvent() {
        registerCliclEvent();
    }

    private void registerCliclEvent() {
        presenter.addDisposible(RxCompoundButton.checkedChanges(btn_showPassword)
                .subscribe(aBoolean -> {
                    if (aBoolean){
                        et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }else {
                        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }));

        presenter.addDisposible(RxCompoundButton.checkedChanges(btn_show_confirm_password)
                .subscribe(aBoolean -> {
                    if (aBoolean){
                        et_confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }else {
                        et_confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }));


        EditWatch editWatch = new EditWatch();
        editWatch.addTextWatcher(et_password);
        editWatch.addTextWatcher(et_username);
        editWatch.addTextWatcher(et_confirm_password);

        et_username.addTextChangedListener(editWatch);
        et_password.addTextChangedListener(editWatch);
        et_confirm_password.addTextChangedListener(editWatch);
        editWatch.setTextWatcher(this);


        presenter.addDisposible(RxView.clicks(btn_register)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .filter(o -> btn_register.isClickable())
                .subscribe(o -> presenter.register(
                        et_username.getText().toString().trim(),
                        et_password.getText().toString().trim(),
                        et_confirm_password.getText().toString().trim())));
    }

    @NonNull
    private HashMap<String, Object> createRegisterMap(TextViewTextChangeEvent et_username, TextViewTextChangeEvent et_password, TextViewTextChangeEvent et_confirm_password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constants.ET_USERNAME_LENGTH,    et_username.text().length());
        map.put(Constants.ET_PASSSWORD_LENGTH,   et_password.text().length());
        map.put(Constants.ET_CONFIRMPASSWORD_LENGTH,      et_confirm_password.text().length());
        return map;
    }

    @Override
    public void hasNoEmptyValue() {
        btn_register.setEnabled(true);
    }

    @Override
    public void hasEmptyValue() {
        btn_register.setEnabled(false);
    }

    @Override
    public void showRegisterSuccessView(String username) {
        CommonUtils.showMessage(this, getString(R.string.register_seccuss));
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.REGISTER_NAME, username);
        intent.putExtras(bundle);
        setResult(1, intent);
        finish();
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1){
            pop();
        }else {
            ActivityCompat.finishAfterTransition(this);
        }
    }
}
