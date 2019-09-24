package imitative.lh.com.wanandroid.ui.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.fragment.BaseFragment;
import imitative.lh.com.wanandroid.base.fragment.BaseRootFragment;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.contract.mainpager.WxArticlePagerContract;
import imitative.lh.com.wanandroid.core.event.JumpToTheTop;
import imitative.lh.com.wanandroid.network.bean.WxAuthor;
import imitative.lh.com.wanandroid.presenter.WxArticlePresenter;
import imitative.lh.com.wanandroid.utils.CommonUtils;

public class WxArticleFragment extends BaseFragment<WxArticlePresenter> implements WxArticlePagerContract.View {
    @BindView(R.id.wx_detail_tab_layout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.wx_detail_viewpager)
    ViewPager mViewPager;
    private ArrayList<BaseRootFragment> fragments = new ArrayList<>();
    private int currentPage;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wx_article;
    }



    @Override
    protected void initDataAndView() {
        super.initDataAndView();
        if (presenter != null && slidingTabLayout.getVisibility() == View.INVISIBLE){
            presenter.getWxAuthorListData();
        }
        if (CommonUtils.isNetworkConnected()){
            showLoadingView();
        }

    }

    private void initViewPagerAndTabLayout(List<WxAuthor> authorsList) {
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return authorsList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return authorsList.get(position).getName();

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


    public static WxArticleFragment getInstance(){
        WxArticleFragment fragment = new WxArticleFragment();
        return fragment;
    }

    @Override
    protected WxArticlePresenter createPresenter() {
        return new WxArticlePresenter();
    }

    @Override
    public void showWxAuthorListView(List<WxAuthor> authorsList) {
        if (presenter.getCurrentPage() == Constants.TYPE_WX_ARTICLE){
            setChildViewVisibility(View.VISIBLE);
        }else {
            setChildViewVisibility(View.INVISIBLE);
        }
        fragments.clear();
        initFragments(authorsList);
        initViewPagerAndTabLayout(authorsList);
        setChildViewVisibility(View.VISIBLE);
        showNormalView();

    }

    @Override
    public void reload() {
        super.reload();
        if (presenter != null && (slidingTabLayout.getVisibility() == View.INVISIBLE)){
            presenter.getWxAuthorListData();
        }
    }


    private void initFragments(List<WxAuthor> authorsList) {
        for (int i = 0; i < authorsList.size(); i++) {
            fragments.add(WxDetailArticleFragment.getInstance(authorsList.get(i).getId()));
        }
    }

    @Override
    public void jumpToTheTop() {
        if (_mActivity != null){
            RxBus.getDefault().post(new JumpToTheTop());
        }
    }
    private void setChildViewVisibility(int visible) {
        slidingTabLayout.setVisibility(visible);
        mViewPager.setVisibility(visible);
    }

}
