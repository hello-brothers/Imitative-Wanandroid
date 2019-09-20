package imitative.lh.com.wanandroid.presenter;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.contract.mainpager.MainPagerContract;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.util.RxUtil;
import imitative.lh.com.wanandroid.network.bean.BannerData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Date 2019/8/30
 * @created by lh
 * @Describe:
 */
public class MainPagerPresenter extends BasePresenter<MainPagerContract.View> implements MainPagerContract.Presenter {

    private int mPageIndex = 0;
    //默认为刷新数据
    private boolean isfresh = true;

    @Override
    public void autoRefresh() {
        isfresh = true;
        createData();
    }

    @Override
    public void refresh() {
        isfresh = true;
        createData();
    }

    @Override
    public void loadMore() {
        //加载更多
        isfresh = false;
        mPageIndex ++;
        getEssayListData();
    }


    /**
     * 模拟数据
     */
    @SuppressLint("CheckResult")
    private void createData() {
        getEssayListData();
        getBannerData();

    }

    @Override
    public void getEssayListData() {

        addDisposible(manager.getEssayListData(mPageIndex)
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData essayListData) {
                        super.onNext(essayListData);
                        mView.showEssayListView(essayListData, isfresh);
                    }
                }));
    }

    private void getBannerData() {
        addDisposible(manager.getBannerData()
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<List<BannerData>>(mView){
                    @Override
                    public void onNext(List<BannerData> bannerData) {
                        super.onNext(bannerData);
                        mView.showBannerData(bannerData);
                    }
                }));
    }
}
