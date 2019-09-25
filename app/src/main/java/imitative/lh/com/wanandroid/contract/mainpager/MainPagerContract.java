package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.network.bean.BannerData;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;

/**
 * @Date 2019/8/29
 * @created by lh
 * @Describe:
 */
public interface MainPagerContract {
    interface View extends AbstractView{
        /**
         * 展示文章列表数据
         */
        void showEssayList(List<EssayData> data);

        void showBannerData(List<BannerData> data);

        void showLoadMoreView(List<EssayData> data);
    }

    interface Presenter extends AbstractPresenter<View>{

        void loadData();

        void refresh();

        void loadMore();

        void getEssayListDataWithTop();

        void getEssayListDataWithNoTop();

        void getBannerData();
    }
}
