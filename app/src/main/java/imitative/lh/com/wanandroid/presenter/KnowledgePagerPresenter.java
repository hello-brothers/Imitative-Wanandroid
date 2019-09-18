package imitative.lh.com.wanandroid.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.contract.mainpager.KnowledgePagerContract;
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
        createDate();
    }

    @Override
    public void autoRefresh() {
        createDate();
    }

    @Override
    public void refresh() {
        createDate();
    }

    private void createDate() {
        ArrayList<String> datas = new ArrayList<>();
        datas.clear();
        for (int i = 0; i < 10; i++) {
            datas.add("22");
        }
        Observable.just(datas)
                .delay(Constants.delayTime,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        mView.showKnowledgeList(strings);
                    }

                });
    }



}
