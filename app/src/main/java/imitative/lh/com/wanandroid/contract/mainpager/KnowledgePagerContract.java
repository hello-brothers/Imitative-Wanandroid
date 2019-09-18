package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;

/**
 * @Date 2019/9/2
 * @created by lh
 * @Describe:
 */
public interface KnowledgePagerContract {

    interface View extends AbstractView{
        void showKnowledgeList(List data);
    }

    interface Presenter extends AbstractPresenter<View>{

        void autoRefresh();

        void refresh();

        void getKnowledgeData();
    }
}
