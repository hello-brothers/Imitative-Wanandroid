package imitative.lh.com.wanandroid.view.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.presenter.AbstractPresenter;

public class WxArticleFragment extends BaseFragment {
    @BindView(R.id.wx_detail_tab_layout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.wx_detail_viewpager)
    ViewPager mViewPager;
    private ArrayList<BaseRootFragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"测试", "android", "学习", "知乎", "安丘一", "腾讯", "世界杯","android", "学习", "知乎", "安丘一", "腾讯", "世界杯"};
    private int currentPage;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wx_article;
    }



    @Override
    protected void initDataAndView() {
        super.initDataAndView();
        initFragment();
        initViewPagerAndTabLayout();

    }

    private void initViewPagerAndTabLayout() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPage = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        slidingTabLayout.setViewPager(mViewPager);
        mViewPager.setCurrentItem(Constants.TAB_ONE);
    }

    private void initFragment() {
        for (int i = 0; i < titles.length; i++) {

            fragments.add(WxDetailArticleFragment.getInstance());
        }

    }

    public static WxArticleFragment getInstance(){
        WxArticleFragment fragment = new WxArticleFragment();
        return fragment;
    }

    @Override
    protected AbstractPresenter createPresenter() {
        return null;
    }
}
