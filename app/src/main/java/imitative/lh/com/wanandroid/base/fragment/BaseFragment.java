package imitative.lh.com.wanandroid.base.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.ui.activity.LoginActivity;

/**
 * @Date 2019/8/30
 * @created by lh
 * @Describe:
 */
public abstract class BaseFragment<T extends BasePresenter> extends BaseRootFragment<T> {

    private static final int NORMAL_STATE = 1;
    private static final int LOADING_STATE = 2;
    private static final int ERROR_STATE = 3;
    private static final int UNLOGIN_STATUS = 4;

    private int mCurrentState = NORMAL_STATE;

    private View        loadingView;
    private View        errorView;
    private View        unloginView;
    private TextView    btn_reload;
    private ViewGroup   mNormalView;
    private TextView tv_toLogin;
    private LottieAnimationView empty_view_anim;

    @Override
    protected void initDataAndView() {
        if (getView() == null){
            return;
        }
        mNormalView = getView().findViewById(R.id.normal_view);
        if (mNormalView == null){
            throw new IllegalArgumentException("smartrefresh is null");
        }
        if (!(mNormalView.getParent() instanceof  ViewGroup)){
            throw new IllegalArgumentException("this view`s parent should be ViewGroup");
        }

        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(_mActivity, R.layout.loading_view, parent);
        View.inflate(_mActivity, R.layout.error_view, parent);
        View.inflate(_mActivity, R.layout.unlogin_view, parent);

        unloginView = parent.findViewById(R.id.unlogin_view);
        loadingView = parent.findViewById(R.id.loading_view);
        errorView = parent.findViewById(R.id.error_view);

        tv_toLogin = unloginView.findViewById(R.id.tv_tologin);
        tv_toLogin.setOnClickListener(v -> toLogin());
        btn_reload = errorView.findViewById(R.id.btn_reload);
        empty_view_anim = errorView.findViewById(R.id.empty_value_view);

        btn_reload.setOnClickListener( v -> reload());
        unloginView.setVisibility(View.GONE);
        mNormalView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    private void toLogin() {
        startActivity(new Intent(_mActivity, LoginActivity.class));
    }

    @Override
    public void showNormalView() {
        super.showNormalView();
        if (mCurrentState == NORMAL_STATE){
            return;
        }
        hideCurrentView();
        mCurrentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingView() {
        if (mCurrentState == LOADING_STATE || loadingView == null){
            return;
        }
        hideCurrentView();
        mCurrentState = LOADING_STATE;
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorView() {
        if (mCurrentState == ERROR_STATE || errorView == null){
            return;
        }
        hideCurrentView();
        mCurrentState = ERROR_STATE;
        errorView.setVisibility(View.VISIBLE);
        empty_view_anim.setAnimation("empty_value.json");
        empty_view_anim.loop(true);
        empty_view_anim.playAnimation();
    }

    @Override
    public void showUnloginView() {
        if (mCurrentState == UNLOGIN_STATUS || unloginView == null){
            return;
        }
        hideCurrentView();
        mCurrentState = UNLOGIN_STATUS;
        unloginView.setVisibility(View.VISIBLE);

    }

    private void hideCurrentView() {
        switch (mCurrentState){
            case NORMAL_STATE:
                if (mNormalView == null){
                    return;
                }
                mNormalView.setVisibility(View.INVISIBLE);
                break;
            case LOADING_STATE:
                loadingView.setVisibility(View.GONE);
                break;
            case ERROR_STATE:
                empty_view_anim.cancelAnimation();
                errorView.setVisibility(View.GONE);
                break;
            case UNLOGIN_STATUS:
                unloginView.setVisibility(View.GONE);
            default:
                break;
        }
    }

    public void jumpToTheTop(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (empty_view_anim != null){
            empty_view_anim.cancelAnimation();
        }
    }
}
