package imitative.lh.com.wanandroid.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.contract.mainpager.KnowledgePagerContract;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.KnowledgeHierarchyData;
import imitative.lh.com.wanandroid.network.util.RxUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Date 2019/9/2
 * @created by lh
 * @Describe:
 */
public class KnowledgePagerPresenter extends BasePresenter<KnowledgePagerContract.View> implements KnowledgePagerContract.Presenter {

    @Override
    public void getKnowledgeData() {
        addDisposible(manager.getKnowleageHierarchyData()
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<List<KnowledgeHierarchyData>>(mView) {
                    @Override
                    public void onNext(List<KnowledgeHierarchyData> knowledgeHierarchyData) {
                        super.onNext(knowledgeHierarchyData);
                        mView.showKnowledgeList(knowledgeHierarchyData);
                    }
                }));
    }

    @Override
    public void autoRefresh() {

    }

    @Override
    public void refresh() {

    }



}
