package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.network.bean.NavigationListData;

/**
 * @Date 2019/9/16
 * @created by lh
 * @Describe:
 */
public interface NavigationContract {
    interface View extends AbstractView{

        void showNavigationListData(List<NavigationListData> data);

        void showRefresh(List<NavigationListData> data);

    }

    interface Presenter extends AbstractPresenter<View>{

        void getNavigationListData();

        void refresh();
    }

}
