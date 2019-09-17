package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.view.AbstractView;

public interface CollectionPagerContract {
    interface View extends AbstractView{
        void showCollectionListData(List data);
    }

    interface Presenter extends AbstractPresenter<View>{

        void getCollectionListData();

        void refresh();
    }
}
