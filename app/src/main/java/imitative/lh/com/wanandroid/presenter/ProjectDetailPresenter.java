package imitative.lh.com.wanandroid.presenter;

import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.contract.mainpager.ProjectPagerDetailContract;
import imitative.lh.com.wanandroid.core.event.JumpToTheTop;
import imitative.lh.com.wanandroid.network.base.BaseObserver;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.util.RxUtil;
import io.reactivex.functions.Consumer;

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

    @Override
    public void cancelColletEssay(int position, EssayData essayData) {
        addDisposible(manager.cancelCollectEssay(essayData.getId())
                .compose(RxUtil.handleCollectResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData essayListData) {
                        super.onNext(essayListData);
                        essayData.setCollect(false);
                        mView.showCancelColletEssay(position, essayData);
                    }
                }));
    }

    @Override
    public void addColletEssay(int position, EssayData essayData) {
        addDisposible(manager.addCollectEssay(essayData.getId())
                .compose(RxUtil.handleCollectResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData essayListData) {
                        super.onNext(essayListData);
                        essayData.setCollect(true);
                        mView.showAddColletEssay(position, essayData);
                    }
                }));
    }

    private void createData(boolean isRefresh) {
        if (projectID == -1){
            return;
        }
        addDisposible(manager.getProjectListData(currentIndex, projectID)
                .compose(RxUtil.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<EssayListData>(mView) {
                    @Override
                    public void onNext(EssayListData projectListData) {
                        super.onNext(projectListData);
                        mView.showProjectDetailData(projectListData, isRefresh);
                    }
                }));


    }
}
