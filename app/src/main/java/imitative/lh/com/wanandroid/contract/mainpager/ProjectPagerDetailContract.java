package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.view.AbstractView;

/**
 * @Date 2019/9/17
 * @created by lh
 * @Describe:
 */
public interface ProjectPagerDetailContract {
    interface View extends AbstractView{

        void jumpToTheTop();

        void showProjectDetailData(List data, boolean isRefresh);
    }

    interface Presenter extends AbstractPresenter<View>{

        void getProjectDetailData();

        void refresh();

        void loadMore();
    }
}
