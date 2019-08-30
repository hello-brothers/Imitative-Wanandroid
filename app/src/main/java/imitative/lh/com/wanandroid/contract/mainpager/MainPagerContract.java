package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.view.AbstractView;

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
        void showEssayListView(List data, boolean isRefresh);

        void showBannerData(List data);
    }

    interface Presenter extends AbstractPresenter<View>{

        void autoRefresh();

        void refresh();

        void loadMore();

        void getEssayListData();
    }
}
