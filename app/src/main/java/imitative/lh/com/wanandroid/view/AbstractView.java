package imitative.lh.com.wanandroid.view;

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

}
