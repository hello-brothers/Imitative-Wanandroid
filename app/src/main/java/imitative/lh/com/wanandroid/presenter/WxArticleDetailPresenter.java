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
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @Date 2019/9/12
 * @created by lh
 * @Describe:
 */
public class WxArticleDetailPresenter extends BasePresenter<WxArticlePagerDetailContract.View> implements WxArticlePagerDetailContract.Presenter {
    @Override
    public void attachView(WxArticlePagerDetailContract.View view) {
        super.attachView(view);
        registerEvent();
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
    public void getWxDetailData() {
        createData(true);
    }

    private void createData(boolean isRefresh) {
        String[] data = new String[10];
        addDisposible(Observable.just(data)
                .delay(Constants.delayTime, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String[], List>() {
                    @Override
                    public List apply(String[] strings) throws Exception {
                        return new ArrayList(Arrays.asList(strings));
                    }
                })
                .subscribe(new Consumer<List>() {
                    @Override
                    public void accept(List list) throws Exception {
                        mView.showWxDetailData(list, isRefresh);
                    }
                }));
    }

    private void registerEvent() {
        addDisposible(RxBus.getDefault().toFlowable(JumpToTheTop.class)
                .subscribe(jumpToTheTop -> mView.jumpToTheTop()));
    }
}
