package imitative.lh.com.wanandroid.base.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;

/**
 * @Date 2019/8/30
 * @created by lh
 * @Describe:
 */
public abstract class BaseFragment<T extends BasePresenter> extends BaseRootFragment<T> {

    private static final int NORMAL_STATE = 1;
    private static final int LOADING_STATE = 2;
    private static final int ERROR_STATE = 3;

    private int mCurrentState = NORMAL_STATE;

    private View        loadingView;
    private View        errorView;
    private Button      btn_reload;
    private ViewGroup   mNormalView;

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
        loadingView = parent.findViewById(R.id.loading_view);
        errorView = parent.findViewById(R.id.error_view);
        btn_reload = errorView.findViewById(R.id.btn_reload);
        btn_reload.setOnClickListener( v -> reload());
        mNormalView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
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
                errorView.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    public void jumpToTheTop(){

    }
}
