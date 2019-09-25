package imitative.lh.com.wanandroid.ui.fragment;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.network.bean.BannerData;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.ui.activity.LoginActivity;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.fragment.BaseFragment;
import imitative.lh.com.wanandroid.contract.mainpager.MainPagerContract;
import imitative.lh.com.wanandroid.presenter.MainPagerPresenter;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.ui.adapter.EssayListAdapter;

public class MainPagerFragment extends BaseFragment<MainPagerPresenter> implements MainPagerContract.View {


    @BindView(R.id.essay_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;

    private EssayListAdapter    recycleradapter;
    private List<EssayData>        mEssayDataList;
    private boolean             isReCreate;
    private Banner              banner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isReCreate = getArguments().getBoolean(Constants.ARG_PARAM1);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (banner != null){
            banner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (banner != null){
            banner.stopAutoPlay();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_pager;
    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    @Override
    protected void initDataAndView() {
        super.initDataAndView();
        setRefrash();
        if (isFirstbuild()){
            //第一次加载
            presenter.loadData();
        }else {
            presenter.refresh();
        }
        if (CommonUtils.isNetworkConnected()){
            showLoadingView();
        }
    }

    @Override
    public void jumpToTheTop() {
        if (recyclerView != null){
            recyclerView.smoothScrollToPosition(0);
        }
    }

    private boolean isFirstbuild() {
        return !isReCreate ;
    }

    public static MainPagerFragment getInstance(boolean param){
        MainPagerFragment fragment = new MainPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ARG_PARAM1, param);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initRecyclerView(){
        mEssayDataList  = new ArrayList<>();
        recycleradapter = new EssayListAdapter(mEssayDataList);
        recycleradapter.setOnItemClickListener((adapter, view, position) -> startEssayDetailPager(view, position));
        recycleradapter.setOnItemChildClickListener((adapter, view, position) -> clickChildEvent(view, position));
        recycleradapter.openLoadAnimation();
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL));
        //添加轮播
        LinearLayout mLinearLayout = (LinearLayout) LayoutInflater.from(_mActivity).inflate(R.layout.head_banner, null);
        banner = mLinearLayout.findViewById(R.id.head_banner);
        mLinearLayout.removeView(banner);
        recycleradapter.addHeaderView(banner);

        recyclerView.setAdapter(recycleradapter);
    }

    /**
     * 点击item里的内容事件
     * @param view 视图
     * @param position 位置
     */
    private void clickChildEvent(View view, int position) {
        switch (view.getId()){
            case R.id.im_start:
                startEvent(position);
                break;
        }
    }

    private void startEvent(int position) {
        if (recycleradapter.getData().size() < position || recycleradapter.getData().size() < 0){
            return;
        }
        if (!presenter.getLoginState()){
            startActivity(new Intent(_mActivity, LoginActivity.class));
            CommonUtils.showMessage(_mActivity, getString(R.string.login_first));
            return;
        }
        CommonUtils.showMessage(_mActivity, "i like " + position);
    }

    /**
     * 点击item进入文章详情
     * @param view item
     * @param position 位置
     */
    private void startEssayDetailPager(View view, int position) {
        if (recycleradapter.getData().size() < 0 || recycleradapter.getData().size() < position){
            return;
        }
        CommonUtils.showMessage(_mActivity, position + "");
    }

    @Override
    public void showEssayList(List<EssayData> data) {
        if (recycleradapter == null){
            return;
        }

        mEssayDataList = data;
        recycleradapter.replaceData(mEssayDataList);
        showNormalView();
    }

    @Override
    public void showBannerData(List<BannerData> bannerDataList) {
        ArrayList<String> bannerImgs = new ArrayList<>();
        for (BannerData bannerData : bannerDataList) {
            bannerImgs.add(bannerData.getImagePath());
        }


        //设置banner样式
//        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);

            }
        });
        //设置图片集合
        banner.setImages(bannerImgs);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(bannerDataList.size() * 400);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
    }

    @Override
    public void showLoadMoreView(List<EssayData> data) {
        if (recycleradapter == null){
            return;
        }
        recycleradapter.addData(data);
        showNormalView();
    }

    @Override
    protected MainPagerPresenter createPresenter() {
        return new MainPagerPresenter();
    }

    private void setRefrash() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            presenter.refresh();
            refreshLayout.finishRefresh(Constants.REFRESH_TIME);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            presenter.loadMore();
            refreshLayout.finishLoadMore(Constants.REFRESH_TIME);
        });
    }

    @Override
    public void reload() {
        super.reload();
        if (presenter != null && recyclerView.getVisibility() == View.INVISIBLE
                && CommonUtils.isNetworkConnected()) {
            presenter.refresh();
        }
    }
}
