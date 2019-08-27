package imitative.lh.com.wanandroid.view;

public interface AbstractView {
    void showLoginView();

    void showLogoutView();

    void showSnackBar(String message);

    void showToast(String message);
}
