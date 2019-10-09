package imitative.lh.com.wanandroid.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.base.view.AbstractView;


public abstract class BaseActivity<T extends AbstractPresenter> extends AbstractSimpleActivity implements AbstractView {
    protected T presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewCreated() {
        presenter = createPresneter();
//        if (presenter == null){
//            throw new IllegalStateException("presenter is null!");
//        }
        if (presenter != null){
            presenter.attachView(this);
        }
    }

    protected abstract T createPresneter();

    @Override
    protected void onDestroy() {
        if (presenter!=null){
            presenter.detachView();
            presenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void showLoginView() {

    }

    @Override
    public void showLogoutView() {

    }

    @Override
    public void showUnloginView() {

    }

    @Override
    public void preload() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void showSnackBar(String message) {
        CommonUtils.showSnackMessage(this, message);
    }

    @Override
    public void showToast(String message) {
        CommonUtils.showMessage(this, message);
    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void showNormalView() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        CommonUtils.showMessage(this, errorMsg);
    }

    @Override
    public void showCancelColletEssay(int position, EssayData essayData) {

    }

    @Override
    public void showAddColletEssay(int position, EssayData essayData) {

    }
}
