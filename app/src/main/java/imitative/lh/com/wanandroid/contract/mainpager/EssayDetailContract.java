package imitative.lh.com.wanandroid.contract.mainpager;

import com.tbruyelle.rxpermissions2.RxPermissions;

import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.base.view.AbstractView;
import imitative.lh.com.wanandroid.network.bean.EssayListData;

public interface EssayDetailContract  {
    interface View extends AbstractView{

        void showCancelColletEssay(EssayListData essayListData);

        void showAddColletEssay(EssayListData essayListData);

        void shareEvent();

        void shareError();
    }

    interface Presenter extends AbstractPresenter<View>{

        void cancelColletEssay(int id);

        void cancelPageColletEssay(int id);

        void addColletEssay(int id);

        void shareEventPermissionVerify(RxPermissions rxPermissions);
    }
}
