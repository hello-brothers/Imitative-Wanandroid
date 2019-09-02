package imitative.lh.com.wanandroid.contract.mainpager;

import java.util.List;

import imitative.lh.com.wanandroid.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.view.AbstractView;

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
        void getKnowledgeData();
    }
}
