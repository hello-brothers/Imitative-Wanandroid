package imitative.lh.com.wanandroid.view.fragment;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.presenter.AbstractPresenter;

/**
 * @Date 2019/8/28
 * @created by lh
 * @Describe:
 */
public class SettingFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting_pager;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDataAndView() {

    }

    public static SettingFragment newInstance(){
        return new SettingFragment();
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }
}
