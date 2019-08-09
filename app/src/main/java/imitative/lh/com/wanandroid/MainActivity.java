package imitative.lh.com.wanandroid;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.presenter.BasePresenter;
import imitative.lh.com.wanandroid.presenter.MainPresenter;
import imitative.lh.com.wanandroid.utils.BottomNavigationViewHelper;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;
import imitative.lh.com.wanandroid.view.BaseActivity;
import imitative.lh.com.wanandroid.view.MainContract;
import imitative.lh.com.wanandroid.view.fragment.BaseFragment;
import imitative.lh.com.wanandroid.view.fragment.KnowledgeHierarchyFragment;
import imitative.lh.com.wanandroid.view.fragment.MainPagerFragment;
import imitative.lh.com.wanandroid.view.fragment.NavigationFragment;
import imitative.lh.com.wanandroid.view.fragment.ProjectFragment;
import imitative.lh.com.wanandroid.view.fragment.WxArticleFragment;

public class MainActivity extends BaseActivity<MainPresenter> implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {

    @BindView(R.id.common_toolbar)
    Toolbar home_toolbar;
    @BindView(R.id.home_drawer)
    DrawerLayout home_drawer;
    @BindView(R.id.common_toolbar_title_tv)
    TextView toolbar_title;
    @BindView(R.id.home_nav)
    NavigationView home_nav;
    @BindView(R.id.home_bottom_nav)
    BottomNavigationView home_bottom_nav;
    private TextView tv_login;
    private MainPagerFragment mainPagerFragment;
    private KnowledgeHierarchyFragment knowledgeHierarchyFragment;
    private WxArticleFragment wxArticleFragment;
    private ProjectFragment projectFragment;
    private NavigationFragment navigationFragment;
    private List<BaseFragment> mFragments;
    private int mLastFgIndex;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(home_toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar_title.setText("开发小白");
        StatusBarUtils.immersive(this, home_toolbar);

        home_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
    }

    @Override
    protected void initDataAndEvent() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments = new ArrayList<>();
        if (savedInstanceState == null){
            initPager(Constants.TYPE_MAIN_PAGER);
        }
    }

    @Override
    protected MainPresenter createPresneter() {
        return new MainPresenter();
    }

    private void initPager(int position) {
        initFragments();
        init();
        switchFragment(position);
    }

    private void switchFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BaseFragment mTargetFg = mFragments.get(position);
        BaseFragment mLastFg = mFragments.get(mLastFgIndex);
        mLastFgIndex = position;
        transaction.hide(mLastFg);
        if (!mTargetFg.isAdded()){
            getSupportFragmentManager().beginTransaction().remove(mTargetFg).commitAllowingStateLoss();
            transaction.add(R.id.fragment_group, mTargetFg);
        }
        transaction.show(mTargetFg);
        transaction.commitAllowingStateLoss();

    }

    private void initFragments() {
        mainPagerFragment = MainPagerFragment.getInstance();
        knowledgeHierarchyFragment = KnowledgeHierarchyFragment.getInstance();
        navigationFragment = NavigationFragment.getInstance();
        projectFragment = ProjectFragment.getInstance();
        wxArticleFragment = WxArticleFragment.getInstance();

        mFragments.add(mainPagerFragment);
        mFragments.add(knowledgeHierarchyFragment);
        mFragments.add(wxArticleFragment);
        mFragments.add(navigationFragment);
        mFragments.add(projectFragment);
    }

    private void init() {
        initNavigationView();
        initDrawerLayout();
        initBottomNavigation();
    }

    private void initBottomNavigation() {
//        BottomNavigationViewHelper.disableShiftMode(home_bottom_nav);

        home_bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.tab_main_pager:
                        loadFragment(0, getString(R.string.home_pager));
                        break;
                    case R.id.tab_knowledge_hierarchy:
                        loadFragment(1, getString(R.string.knowledge_hierarchy));
                        break;
                    case R.id.tab_wx_article:
                        loadFragment(2, getString(R.string.wx_article));
                        break;
                    case R.id.tab_navigation:
                        loadFragment(3, getString(R.string.navigation));
                        break;
                    case R.id.tab_project:
                        loadFragment(4, getString(R.string.project));
                        break;
                }
                return true;
            }
        });
    }

    private void loadFragment(int position, String title) {
        switchFragment(position);
        toolbar_title.setText(title);

    }

    private void initNavigationView() {
        home_nav.setNavigationItemSelectedListener(this);
        View headerView = home_nav.getHeaderView(0);
        tv_login = headerView.findViewById(R.id.nav_header_login_tv);

        if (!presenter.getLoginState()){
            showLogoutView();
        }else {
            showLoginView();
        }

    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, home_drawer, home_toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = home_drawer.getChildAt(0);
                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;

                //设置左边菜单滑动后的占据屏幕大小
                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
                mContent.setScaleX(endScale);
                mContent.setScaleY(endScale);
            }
        };
        toggle.syncState();
        home_drawer.addDrawerListener(toggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_usage:
                Toast.makeText(mActivity, "usage", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_search:
                Toast.makeText(mActivity, "search", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (home_drawer!=null){
            home_drawer.closeDrawer(Gravity.START);
        }
        switch (menuItem.getItemId()){

            case R.id.nav_item_wan_android:
                break;
            case R.id.nav_item_collection:
                Toast.makeText(this, "nav_item_collection", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_item_logout:
                Toast.makeText(this, "nav_item_logout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_item_baout:
                Toast.makeText(this, "nav_item_baout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_item_setting:
                Toast.makeText(this, "nav_item_setting", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    /**
     * 回退键回调
     */
    @Override
    public void onBackPressedSupport() {
        if (home_drawer != null && home_drawer.isDrawerOpen(Gravity.START)){
            home_drawer.closeDrawer(Gravity.START);
        }
        if (getSupportFragmentManager().getBackStackEntryCount() >1){
            pop();
        }else {
            ActivityCompat.finishAfterTransition(this);
        }

    }

    /**
     * 展示登录后的界面
     */
    @Override
    public void showLoginView() {

    }

    /**
     * 展示未登录状态的界面
     */
    @Override
    public void showLogoutView() {
        tv_login.setText("login");
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "跳转登陆界面", Toast.LENGTH_SHORT).show();
            }
        });
        if (home_nav == null){
            return;
        }
        home_nav.getMenu().findItem(R.id.nav_item_logout).setVisible(false);
    }
}
