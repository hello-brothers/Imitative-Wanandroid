package imitative.lh.com.wanandroid.contract.mainpager;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;

/**
 * @Date 2019/9/18
 * @created by lh
 * @Describe:
 */
public interface SearchContract {
    interface View extends AbstractView{

        void showSearchView();

        void showSearchData();

        void showNoData();

    }

    interface Presenter extends AbstractPresenter<View>{

        void getSearchData(String searchStr);
    }
}
