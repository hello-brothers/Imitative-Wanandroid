package imitative.lh.com.wanandroid.ui.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.fragment.BaseFragment;
import imitative.lh.com.wanandroid.base.fragment.BaseRootFragment;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.contract.mainpager.ProjectPagerContracr;
import imitative.lh.com.wanandroid.core.event.JumpToTheTop;
import imitative.lh.com.wanandroid.presenter.ProjectPresenter;
import imitative.lh.com.wanandroid.utils.CommonUtils;

public class ProjectFragment extends BaseFragment<ProjectPresenter> implements ProjectPagerContracr.View {
    @BindView(R.id.project_slidTab)
    SlidingTabLayout project_Tab;
    @BindView(R.id.project_detail_pager)
    ViewPager projectDetailPager;
    @BindView(R.id.normal_view)
    LinearLayout normal_view;
    private ArrayList<BaseRootFragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDataAndView() {
        super.initDataAndView();
        if (presenter != null){
            presenter.getProjectListData();
        }
        if (CommonUtils.isNetworkConnected()){
            showLoadingView();
        }
    }

    public static ProjectFragment getInstance(){
        ProjectFragment fragment = new ProjectFragment();
        return fragment;
    }

    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter();
    }

    @Override
    public void showProjectListData(List data) {
        fragments.clear();
        initFragment(data);
        initViewPagerAndTabLayout(data);
        showNormalView();
    }

    private void initFragment(List data) {
        for (int i = 0; i < data.size(); i++) {
            fragments.add(ProjectDetailFragment.getInstance());
        }
    }

    private void initViewPagerAndTabLayout(List data) {
        projectDetailPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return data.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return (CharSequence) data.get(position);
            }
        });

        project_Tab.setViewPager(projectDetailPager);
        project_Tab.setCurrentTab(Constants.TAB_ONE);
    }

    @Override
    public void reload() {
        super.reload();
        if (presenter != null && normal_view.getVisibility() == View.VISIBLE){
            presenter.getProjectListData();
        }
    }

    @Override
    public void jumpToTheTop() {
        super.jumpToTheTop();
        RxBus.getDefault().post(new JumpToTheTop());
    }
}
