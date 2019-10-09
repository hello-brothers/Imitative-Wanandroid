package imitative.lh.com.wanandroid.presenter;

import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.contract.mainpager.WxArticlePagerDetailContract;
import imitative.lh.com.wanandroid.core.event.CollectionEvent;
import imitative.lh.com.wanandroid.core.event.JumpToTheTop;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.util.RxUtil;

/**
 * @Date 2019/9/12
 * @created by lh
 * @Describe:
 */
public class WxArticleDetailPresenter extends BasePresenter<WxArticlePagerDetailContract.View> implements WxArticlePagerDetailContract.Presenter {
    private int currentIndex = 0;
    private int authorID = -1;

    @Override
    public void attachView(WxArticlePagerDetailContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    @Override
    public void refresh() {
        if (authorID == -1){
            return;
        }
        createData(true);
    }

    @Override
    public void loadMore() {
        if (authorID == 0){
            return;
        }
        currentIndex++;
        createData(false);
    }

    @Override
    public void getWxDetailData(int authorId) {
        this.authorID = authorId;


        createData(true);
    }


    private void createData(boolean isRefresh) {
        addDisposible(manager.getWxAuthorListData(authorID, currentIndex)
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .filter(wxArticalData -> mView != null)
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData wxArticalData) {
                        super.onNext(wxArticalData);
                        if (wxArticalData == null || wxArticalData.getDatas() == null){
                            mView.showErrorView();
                        }
                        mView.showWxDetailData(wxArticalData.getDatas(), isRefresh);
                    }
                }));
    }

    private void registerEvent() {
        addDisposible(RxBus.getDefault().toFlowable(JumpToTheTop.class)
                .subscribe(jumpToTheTop -> mView.jumpToTheTop()));
    }
}
