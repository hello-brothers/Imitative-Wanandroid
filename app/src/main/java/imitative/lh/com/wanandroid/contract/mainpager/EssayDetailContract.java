package imitative.lh.com.wanandroid.contract.mainpager;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.network.bean.EssayListData;

public interface EssayDetailContract  {
    interface View extends AbstractView{

        void showCancelColletEssay(EssayListData essayListData);

        void showAddColletEssay(EssayListData essayListData);
    }

    interface Presenter extends AbstractPresenter<View>{

        void cancelColletEssay(int id);

        void cancelPageColletEssay(int id);

        void addColletEssay(int id);
    }
}
