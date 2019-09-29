package imitative.lh.com.wanandroid.presenter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.contract.mainpager.MainPagerContract;
import imitative.lh.com.wanandroid.core.event.LoginEvent;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.base.BaseResponse;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.bean.LoginData;
import imitative.lh.com.wanandroid.network.util.RxUtil;
import imitative.lh.com.wanandroid.network.bean.BannerData;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * @Date 2019/8/30
 * @created by lh
 * @Describe:
 */
public class MainPagerPresenter extends BasePresenter<MainPagerContract.View> implements MainPagerContract.Presenter {

    /***默认页码***/
    private int mPageIndex = 0;

    /**
     * 首页 置顶文章
     * @return
     */
    private Observable<List<EssayData>> getTopEssayAbservable() {
        return manager.getTopArticalListData()
                .compose(RxUtil.handleResult());
    }

    /**
     * 首页文章列表 不包括置顶
     * @return
     */
    private Observable<List<EssayData>> getEssayListWithoutTopObservable() {
        return manager.getEssayListData(mPageIndex)
                .compose(RxUtil.handleResult())
                .flatMap(essayListData -> Observable.just(essayListData.getDatas()));
    }

    /**
     * 自动登录
     * @return
     */
    private Observable<LoginData> getAutoLoginData(){
        return manager.getLoginData(getLoginAccount(), getLoginPassword())
                .compose(RxUtil.handleResult());

    }


    @Override
    public void loadData() {
        mPageIndex = 0;
        if (manager.isLoadTopEssayData()) {
            canAutoLogin();
            getEssayListDataWithTop();
        } else {
            getEssayListDataWithNoTop();
        }
    }

    private void canAutoLogin() {
        addDisposible(getAutoLoginData().compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<LoginData>(mView) {
                    @Override
                    public void onNext(LoginData loginData) {
                        super.onNext(loginData);
                        manager.setLoginState(true);
                        manager.setLoginAccount(loginData.getUsername());
                        manager.setLoginPassword(loginData.getPassword());
                        RxBus.getDefault().post(new LoginEvent(true));
                        Log.i("TAG", "onNext: " + "autologinsuccess");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErrorMsg(WanAndroidApp.getInstance().getString(R.string.LOGIN_ERROR));
                        manager.setLoginPassword("");
                        manager.setLoginAccount("");
                        manager.setLoginState(false);
                        RxBus.getDefault().post(new LoginEvent(false));

                    }
                }));
    }

    @Override
    public void refresh() {
        mPageIndex = 0;
        //判断是否加载热门信息
        if (manager.isLoadTopEssayData()) {
            getEssayListDataWithTop();
        } else {
            getEssayListDataWithNoTop();
        }
    }

    @Override
    public void loadMore() {
        mPageIndex++;
        addDisposible(manager.getEssayListData(mPageIndex)
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<EssayListData>(mView){
                    @Override
                    public void onNext(EssayListData essayListData) {
                        super.onNext(essayListData);
                        mView.showLoadMoreView(essayListData.getDatas());
                    }
                }));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getEssayListDataWithTop() {
        Observable<List<EssayData>>     topAbserver         = getTopEssayAbservable();
        Observable<List<EssayData>>     essayObserver       = getEssayListWithoutTopObservable();
        Observable<List<BannerData>>    bannerObservable    = getBannerObservable().compose(RxUtil.handleResult());

       Observable.zip(topAbserver, essayObserver, bannerObservable, this::createResponseMap)
               .compose(RxUtil.rxSchedulerHelper())
               .subscribeWith(new BaseObserver<HashMap<String, Object>>(mView) {
                   @Override
                   public void onNext(HashMap<String, Object> map) {
                       super.onNext(map);
                       List<EssayData>  topData         = CommonUtils.cast(map.get(Constants.ESSAY_TOPDATA));
                       List<EssayData>  essayDataList   = CommonUtils.cast(map.get(Constants.ESSAYLIST_DATA));
                       List<BannerData> bannerData      = CommonUtils.cast(map.get(Constants.BANNER_DATA));

                       if (topData != null && essayDataList != null){
                           for (EssayData topDatum : topData) {
                               topDatum.setItemtype(EssayData.TYPE_TOP);
                           }
                           for (EssayData essayData : essayDataList) {
                               essayData.setItemtype(EssayData.TYPE_NORMAL);
                           }
                           topData.addAll(essayDataList);
                           mView.showEssayList(topData);
                       }
                       if (bannerData != null){
                           mView.showBannerData(bannerData);
                       }
                   }
               });
    }

    @NonNull
    private HashMap<String, Object> createResponseMap(List<EssayData> topEssayAbservable,
                                                      List<EssayData> essayListWithoutTopObservable,
                                                      List<BannerData> bannerObservable) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constants.ESSAY_TOPDATA,    topEssayAbservable);
        map.put(Constants.ESSAYLIST_DATA,   essayListWithoutTopObservable);
        map.put(Constants.BANNER_DATA,      bannerObservable);
        return map;
    }

    @Override
    public void getEssayListDataWithNoTop() {
        addDisposible(manager.getEssayListData(mPageIndex)
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData essayListData) {
                        super.onNext(essayListData);
                        mView.showEssayList(essayListData.getDatas());
                    }
                }));
    }

    @Override
    public void getBannerData() {
        addDisposible(getBannerObservable()
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

    private Observable<BaseResponse<List<BannerData>>> getBannerObservable() {
        return manager.getBannerData();
    }
}
