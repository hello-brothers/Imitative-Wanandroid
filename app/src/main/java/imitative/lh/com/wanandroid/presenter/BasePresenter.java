package imitative.lh.com.wanandroid.presenter;

import imitative.lh.com.wanandroid.view.AbstractView;

public class BasePresenter<T extends AbstractView>  {
    private T view;

    public void attachView(T view){
        this.view = view;
    }

    public void detachView(){
        this.view = null;
    }

    public T getView(){
        return this.view;
    }
}
