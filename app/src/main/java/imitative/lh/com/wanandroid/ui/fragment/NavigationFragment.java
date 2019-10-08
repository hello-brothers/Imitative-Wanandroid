package imitative.lh.com.wanandroid.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.base.fragment.BaseFragment;
import imitative.lh.com.wanandroid.contract.mainpager.NavigationContract;
import imitative.lh.com.wanandroid.network.bean.NavigationListData;
import imitative.lh.com.wanandroid.presenter.NavigationPresenter;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.ui.adapter.NavigationAdapter;
import imitative.lh.com.wanandroid.utils.SkipUtils;
import imitative.lh.com.wanandroid.widget.custom.FlexTextView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class NavigationFragment extends BaseFragment<NavigationPresenter> implements NavigationContract.View, FlexTextView.OnFlexClickListener {
    @BindView(R.id.navigation_tab_layout)
    VerticalTabLayout verticalTabLayout;
    @BindView(R.id.navigation_RecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.normal_view)
    LinearLayout normal_view;
    private NavigationAdapter       navigationAdapter;
    private LinearLayoutManager     layoutManager;

    /** 判断recyclerview是主动滑动还是被动滑动
     * true -是 false -否由tablayout引起
     * **/
    private boolean                 isTouchRecycler;
    private boolean                 scrollByTouch;
    //记录上次顶部的位置，防止同一滑块滑动定位到同一tab
    private int                     lastPos;
    private boolean                 canScroll;
    private int                     scrollToPosition;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initView() {
        initReyclerView();
    }

    @Override
    protected void initDataAndView() {
        if (presenter != null){
            presenter.getNavigationListData();
        }
        if (CommonUtils.isNetworkConnected()){
            showLoadingView();
        }
    }

    public static NavigationFragment getInstance(){
        NavigationFragment fragment = new NavigationFragment();
        return fragment;
    }

    @Override
    protected NavigationPresenter createPresenter() {
        return new NavigationPresenter();
    }

    @Override
    public void showNavigationListData(List<NavigationListData> data) {
        if (data.size() == 0){
            return;
        }
        initVerticalTabLayout(data);
        navigationAdapter.replaceData(data);
        leftRightLinkage();
    }

    @Override
    public void showRefresh(List<NavigationListData> data) {
        if (navigationAdapter == null){
            return;
        }
        navigationAdapter.replaceData(data);
    }

    private void initReyclerView() {

        List<NavigationListData> data = new ArrayList<>();
        navigationAdapter = new NavigationAdapter(R.layout.item_knowledge, data, this);
        layoutManager = new LinearLayoutManager(_mActivity);
        recyclerView.setOnTouchListener((v, event) -> {
            isTouchRecycler = true;
            return false;
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(navigationAdapter);
    }


    private void initVerticalTabLayout(List<NavigationListData> data) {
        verticalTabLayout.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                ITabView.TabTitle title = new ITabView.TabTitle.Builder().setContent(data.get(position).getName()).setTextColor(
                        ContextCompat.getColor(_mActivity, R.color.blue4),
                        ContextCompat.getColor(_mActivity, R.color.colorblack))
                        .setTextSize(14)
                        .build();
                return title;
            }

            @Override
            public int getBackground(int position) {
                return ContextCompat.getColor(_mActivity, R.color.blue6);
            }
        });
    }

    private void leftRightLinkage() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 滚动变化时回调
             * @param recyclerView  当前滚动的recyclerview
             * @param newState  当前的滚动状态
             *                  开始滚动（SCROLL_STATE_FLING），
             *                  正在滚动(SCROLL_STATE_TOUCH_SCROLL),
             *                  已经停止（SCROLL_STATE_IDLE）
             */
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isTouchRecycler && (newState != RecyclerView.SCROLL_STATE_IDLE)){
                    scrollByTouch = true;
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    scrollByTouch = false;
                }
                if (canScroll){
                    canScroll = false;
                    smoothScrollToPosition(scrollToPosition);
                }
            }

            /**
             * 滚动时回调
             * @param recyclerView 当前滚动的view
             * @param dx    水平滚动的距离
             * @param dy    垂直滚动的距离
             */
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (scrollByTouch){
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if (lastPos != firstVisibleItemPosition){
                        verticalTabLayout.setTabSelected(firstVisibleItemPosition);
                    }
                    lastPos = firstVisibleItemPosition;
                }

            }
        });

        verticalTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                if (!scrollByTouch){
                    smoothScrollToPosition(position);
                }

            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
    }

    private void smoothScrollToPosition(int currentPosition) {
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        int lastPosition = layoutManager.findLastVisibleItemPosition();

        if (currentPosition <= firstPosition){
            recyclerView.smoothScrollToPosition(currentPosition);
        }else if (currentPosition <= lastPosition){
            int top = recyclerView.getChildAt(currentPosition - firstPosition).getTop();
            recyclerView.smoothScrollBy(0, top);
        }else {
            recyclerView.smoothScrollToPosition(currentPosition);
            scrollToPosition = currentPosition;
            canScroll = true;
        }
    }

    @Override
    public void jumpToTheTop() {
        super.jumpToTheTop();
        if (verticalTabLayout != null){
            verticalTabLayout.setTabSelected(0);
        }
    }

    @Override
    public void reload() {
        super.reload();
        if (presenter != null ){
            presenter.refresh();
        }
    }

    /**
     * 点击flextextview 进入网页
     * @param title
     * @param link
     * @param id
     * @param isCollect
     */
    @Override
    public void onClick(String title, String link, int id, boolean isCollect) {
        SkipUtils.startEssayDetailActivity(_mActivity, title, link, id, isCollect, false);
    }


}