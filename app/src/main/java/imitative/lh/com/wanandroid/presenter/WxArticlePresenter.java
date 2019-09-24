package imitative.lh.com.wanandroid.presenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.contract.mainpager.WxArticlePagerContract;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.WxAuthor;
import imitative.lh.com.wanandroid.network.util.RxUtil;
import io.reactivex.Observable;
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
        addDisposible(manager.getWxAuthorTab()
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<List<WxAuthor>>(mView) {
                    @Override
                    public void onNext(List<WxAuthor> wxAuthors) {
                        super.onNext(wxAuthors);
                        mView.showWxAuthorListView(wxAuthors);
                    }
                })
        );

    }


}
