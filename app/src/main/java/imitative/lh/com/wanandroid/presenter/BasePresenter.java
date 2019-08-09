package imitative.lh.com.wanandroid.presenter;

import imitative.lh.com.wanandroid.view.AbstractView;

public class BasePresenter<T extends AbstractView> implements AbstractPresenter<T>  {
    private T view;
    private boolean loginState;

    public void attachView(T view){
        this.view = view;
    }

    public void detachView(){
        this.view = null;
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
}
