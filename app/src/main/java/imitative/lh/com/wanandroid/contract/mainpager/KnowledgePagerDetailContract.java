package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;

public interface KnowledgePagerDetailContract {

    interface View extends AbstractView{

        void showKnowledgeDetailData(List<EssayData> data, boolean isRefresh);

        void showCancelColletEssay(int position, EssayData essayData);

        void showAddColletEssay(int position, EssayData essayData);

    }

    interface Presenter extends AbstractPresenter<View>{

        void getKnowledgeDetailData(int cid);

        void refresh();

        void loadMore();

        void cancelColletEssay(int position, EssayData essayData);

        void addColletEssay(int position, EssayData essayData);
    }
}
