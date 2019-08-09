package imitative.lh.com.wanandroid.view.fragment;



import imitative.lh.com.wanandroid.R;

public class MainPagerFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_pager;
    }

    public static MainPagerFragment getInstance(){
        MainPagerFragment fragment = new MainPagerFragment();
        return fragment;
    }
}
