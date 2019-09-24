package imitative.lh.com.wanandroid.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.contract.mainpager.NavigationContract;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.NavigationListData;
import imitative.lh.com.wanandroid.network.util.RxUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Date 2019/9/16
 * @created by lh
 * @Describe:
 */
public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {
    @Override
    public void getNavigationListData() {
        createDate();
    }

    private void createDate() {

        addDisposible(manager.getNavigationListData()
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .filter(navigationListData -> mView != null)
                .subscribeWith(new BaseObserver<List<NavigationListData>>(mView) {
                    @Override
                    public void onNext(List<NavigationListData> navigationListData) {
                        super.onNext(navigationListData);
                        mView.showNavigationListData(navigationListData);
                    }
                }));
    }
}
