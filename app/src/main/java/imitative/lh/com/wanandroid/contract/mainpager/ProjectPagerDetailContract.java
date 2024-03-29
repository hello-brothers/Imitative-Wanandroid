package imitative.lh.com.wanandroid.contract.mainpager;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;

/**
 * @Date 2019/9/17
 * @created by lh
 * @Describe:
 */
public interface ProjectPagerDetailContract {
    interface View extends AbstractView{

        void jumpToTheTop();

        void showProjectDetailData(EssayListData data, boolean isRefresh);

        void showCancelColletEssay(int position, EssayData essayData);

        void showAddColletEssay(int position, EssayData essayData);
    }

    interface Presenter extends AbstractPresenter<View>{

        void getProjectDetailData(int projectID);

        void refresh();

        void loadMore();

        void cancelColletEssay(int position, EssayData essayData);

        void addColletEssay(int position, EssayData essayData);
    }
}
