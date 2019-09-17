package imitative.lh.com.wanandroid.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.contract.mainpager.ProjectPagerContracr;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @Date 2019/9/17
 * @created by lh
 * @Describe:
 */
public class ProjectPresenter extends BasePresenter<ProjectPagerContracr.View> implements ProjectPagerContracr.Presenter {

    @Override
    public void autoRefresh() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void getProjectListData() {
        createData();
    }

    private void createData() {
        String[] titles = {"完整项目", "跨平台应用", "资源聚合类", "动画", "项目基础功能", "网络下载"};
        addDisposible(Observable.just(titles)
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
                        mView.showProjectListData(list);
                    }
                }));
    }
}
