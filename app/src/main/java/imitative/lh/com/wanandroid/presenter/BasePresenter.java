package imitative.lh.com.wanandroid.presenter;

import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.view.AbstractView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<T extends AbstractView> implements AbstractPresenter<T>  {
    private T view;
    private boolean loginState;
    private CompositeDisposable compositeDisposable;
    public void attachView(T view){
        this.view = view;
    }

    public void detachView(){
        this.view = null;
        if (compositeDisposable != null){
            compositeDisposable.clear();
        }
    }

    public T getView(){
        return this.view;
    }

    public boolean getLoginState(){
        return loginState;
    }

    public void setLoginState(boolean loginState){
        this.loginState = loginState;
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
