package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.network.bean.WxArticalListData;

/**
 * @Date 2019/9/12
 * @created by lh
 * @Describe:
 */
public interface WxArticlePagerDetailContract {
    interface View extends AbstractView{

        void jumpToTheTop();

        void showWxDetailData(List<WxArticalListData.WxArticalData> data, boolean isRefresh);


    }

    interface Presenter extends AbstractPresenter<View>{

        void refresh();

        void loadMore();

        void getWxDetailData(int authorId);


    }
}
