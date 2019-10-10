package imitative.lh.com.wanandroid.presenter;

import android.Manifest;

import com.tbruyelle.rxpermissions2.RxPermissions;

import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.contract.mainpager.EssayDetailContract;
import imitative.lh.com.wanandroid.core.event.CollectionEvent;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.util.RxUtil;

public class EssayDetailPresenter extends BasePresenter<EssayDetailContract.View> implements EssayDetailContract.Presenter {

    @Override
    public void cancelColletEssay(int id) {
        addDisposible(manager.cancelCollectEssay(id)
                .compose(RxUtil.handleCollectResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData essayListData) {
                        super.onNext(essayListData);
                        RxBus.getDefault().post(new CollectionEvent());
                        mView.showCancelColletEssay(essayListData);
                    }
                }));
    }

    @Override
    public void cancelPageColletEssay(int id) {
        addDisposible(manager.cancelPageCollectEssay(id)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleCollectResult())
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData essayListData) {
                        super.onNext(essayListData);
                        RxBus.getDefault().post(new CollectionEvent());
                        mView.showCancelColletEssay(essayListData);
                    }
                }));
    }

    @Override
    public void addColletEssay(int id) {
        addDisposible(manager.addCollectEssay(id)
                .compose(RxUtil.handleCollectResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData essayListData) {
                        super.onNext(essayListData);
                        RxBus.getDefault().post(new CollectionEvent());
                        mView.showAddColletEssay(essayListData);
                    }
                }));
    }

    @Override
    public void shareEventPermissionVerify(RxPermissions rxPermissions) {
        addDisposible(rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean){
                        mView.shareEvent();
                    }else {
                        mView.shareError();
                    }
                }));
    }
}
