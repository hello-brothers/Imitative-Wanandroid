package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.network.bean.ProjectListData;

/**
 * @Date 2019/9/17
 * @created by lh
 * @Describe:
 */
public interface ProjectPagerDetailContract {
    interface View extends AbstractView{

        void jumpToTheTop();

        void showProjectDetailData(ProjectListData data, boolean isRefresh);
    }

    interface Presenter extends AbstractPresenter<View>{

        void getProjectDetailData(int projectID);

        void refresh();

        void loadMore();
    }
}
