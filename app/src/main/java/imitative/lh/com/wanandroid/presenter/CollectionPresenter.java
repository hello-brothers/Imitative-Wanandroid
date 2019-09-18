package imitative.lh.com.wanandroid.presenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.presenter.BasePresenter;
import imitative.lh.com.wanandroid.contract.mainpager.CollectionPagerContract;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CollectionPresenter extends BasePresenter<CollectionPagerContract.View> implements CollectionPagerContract.Presenter {
    @Override
    public void getCollectionListData() {
        createData();
    }

    @Override
    public void refresh() {
        createData();
    }

    private void createData() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(i+ "");
        }

        addDisposible(Observable.just(data).delay(Constants.delayTime, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<String>>() {
                    @Override
                    public void accept(ArrayList<String> strings) throws Exception {
                        mView.showCollectionListData(strings);
                    }
                }));
    }
}
