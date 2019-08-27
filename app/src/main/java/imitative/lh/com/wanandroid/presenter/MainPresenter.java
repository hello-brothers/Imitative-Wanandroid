package imitative.lh.com.wanandroid.presenter;

import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.core.event.LoginEvent;
import imitative.lh.com.wanandroid.view.MainContract;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

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
                .subscribe(new Consumer<LoginEvent>() {
                    @Override
                    public void accept(LoginEvent loginEvent) throws Exception {
                        getView().showLoginView();
                    }
                }));

        addDisposible(RxBus.getDefault()
                .toFlowable(LoginEvent.class)
                .filter(new Predicate<LoginEvent>() {
                    @Override
                    public boolean test(LoginEvent loginEvent) throws Exception {
                        return !loginEvent.isLogin();
                    }
                })
                .subscribe(new Consumer<LoginEvent>() {
                    @Override
                    public void accept(LoginEvent loginEvent) throws Exception {
                        getView().showLogoutView();
                    }
                }));
    }
}
