package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;

/**
 * @Date 2019/9/16
 * @created by lh
 * @Describe:
 */
public interface NavigationContract {
    interface View extends AbstractView{
        void showNavigationListData(List data);
    }

    interface Presenter extends AbstractPresenter<View>{
        void getNavigationListData();
    }

}
