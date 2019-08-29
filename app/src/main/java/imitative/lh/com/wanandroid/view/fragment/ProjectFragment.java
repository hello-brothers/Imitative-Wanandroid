package imitative.lh.com.wanandroid.view.fragment;

import imitative.lh.com.wanandroid.R;

public class ProjectFragment extends BaseFragment {
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
}
