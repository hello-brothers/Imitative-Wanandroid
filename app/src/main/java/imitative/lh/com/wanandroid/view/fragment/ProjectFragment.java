package imitative.lh.com.wanandroid.view.fragment;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.presenter.AbstractPresenter;

public class ProjectFragment extends BaseRootFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDataAndView() {

    }

    public static ProjectFragment getInstance(){
        ProjectFragment fragment = new ProjectFragment();
        return fragment;
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }
}
