package imitative.lh.com.wanandroid.view.fragment;

import imitative.lh.com.wanandroid.R;

public class WxArticleFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wx_article;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDataAndView() {

    }

    public static WxArticleFragment getInstance(){
        WxArticleFragment fragment = new WxArticleFragment();
        return fragment;
    }
}
