package imitative.lh.com.wanandroid.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.core.event.LoginEvent;
import imitative.lh.com.wanandroid.presenter.MainPresenter;
import imitative.lh.com.wanandroid.utils.CommonAlertDialog;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;
import imitative.lh.com.wanandroid.base.activity.BaseActivity;
import imitative.lh.com.wanandroid.contract.mainpager.MainContract;
import imitative.lh.com.wanandroid.base.fragment.AbstractSimpleFragment;
import imitative.lh.com.wanandroid.base.fragment.BaseRootFragment;
import imitative.lh.com.wanandroid.ui.fragment.CollectionFragment;
import imitative.lh.com.wanandroid.ui.fragment.KnowledgeHierarchyFragment;
import imitative.lh.com.wanandroid.ui.fragment.MainPagerFragment;
import imitative.lh.com.wanandroid.ui.fragment.NavigationFragment;
import imitative.lh.com.wanandroid.ui.fragment.ProjectFragment;
import imitative.lh.com.wanandroid.ui.fragment.SettingFragment;
import imitative.lh.com.wanandroid.ui.fragment.WxArticleFragment;

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
    @BindView(R.id.main_floating_action_btn)
    FloatingActionButton mFloatingActionButton;

    private MainPagerFragment                       mainPagerFragment;
    private KnowledgeHierarchyFragment              knowledgeHierarchyFragment;
    private WxArticleFragment                       wxArticleFragment;
    private ProjectFragment                         projectFragment;
    private NavigationFragment                      navigationFragment;
    private TextView                                tv_login;
    private List<AbstractSimpleFragment>                      mFragments;
    private int                                     mLastFgIndex;

    private static final int LOGIN_COLLECT_CODE     = 101;
    private Menu mMenu;
    private boolean isNeedShowMenu;


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
        toolbar_title.setText(R.string.main_title);
        StatusBarUtils.immersive(this, home_toolbar, false);
        home_toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initDataAndEvent() {
        presenter.registerEvent();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments = new ArrayList<>();
        if (savedInstanceState == null){
            initPager(false, Constants.TYPE_MAIN_PAGER);
        }else {
            initPager(true, Constants.TYPE_MAIN_PAGER);
        }
    }

    @Override
    protected MainPresenter createPresneter() {
        return new MainPresenter();
    }

    /**
     * 初始化界面以及设置一个默认的显示界面
     * @param position 显示界面序号
     *
     */
    private void initPager(boolean isReCreate, int position) {
        initFragments(isReCreate);
        init();
        switchFragment(position);
    }

    /**
     * 初始化fragment
     */
    private void initFragments(boolean isReCreate) {
        mainPagerFragment           = MainPagerFragment.getInstance(isReCreate);              //首页
        knowledgeHierarchyFragment  = KnowledgeHierarchyFragment.getInstance();     //知识体系
        wxArticleFragment           = WxArticleFragment.getInstance();              //公众号
        navigationFragment          = NavigationFragment.getInstance();             //导航
        projectFragment             = ProjectFragment.getInstance();                //项目

        CollectionFragment collectionFragment   = CollectionFragment.newInstance(); //收藏
        SettingFragment settingFragment         = SettingFragment.newInstance();    //设置

        mFragments.add(mainPagerFragment);
        mFragments.add(knowledgeHierarchyFragment);
        mFragments.add(wxArticleFragment);
        mFragments.add(navigationFragment);
        mFragments.add(projectFragment);
        mFragments.add(collectionFragment);
        mFragments.add(settingFragment);
    }

    /**
     * 初始化侧边栏以及导航、底部栏导航
     */
    private void init() {
        initNavigationView();
        initDrawerLayout();
        initBottomNavigation();
    }

    /**
     * @see Constants#TYPE_MAIN_PAGER
     * @see Constants#TYPE_KNOWLEDGE
     * @see Constants#TYPE_WX_ARTICLE
     * @see Constants#TYPE_NAVIGATION
     * @see Constants#TYPE_PROJECT
     * @see Constants#TYPE_COLLECT
     * @see Constants#TYPE_SETTING
     * @param position
     */
    @SuppressLint("RestrictedApi")
    private void switchFragment(int position) {
        if (position == Constants.TYPE_WX_ARTICLE){
//            home_toolbar.setVisibility(View.GONE);
        }else {
            home_toolbar.setVisibility(View.VISIBLE);

        }
        if (position > Constants.TYPE_PROJECT){
            isNeedShowMenu = false;
            home_bottom_nav.setVisibility(View.INVISIBLE);
            mFloatingActionButton.setVisibility(View.GONE);
        }else {
            isNeedShowMenu = true;
            home_bottom_nav.setVisibility(View.VISIBLE);
            mFloatingActionButton.setVisibility(View.VISIBLE);
        }

        checkOptionMenu();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        AbstractSimpleFragment mTargetFg          = mFragments.get(position);
        AbstractSimpleFragment mLastFg            = mFragments.get(mLastFgIndex);
        mLastFgIndex = position;
        transaction.hide(mLastFg);
        if (!mTargetFg.isAdded()){
            getSupportFragmentManager().beginTransaction().remove(mTargetFg).commitAllowingStateLoss();
            transaction.add(R.id.fragment_group, mTargetFg);
        }
        transaction.show(mTargetFg);
        transaction.commitAllowingStateLoss();
    }

    private void initBottomNavigation() {
//        BottomNavigationViewHelper.disableShiftMode(home_bottom_nav);
        home_bottom_nav.setOnNavigationItemSelectedListener( menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.tab_main_pager:
                    loadFragment(0, getString(R.string.home_pager), mainPagerFragment, Constants.TYPE_MAIN_PAGER);
                    break;
                case R.id.tab_knowledge_hierarchy:
                    loadFragment(1, getString(R.string.knowledge_hierarchy), knowledgeHierarchyFragment, Constants.TYPE_KNOWLEDGE);
                    break;
                case R.id.tab_wx_article:
                    loadFragment(2, getString(R.string.wx_article), wxArticleFragment, Constants.TYPE_WX_ARTICLE);
                    break;
                case R.id.tab_navigation:
                    loadFragment(3, getString(R.string.navigation), navigationFragment, Constants.TYPE_NAVIGATION);
                    break;
                case R.id.tab_project:
                    loadFragment(4, getString(R.string.project), projectFragment, Constants.TYPE_PROJECT);
                    break;
            }
            return true;
        });
    }

    private void loadFragment(int position, String title, BaseRootFragment fragment, int index) {
        toolbar_title.setText(title);
        switchFragment(position);
        presenter.setCurrentPage(index);
        fragment.reload();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.nav_item_wan_android:
                startMainPager();
                break;
            case R.id.nav_item_collection:
                startCollectFragment();
//                if (presenter.getLoginState()){
//                    startCollectFragment();
//                    return true;
//                }else {
//                    startActivityForResult(new Intent(this, LoginActivity.class), LOGIN_COLLECT_CODE);
//                    CommonUtils.showMessage(this, getString(R.string.login_first));
//                    return false;
//                }
                break;
            case R.id.nav_item_logout:
                if (presenter.getLoginState()){
                    logout();
                    return false;
                }
                break;
            case R.id.nav_item_baout:
                startActivity(new Intent(this, AboutUsActivity.class));
                return false;
            case R.id.nav_item_setting:
                startSettingFragment();
                break;
        }
        return true;
    }

    private void startSettingFragment() {
        toolbar_title.setText(getString(R.string.setting));
        closeDrawer();
        switchFragment(Constants.TYPE_SETTING);
    }

    private void startCollectFragment() {
        toolbar_title.setText(getString(R.string.collection));
        closeDrawer();
        switchFragment(Constants.TYPE_COLLECT);
    }

    private void startMainPager() {
        toolbar_title.setText(R.string.main_title);
        home_bottom_nav.setSelectedItemId(R.id.nav_item_wan_android);
        switchFragment(Constants.TYPE_MAIN_PAGER);
        closeDrawer();
    }

    public void closeDrawer(){
        if (home_drawer!=null){
            home_drawer.closeDrawer(Gravity.START);
        }
    }

    /**
     * 回退键回调
     */
    @Override
    public void onBackPressedSupport() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    /**
     * 展示登录后的界面
     */
    @Override
    public void showLoginView() {
        if (home_nav == null){
            return;
        }

        tv_login.setText(WanAndroidApp.getInstance().getDataManager().getLoginAccount());
        tv_login.setOnClickListener(null);
        home_nav.getMenu().findItem(R.id.nav_item_logout).setVisible(true);
    }

    /**
     * 展示未登录状态的界面
     */
    @Override
    public void showLogoutView() {
        tv_login.setText(R.string.login_text);
        tv_login.setOnClickListener(v ->{
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(R.anim.right_to_left_enter, R.anim.right_to_left_exit);
        });
        if (home_nav == null){
            return;
        }
        home_nav.getMenu().findItem(R.id.nav_item_logout).setVisible(false);
    }

    private void logout() {
        CommonAlertDialog.newInstance().showDialog(this,
                getString(R.string.logout_tint), getString(R.string.query_logout), getString(R.string.no), getString(R.string.ok),
                v -> CommonAlertDialog.newInstance().cancelDialog(true),
                v -> presenter.logout());
    }

    @Override
    public void showLogoutSuccess() {
        CommonAlertDialog.newInstance().cancelDialog(true);
//        startActivity(new Intent(this, LoginActivity.class));
        adjustFragment();
        RxBus.getDefault().post(new LoginEvent(false));
    }

    @OnClick(R.id.main_floating_action_btn)
    void OnClick(View view){
        switch (view.getId()){
            case R.id.main_floating_action_btn:
                jumpToTheTop();
                break;
        }
    }

    private void jumpToTheTop() {
        if (presenter == null){
            return;
        }

        switch (presenter.getCurrentPage()){
            case Constants.TYPE_MAIN_PAGER:
                mainPagerFragment.jumpToTheTop();
                break;
            case Constants.TYPE_KNOWLEDGE:
                knowledgeHierarchyFragment.jumpToTheTop();
                break;
            case Constants.TYPE_WX_ARTICLE:
                wxArticleFragment.jumpToTheTop();
                break;
            case Constants.TYPE_NAVIGATION:
                navigationFragment.jumpToTheTop();
                break;
            case Constants.TYPE_PROJECT:
                projectFragment.jumpToTheTop();
                break;
                default:
                    break;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.mMenu = menu;
        checkOptionMenu();
        return super.onPrepareOptionsMenu(menu);

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
                startActivity(new Intent(this, SearchActivity.class));
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);

                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_COLLECT_CODE){
            adjustFragment();
        }
    }

    private void adjustFragment() {
        switchFragment(Constants.TYPE_MAIN_PAGER);
        home_nav.setCheckedItem(R.id.nav_item_wan_android);
        home_bottom_nav.setSelectedItemId(R.id.tab_main_pager);
    }

    /**
     * 调整menu的显示隐藏
     */
    private void checkOptionMenu() {
        if (null != this.mMenu){
            boolean showTag = isNeedShowMenu == true ? true : false;
            for (int i = 0; i < mMenu.size(); i++) {
                mMenu.getItem(i).setVisible(showTag);
                mMenu.getItem(i).setEnabled(showTag);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonAlertDialog.newInstance().cancelDialog(true);
    }
}
