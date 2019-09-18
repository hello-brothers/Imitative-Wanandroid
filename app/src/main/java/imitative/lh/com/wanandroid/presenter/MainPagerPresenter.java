package imitative.lh.com.wanandroid.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.contract.mainpager.MainPagerContract;
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

    @Override
    public void autoRefresh() {
        createData(true);
    }

    @Override
    public void refresh() {
        createData(true);
    }

    @Override
    public void loadMore() {
        createData(false);
    }

    @Override
    public void getEssayListData() {
        createData(true);
    }

    /**
     * 模拟数据
     * @param isRefresh 是否是刷新界面操作
     */
    private void createData(boolean isRefresh) {
        List<String> dataList = new ArrayList<>();
        Integer[] imgs = new Integer[]{R.drawable.v0, R.drawable.v1, R.drawable.v2, R.drawable.v3};
        List<Integer> bannerData = Arrays.asList(imgs);

        for (int i = 0; i < 10; i++) {
            dataList.add("1");
        }

        Observable.just(dataList)
                .delay(Constants.delayTime, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        mView.showEssayListView(dataList, isRefresh);
                        mView.showBannerData(bannerData);
                    }

                });

    }
}
