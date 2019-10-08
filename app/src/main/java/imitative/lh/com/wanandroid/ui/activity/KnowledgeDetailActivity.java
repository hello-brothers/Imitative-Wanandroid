package imitative.lh.com.wanandroid.ui.activity;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.activity.BaseActivity;
import imitative.lh.com.wanandroid.base.fragment.BaseRootFragment;
import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.network.bean.KnowledgeHierarchyData;
import imitative.lh.com.wanandroid.ui.fragment.KnowledgeHierarchyDetailFragment;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;

public class KnowledgeDetailActivity extends BaseActivity {

    @BindView(R.id.common_toolbar)
    Toolbar toolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView title;
    private String know_title;
    @BindView(R.id.knowledge_detail_tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.knowledge_detail_viewpager)
    ViewPager viewPager;
    private KnowledgeHierarchyData knowledgeData;
    private ArrayList<BaseRootFragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getBoundData();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected AbstractPresenter createPresneter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge_detail;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
        StatusBarUtils.immersive(this, toolbar, false);
    }

    @Override
    protected void initDataAndEvent() {
        if (knowledgeData == null){
            return;
        }
        title.setText(knowledgeData.getName());

        initFragments();
        initViewPagerAndTabLayout();
    }

    private void getBoundData() {
        Bundle bundle = getIntent().getExtras();
        know_title = bundle.getString(Constants.KNOW_TITLE);
        knowledgeData = (KnowledgeHierarchyData) getIntent().getSerializableExtra(Constants.ARG_PARAM1);
    }

    private void initFragments() {
        for (int i = 0; i < knowledgeData.getChildren().size(); i++) {
            fragments.add(KnowledgeHierarchyDetailFragment.getInstance(knowledgeData.getChildren().get(i).getId()));
        }
    }

    private void initViewPagerAndTabLayout() {

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return knowledgeData.getChildren().size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                String name = knowledgeData.getChildren().get(position).getName();
                return name;
            }
        });

        tabLayout.setViewPager(viewPager);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setCurrentItem(Constants.TAB_ONE);

    }
}
