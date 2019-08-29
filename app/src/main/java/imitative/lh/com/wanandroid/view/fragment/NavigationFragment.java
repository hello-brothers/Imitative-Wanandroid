package imitative.lh.com.wanandroid.view.fragment;

import imitative.lh.com.wanandroid.R;

public class NavigationFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDataAndView() {

    }

    public static NavigationFragment getInstance(){
        NavigationFragment fragment = new NavigationFragment();
        return fragment;
    }
}
