package imitative.lh.com.wanandroid.presenter;

import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.contract.mainpager.RegisterContract;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.RegisterData;
import imitative.lh.com.wanandroid.network.util.RxUtil;

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    @Override
    public void attachView(RegisterContract.View view) {
        super.attachView(view);
        registerClickEvent();
    }

    private void registerClickEvent() {

    }

    @Override
    public void register(String username, String password, String repassword) {
        addDisposible(manager.register(username, password, repassword)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleRegisterResult())
                .subscribeWith(new BaseObserver<RegisterData>(mView) {
                    @Override
                    public void onNext(RegisterData registerData) {
                        super.onNext(registerData);
                        mView.showRegisterSuccessView(username);
                    }
                }));
    }
}
