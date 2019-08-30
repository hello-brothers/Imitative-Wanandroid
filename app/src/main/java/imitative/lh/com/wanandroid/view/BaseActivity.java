package imitative.lh.com.wanandroid.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import imitative.lh.com.wanandroid.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.utils.CommonUtils;


public abstract class BaseActivity<T extends AbstractPresenter> extends AbstractSimpleActivity implements AbstractView{
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
    public void preload() {

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
}
