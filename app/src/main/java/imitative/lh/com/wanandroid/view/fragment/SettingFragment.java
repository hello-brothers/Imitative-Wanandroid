package imitative.lh.com.wanandroid.view.fragment;

import imitative.lh.com.wanandroid.R;

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

    public static SettingFragment newInstance(){
        return new SettingFragment();
    }
}