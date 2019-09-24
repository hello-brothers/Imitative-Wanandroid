package imitative.lh.com.wanandroid.presenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.contract.mainpager.ProjectPagerDetailContract;
import imitative.lh.com.wanandroid.core.event.JumpToTheTop;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.ProjectListData;
import imitative.lh.com.wanandroid.network.util.RxUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Date 2019/9/17
 * @created by lh
 * @Describe:
 */
public class ProjectDetailPresenter extends BasePresenter<ProjectPagerDetailContract.View> implements ProjectPagerDetailContract.Presenter {

    private int currentIndex = 1;
    private int projectID = -1;

    @Override
    public void attachView(ProjectPagerDetailContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addDisposible(RxBus.getDefault().toFlowable(JumpToTheTop.class)
                .subscribe(new Consumer<JumpToTheTop>() {
                    @Override
                    public void accept(JumpToTheTop jumpToTheTop) throws Exception {
                        mView.jumpToTheTop();
                    }
                }));
    }

    @Override
    public void getProjectDetailData(int projectId) {

        this.projectID = projectId;
        createData(true);
    }

    @Override
    public void refresh() {
        currentIndex = 0;
        createData(true);
    }

    @Override
    public void loadMore() {
        currentIndex++;
        createData(false);
    }

    private void createData(boolean isRefresh) {
        if (projectID == -1){
            return;
        }
        addDisposible(manager.getProjectListData(currentIndex, projectID)
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<ProjectListData>(mView) {
                    @Override
                    public void onNext(ProjectListData projectListData) {
                        super.onNext(projectListData);
                        mView.showProjectDetailData(projectListData, isRefresh);
                    }
                }));


    }
}
