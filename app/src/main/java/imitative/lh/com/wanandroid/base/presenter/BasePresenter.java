package imitative.lh.com.wanandroid.base.presenter;

import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.core.DataManager;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.core.event.CollectionEvent;
import imitative.lh.com.wanandroid.core.event.LoginEvent;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.util.RxUtil;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<T extends AbstractView> implements AbstractPresenter<T>  {
    protected T mView;
    private CompositeDisposable compositeDisposable;
    protected DataManager manager = WanAndroidApp.getInstance().getDataManager();
    public void attachView(T view){
        this.mView = view;
        registerEvent();
    }

    private void registerEvent() {
        addDisposible(RxBus.getDefault().toFlowable(LoginEvent.class)
                .filter(loginEvent -> mView!=null)
                .subscribe(loginEvent -> mView.reload()));

        addDisposible(RxBus.getDefault().toFlowable(CollectionEvent.class)
                .filter(collectionEvent -> mView!=null)
                .subscribe(collectionEvent -> mView.reload()));

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

    /**
     * 集成多个界面重复操作添加取消收藏操作
     * @param position
     * @param essayData
     */
    public void cancelColletEssay(int position, EssayData essayData) {
        addDisposible(manager.cancelCollectEssay(essayData.getId())
                .compose(RxUtil.handleCollectResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData essayListData) {
                        super.onNext(essayListData);
                        essayData.setCollect(false);
                        mView.showCancelColletEssay(position, essayData);
                    }
                }));
    }

    public void addColletEssay(int position, EssayData essayData) {
        addDisposible(manager.addCollectEssay(essayData.getId())
                .compose(RxUtil.handleCollectResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData essayListData) {
                        super.onNext(essayListData);
                        essayData.setCollect(true);
                        mView.showAddColletEssay(position, essayData);
                    }
                }));
    }

}
