package imitative.lh.com.wanandroid.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.contract.mainpager.WxArticlePagerDetailContract;
import imitative.lh.com.wanandroid.core.event.JumpToTheTop;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.WxArticalListData;
import imitative.lh.com.wanandroid.network.util.RxUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

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
                .subscribeWith(new BaseObserver<WxArticalListData>(mView) {
                    @Override
                    public void onNext(WxArticalListData wxArticalData) {
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
