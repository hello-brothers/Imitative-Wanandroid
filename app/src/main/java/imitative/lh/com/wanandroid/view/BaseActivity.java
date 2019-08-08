package imitative.lh.com.wanandroid.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import imitative.lh.com.wanandroid.presenter.BasePresenter;

public abstract class BaseActivity<T extends BasePresenter> extends AbstractSimpleActivity implements AbstractView{
    private T presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewCreated() {
        presenter = createPresneter();
//        if (presenter == null){
//            throw new IllegalStateException("presenter is null!");
//        }
        if (presenter != null){
            presenter.attachView(this);
        }
    }

    protected abstract T createPresneter();

    @Override
    protected void onDestroy() {
        if (presenter!=null){
            presenter.detachView();
            presenter = null;
        }
        super.onDestroy();
    }
}
