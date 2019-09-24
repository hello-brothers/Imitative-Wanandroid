package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.network.bean.ProjectTab;

/**
 * @Date 2019/9/17
 * @created by lh
 * @Describe:
 */
public interface ProjectPagerContracr {
    interface View extends AbstractView{
        void showProjectListData(List<ProjectTab> data);
    }

    interface Presenter extends AbstractPresenter<View>{

        void autoRefresh();

        void refresh();

        void getProjectListData();
    }
}
