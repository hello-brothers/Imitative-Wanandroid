package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.core.dao.HistoryData;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.HotKey;

/**
 * @Date 2019/9/18
 * @created by lh
 * @Describe:
 */
public interface SearchContract {
    interface View extends AbstractView{

        void showHotKey(List<HotKey> hotKeyList);

        void showSearchView();

        void showSearchData(List<EssayData> dataList, boolean isRefresh);

        void showNoData();

        void showCancelColletEssay(int position, EssayData essayData);

        void showAddColletEssay(int position, EssayData essayData);

        void showRefreshHistoryData(List<HistoryData> historyDataList);
    }

    interface Presenter extends AbstractPresenter<View>{

        void getSearchHotData();

        void getSearchData(String searchStr);

        void loadMore();

        void refresh();

        void addHistoryData(String data);

        void clearAllHistoryData();

        void refreshHistoryData();



    }
}
