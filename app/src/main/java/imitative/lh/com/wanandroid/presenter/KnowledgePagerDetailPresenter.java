package imitative.lh.com.wanandroid.presenter;

import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.contract.mainpager.KnowledgePagerDetailContract;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.util.RxUtil;

public class KnowledgePagerDetailPresenter extends BasePresenter<KnowledgePagerDetailContract.View> implements KnowledgePagerDetailContract.Presenter {


    private int pageIndex = 0;
    private int id;

    @Override
    public void getKnowledgeDetailData(int cid) {
        this.id = cid;
        createData(true);
    }

    @Override
    public void refresh() {
        pageIndex = 0;
        createData(true);
    }

    @Override
    public void loadMore() {
        pageIndex++;
        createData(false);
    }

    @Override
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

    @Override
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

    private void createData(boolean isRefresh) {
        addDisposible(manager.getKnowledagDetailListData(pageIndex, id)
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData essayListData) {
                        super.onNext(essayListData);
                        mView.showKnowledgeDetailData(essayListData.getDatas(), isRefresh);
                    }
                }));
    }
}
