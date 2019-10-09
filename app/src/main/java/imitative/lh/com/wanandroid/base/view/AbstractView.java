package imitative.lh.com.wanandroid.base.view;

import imitative.lh.com.wanandroid.network.bean.EssayData;

public interface AbstractView {
    void showLoginView();

    void showLogoutView();

    void showSnackBar(String message);

    void preload();

    void reload();

    void showToast(String message);

    void showNormalView();

    void showLoadingView();

    void showErrorView();

    void showUnloginView();

    void showErrorMsg(String errorMsg);

    void showCancelColletEssay(int position, EssayData essayData);

    void showAddColletEssay(int position, EssayData essayData);

}
