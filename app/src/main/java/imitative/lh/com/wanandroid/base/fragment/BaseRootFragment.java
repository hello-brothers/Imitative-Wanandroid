package imitative.lh.com.wanandroid.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.utils.CommonUtils;

/**
 * @Date 2019/8/29
 * @created by lh
 * @Describe:
 */
public abstract class BaseRootFragment<T extends AbstractPresenter> extends AbstractSimpleFragment implements AbstractView {

    protected T presenter;

    protected abstract T createPresenter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        if (presenter != null){
            presenter.attachView(this);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (presenter != null){
            presenter.detachView();
            presenter = null;
        }
        super.onDestroyView();
    }

    @Override
    public void showLoginView() {

    }

    @Override
    public void showLogoutView() {

    }

    @Override
    public void showSnackBar(String message) {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void preload() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void showNormalView() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showUnloginView() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        CommonUtils.showMessage(_mActivity, errorMsg);
    }

    @Override
    public void showAddColletEssay(int position, EssayData essayData) {

    }

    @Override
    public void showCancelColletEssay(int position, EssayData essayData) {
        
    }
}
