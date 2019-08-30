package imitative.lh.com.wanandroid.presenter;

import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.view.AbstractView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<T extends AbstractView> implements AbstractPresenter<T>  {
    protected T mView;
    private CompositeDisposable compositeDisposable;
    public void attachView(T view){
        this.mView = view;
    }

    public void detachView(){
        this.mView = null;
        if (compositeDisposable != null){
            compositeDisposable.clear();
        }
    }

    public T getView(){
        return this.mView;
    }

    public boolean getLoginState(){
        return WanAndroidApp.getInstance().getDataManager().getLoginState();
    }

    @Override
    public String getLoginAccount() {
        return WanAndroidApp.getInstance().getDataManager().getLoginAccount();
    }

    @Override
    public String getLoginPassword() {
        return WanAndroidApp.getInstance().getDataManager().getLoginPassword();
    }

    public void setLoginState(boolean loginState){
        WanAndroidApp.getInstance().getDataManager().setLoginState(loginState);
    }

    @Override
    public void setLoginAccount(String account) {
        WanAndroidApp.getInstance().getDataManager().setLoginAccount(account);
    }

    @Override
    public void setLoginPassword(String password) {
        WanAndroidApp.getInstance().getDataManager().setLoginPassword(password);
    }

    public void addDisposible(Disposable disposable){
        if (compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}
