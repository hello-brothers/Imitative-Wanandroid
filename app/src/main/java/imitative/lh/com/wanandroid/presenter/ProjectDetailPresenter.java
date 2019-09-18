package imitative.lh.com.wanandroid.presenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.contract.mainpager.ProjectPagerDetailContract;
import imitative.lh.com.wanandroid.core.event.JumpToTheTop;
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
    public void getProjectDetailData() {
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

    private void createData(boolean isRefresh) {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(i + "");
        }
        addDisposible(Observable.just(data)
                .delay(Constants.delayTime, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<String>>() {
                    @Override
                    public void accept(ArrayList<String> strings) throws Exception {
                        mView.showProjectDetailData(strings, isRefresh);
                    }
                }));
    }
}
