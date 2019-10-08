package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.network.bean.EssayData;

public interface CollectionPagerContract {
    interface View extends AbstractView{

        void showCollectionListData(List<EssayData> data);

        void showCancelPageCollectEssay(int position);
    }

    interface Presenter extends AbstractPresenter<View>{

        void getCollectionListData();

        void refresh();

        void cancelPageCollectEssay(int position, EssayData essayData);
    }
}
