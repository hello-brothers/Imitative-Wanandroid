package imitative.lh.com.wanandroid.presenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.contract.mainpager.NavigationContract;
import imitative.lh.com.wanandroid.view.fragment.BaseFragment;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Date 2019/9/16
 * @created by lh
 * @Describe:
 */
public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {
    @Override
    public void getNavigationListData() {
        createDate();
    }

    private void createDate() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            data.add("title " + i);
        }
        Observable.just(data).delay(Constants.delayTime, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<String>>() {
                    @Override
                    public void accept(ArrayList<String> strings) throws Exception {
                        mView.showNavigationListData(data);
                    }
                });
    }
}
