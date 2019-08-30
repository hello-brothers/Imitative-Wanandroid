package imitative.lh.com.wanandroid.presenter;

import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.core.event.LoginEvent;
import imitative.lh.com.wanandroid.view.MainContract;

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
                .subscribe(loginEvent -> getView().showLogoutView()));
    }

    @Override
    public void logout() {
        setLoginAccount("");
        setLoginPassword("");
        setLoginState(false);
        getView().showLogoutSuccess();
    }
}
