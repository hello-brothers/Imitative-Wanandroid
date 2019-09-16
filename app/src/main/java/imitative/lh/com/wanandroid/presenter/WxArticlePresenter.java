package imitative.lh.com.wanandroid.presenter;

import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.contract.mainpager.WxArticlePagerContract;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Date 2019/9/12
 * @created by lh
 * @Describe:
 */
public class WxArticlePresenter extends BasePresenter<WxArticlePagerContract.View> implements WxArticlePagerContract.Presenter {
    @Override
    public void autoRefresh() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void getWxAuthorListData() {
        createData();
    }

    private void createData() {
        String[] titles = new String[]{"测试", "android", "学习", "知乎", "安丘一", "腾讯", "世界杯","android", "学习", "知乎", "安丘一", "腾讯", "世界杯"};
        Observable.just(titles)
                .delay(Constants.delayTime, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String[]>() {
                    @Override
                    public void accept(String[] strings) throws Exception {
                        mView.showWxAuthorListView(titles);
                    }
                });

    }
}
