package imitative.lh.com.wanandroid.presenter;

import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.core.event.LoginEvent;
import imitative.lh.com.wanandroid.contract.mainpager.MainContract;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.LoginData;
import imitative.lh.com.wanandroid.network.util.RxUtil;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter{
    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void registerEvent() {
        addDisposible(RxBus.getDefault()
                .toFlowable(LoginEvent.class)
                .filter(LoginEvent :: isLogin)
                .subscribe(loginEvent -> getView().showLoginView()));

        addDisposible(RxBus.getDefault()
                .toFlowable(LoginEvent.class)
                .filter(loginEvent -> !loginEvent.isLogin())
                .subscribe(loginEvent -> {
                    getView().showLogoutView();
                }));
    }

    @Override
    public void logout() {

        addDisposible(manager.logout()
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleLogoutResult())
                .subscribeWith(new BaseObserver<LoginData>(mView) {
                    @Override
                    public void onNext(LoginData loginData) {
                        super.onNext(loginData);
                        WanAndroidApp.getInstance().getNetworkManager().removeAllCookie();
                        getView().showLogoutSuccess();
                        RxBus.getDefault().post(new LoginEvent(false));
                        setLoginAccount("");
                        setLoginPassword("");
                        setLoginState(false);
                    }
                }));

    }
}
