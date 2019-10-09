package imitative.lh.com.wanandroid.presenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.contract.mainpager.SearchContract;
import imitative.lh.com.wanandroid.core.dao.HistoryData;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.bean.HotKey;
import imitative.lh.com.wanandroid.network.util.RxUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Date 2019/9/18
 * @created by lh
 * @Describe:
 */
public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {
    private String key;
    private int pageIndex = 0;


    @Override
    public void attachView(SearchContract.View view) {
        super.attachView(view);

    }

    @Override
    public void getSearchHotData() {
        addDisposible(manager.getSearchHotKey()
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<List<HotKey>>(mView) {
                    @Override
                    public void onNext(List<HotKey> hotKeys) {
                        super.onNext(hotKeys);
                        mView.showHotKey(hotKeys);
                    }
                }));
    }

    @Override
    public void getSearchData(String searchStr) {
        this.key = searchStr;

        createData(true);
    }

    @Override
    public void loadMore() {
        createData(false);
    }

    @Override
    public void refresh() {
        createData(true);
    }

    @Override
    public void addHistoryData(String data) {
        manager.addHistoryData(data);
    }

    @Override
    public void clearAllHistoryData() {
        manager.clearAllHistoryData();
    }

    @Override
    public void refreshHistoryData() {
        List<HistoryData> historyDataList = manager.loadAllHistoryData();
        mView.showRefreshHistoryData(historyDataList);
    }


    private void createData(boolean isRefresh) {

        addDisposible(manager.getSearchData(pageIndex, key)
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData essayListData) {
                        super.onNext(essayListData);
                        if (essayListData.getDatas().size() == 0){
                            mView.showNoData();
                        }else {
                            addHistoryData(key);
                            refreshHistoryData();
                            mView.showSearchData(essayListData.getDatas(), isRefresh);
                        }
                    }
                }));

    }


}
