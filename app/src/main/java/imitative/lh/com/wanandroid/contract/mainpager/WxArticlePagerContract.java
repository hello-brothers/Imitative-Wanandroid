package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.network.bean.WxAuthor;

/**
 * @Date 2019/9/12
 * @created by lh
 * @Describe:
 */
public interface WxArticlePagerContract {
    interface View extends AbstractView{
        void showWxAuthorListView(List<WxAuthor> authors);
    }

    interface Presenter extends AbstractPresenter<View>{

        void autoRefresh();

        void refresh();

        void getWxAuthorListData();

    }
}
