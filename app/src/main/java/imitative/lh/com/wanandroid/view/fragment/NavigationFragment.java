package imitative.lh.com.wanandroid.view.fragment;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.contract.mainpager.NavigationContract;
import imitative.lh.com.wanandroid.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.presenter.NavigationPresenter;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.view.adapter.NavigationAdapter;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class NavigationFragment extends BaseFragment<NavigationPresenter> implements NavigationContract.View {
    @BindView(R.id.navigation_tab_layout)
    VerticalTabLayout verticalTabLayout;
    @BindView(R.id.navigation_RecyclerView)
    RecyclerView recyclerView;
    private NavigationAdapter navigationAdapter;
    private LinearLayoutManager layoutManager;
    private int lastfirstCompletelyVisibleItemPosition = -1;
    private boolean needScorll;
    private boolean needTabClick;

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
    public void showNavigationListData(List data) {
        initVerticalTabLayout(data);
        navigationAdapter.replaceData(data);
        leftRightLinkage();
    }

    private void initReyclerView() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            data.add(i + "");
        }
        navigationAdapter = new NavigationAdapter(R.layout.item_knowledge, data);
        layoutManager = new LinearLayoutManager(_mActivity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(navigationAdapter);
    }


    private void initVerticalTabLayout(List data) {
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
                ITabView.TabTitle title = new ITabView.TabTitle.Builder().setContent(data.get(position) + "").setTextColor(
                        ContextCompat.getColor(_mActivity, R.color.blue4),
                        ContextCompat.getColor(_mActivity, R.color.colorblack))
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
             */
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    needTabClick = true;
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
//                if (needTabClick){
//                    return;
//                }
                int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstCompletelyVisibleItemPosition == lastfirstCompletelyVisibleItemPosition){
                    return;
                }
                needTabClick = false; //是否需要tab的回调跟着相应
                verticalTabLayout.setTabSelected(firstCompletelyVisibleItemPosition);
                lastfirstCompletelyVisibleItemPosition = firstCompletelyVisibleItemPosition;
            }
        });

        verticalTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                if (needTabClick) {
                    setTag(position);
                    Log.i("TAG", "onTabSelected: " + position);
                }
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
    }

    private void setTag(int position) {
        smoothScrollToPosition(position);
    }

    private void smoothScrollToPosition(int currentPosition) {
        Log.i("TAG", "smoothScrollToPosition: " + currentPosition);
        int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
        int firstPosition = layoutManager.findFirstVisibleItemPosition();

        if (currentPosition <= lastCompletelyVisibleItemPosition && currentPosition >= firstCompletelyVisibleItemPosition){
//            int firstTop = recyclerView.getChildAt(firstPosition).getTop();
//            int curTop = recyclerView.getChildAt(currentPosition).getTop();
//            int top = curTop-firstTop;
            needScorll = false;
            layoutManager.scrollToPositionWithOffset(currentPosition, 0);
//            recyclerView.smoothScrollBy(0, top);
        }else {
            recyclerView.smoothScrollToPosition(currentPosition);
        }
    }

}
