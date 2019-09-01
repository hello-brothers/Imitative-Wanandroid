package imitative.lh.com.wanandroid.presenter;

import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.core.DataManager;
import imitative.lh.com.wanandroid.view.AbstractView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<T extends AbstractView> implements AbstractPresenter<T>  {
    protected T mView;
    private CompositeDisposable compositeDisposable;
    private DataManager manager = WanAndroidApp.getInstance().getDataManager();
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
        return manager.getLoginState();
    }

    @Override
    public String getLoginAccount() {
        return manager.getLoginAccount();
    }

    @Override
    public String getLoginPassword() {
        return manager.getLoginPassword();
    }

    @Override
    public int getCurrentPage() {
        return manager.getCurrentPage();
    }

    @Override
    public void setCurrentPage(int index) {
        manager.setCurrentPage(index);
    }

    public void setLoginState(boolean loginState){
        manager.setLoginState(loginState);
    }

    @Override
    public void setLoginAccount(String account) {
        manager.setLoginAccount(account);
    }

    @Override
    public void setLoginPassword(String password) {
        manager.setLoginPassword(password);
    }

    public void addDisposible(Disposable disposable){
        if (compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}
