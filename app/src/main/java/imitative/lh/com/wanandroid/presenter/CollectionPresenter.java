package imitative.lh.com.wanandroid.presenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.contract.mainpager.CollectionPagerContract;
import imitative.lh.com.wanandroid.core.event.CollectionEvent;
import imitative.lh.com.wanandroid.core.event.LoginEvent;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.util.RxUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CollectionPresenter extends BasePresenter<CollectionPagerContract.View> implements CollectionPagerContract.Presenter {

    private int pageIndex = 0;

    @Override
    public void attachView(CollectionPagerContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addDisposible(RxBus.getDefault().toFlowable(LoginEvent.class)
                .filter(loginEvent -> !loginEvent.isLogin())
                .subscribe(loginEvent -> mView.showUnloginView()));
    }

    @Override
    public void getCollectionListData() {
        pageIndex = 0;
        createData();
    }

    @Override
    public void refresh() {
        pageIndex = 0;
        createData();
    }

    @Override
    public void cancelPageCollectEssay(int position, EssayData essayData) {
        addDisposible(manager.cancelPageCollectEssay(essayData.getId())
                .compose(RxUtil.handleCollectResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData essayListData) {
                        super.onNext(essayListData);
                        mView.showCancelPageCollectEssay(position);
                    }
                }));
    }

    private void createData() {

        addDisposible(manager.getCollectionData(pageIndex)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult())
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData essayListData) {
                        super.onNext(essayListData);
                        mView.showCollectionListData(essayListData.getDatas());
                    }
                }));
    }
}
