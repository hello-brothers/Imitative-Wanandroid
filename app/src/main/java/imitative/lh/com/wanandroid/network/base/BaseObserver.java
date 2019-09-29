package imitative.lh.com.wanandroid.network.base;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.contract.mainpager.MainPagerContract;
import imitative.lh.com.wanandroid.network.exception.ApiException;
import imitative.lh.com.wanandroid.network.exception.CustomException;
import io.reactivex.observers.ResourceObserver;

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe: 观察者基类 同一处理
 * 1、onError处理exception提示
 * 2.重写的onNext处理数据显示
 */
public abstract class BaseObserver<T> extends ResourceObserver<T> {

    private final AbstractView mView;

    public BaseObserver(AbstractView mView) {
        this.mView = mView;
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        if (mView == null){
            return;
        }
        String message = e.getMessage();
        if (e instanceof ApiException){
            int code = ((ApiException) e).getExceptionCode();
            showErrorToastAndView(code, message);
            mView.showErrorView();
        }

    }

    private void showErrorToastAndView(int code, String message) {
        switch (code){
            case CustomException.PARSE_ERROR:
                mView.showErrorMsg(WanAndroidApp.getInstance().getString(R.string.PARSE_ERROR));
                break;
            case CustomException.HTTP_ERROR:
                mView.showErrorMsg(WanAndroidApp.getInstance().getString(R.string.HTTP_ERROR));
                break;
            case CustomException.NETWORK_ERROR:
                mView.showErrorMsg(WanAndroidApp.getInstance().getString(R.string.NETWORK_ERROR));
                break;
            case CustomException.UNKNOWN:
                mView.showErrorMsg(WanAndroidApp.getInstance().getString(R.string.UNKNOWN));
                break;
            case BaseResponse.CODE_FAIL:
                mView.showErrorMsg(WanAndroidApp.getInstance().getString(R.string.LOGIN_ERROR));
                break;
        }
    }

    @Override
    public void onComplete() {

    }
}
