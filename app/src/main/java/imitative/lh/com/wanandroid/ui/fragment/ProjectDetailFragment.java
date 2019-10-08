package imitative.lh.com.wanandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.fragment.BaseFragment;
import imitative.lh.com.wanandroid.contract.mainpager.ProjectPagerDetailContract;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.presenter.ProjectDetailPresenter;
import imitative.lh.com.wanandroid.ui.activity.LoginActivity;
import imitative.lh.com.wanandroid.utils.CommonAlertDialog;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.ui.adapter.ProjectDetailAdapter;
import imitative.lh.com.wanandroid.utils.SkipUtils;

/**
 * @Date 2019/9/17
 * @created by lh
 * @Describe:
 */
public class ProjectDetailFragment extends BaseFragment<ProjectDetailPresenter> implements ProjectPagerDetailContract.View {

    @BindView(R.id.normal_view)
    SmartRefreshLayout project_refresh;
    @BindView(R.id.project_detail_recycler)
    RecyclerView project_detail_recycler;
    private ProjectDetailAdapter    projectDetailAdapter;
    private LinearLayoutManager     manager;
    private boolean isNoMoreDate = false;

    public static ProjectDetailFragment getInstance(int id){
        ProjectDetailFragment projectDetailFragment = new ProjectDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARG_PARAM1, id);
        projectDetailFragment.setArguments(bundle);
        return projectDetailFragment;
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
    }

    @Override
    protected void initDataAndView() {
        super.initDataAndView();
        Bundle bundle = getArguments();
        int projectId = bundle.getInt(Constants.ARG_PARAM1, 0);
        if (projectId == 0){
            return;
        }
        initRefresh();
        if (presenter != null){
            presenter.getProjectDetailData(projectId);
        }
        if (CommonUtils.isNetworkConnected()){
            showLoadingView();
        }
    }

    @Override
    protected ProjectDetailPresenter createPresenter() {
        return new ProjectDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_detail_article;
    }


    private void initRefresh() {
        project_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.refresh();
                refreshLayout.finishRefresh(3000);
            }
        });

        project_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    presenter.loadMore();
                    refreshLayout.finishLoadMore(3000);
            }
        });
    }

    private void initRecyclerView() {
        ArrayList<EssayData> data = new ArrayList<>();
        projectDetailAdapter = new ProjectDetailAdapter(R.layout.item_essay_with_img, data);
        projectDetailAdapter.setOnItemClickListener((adapter, view, position) -> startEssayDetailPager(view, position));
        projectDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> clickChildEvent(view, position));
        manager = new LinearLayoutManager(_mActivity);
        project_detail_recycler.addItemDecoration(new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL));
        project_detail_recycler.setLayoutManager(manager);
        project_detail_recycler.setAdapter(projectDetailAdapter);
    }

    @Override
    public void showProjectDetailData(EssayListData data, boolean isRefresh) {
        if (data.getDatas() == null){
            return;
        }
        if (data.getDatas().size() < Constants.PAGE_SIZE){
            project_refresh.finishLoadMoreWithNoMoreData();
        }
        if (isRefresh){
            projectDetailAdapter.replaceData(data.getDatas());
        }else {
            projectDetailAdapter.addData(data.getDatas());
        }
        showNormalView();
    }

    @Override
    public void showCancelColletEssay(int position, EssayData essayData) {
        projectDetailAdapter.setData(position, essayData);
    }

    @Override
    public void showAddColletEssay(int position, EssayData essayData) {
        projectDetailAdapter.setData(position, essayData);
    }

    @Override
    public void jumpToTheTop() {
        super.jumpToTheTop();
        project_detail_recycler.smoothScrollToPosition(0);
    }

    private void startEssayDetailPager(View view, int position) {
        EssayData wxArticalData = projectDetailAdapter.getData().get(position);
        SkipUtils.startEssayDetailActivity(_mActivity,
                wxArticalData.getTitle(),
                wxArticalData.getLink(),
                wxArticalData.getId(),
                wxArticalData.isCollect(), false);
    }

    private void clickChildEvent(View view, int position) {
        switch (view.getId()){
            case R.id.im_start:
                startEvent(position);
                break;
        }
    }

    private void startEvent(int position) {
        if (projectDetailAdapter.getData().size() < position || projectDetailAdapter.getData().size() < 0){
            return;
        }
        if (!presenter.getLoginState()){
            CommonAlertDialog.newInstance().showDialog(_mActivity,
                    getString(R.string.unlogin), getString(R.string.unlogin_text), getString(R.string.no), getString(R.string.ok),
                    v -> CommonAlertDialog.newInstance().cancelDialog(true),
                    v -> {
                        startActivity(new Intent(_mActivity, LoginActivity.class));
                        CommonAlertDialog.newInstance().cancelDialog(true);
                    }
            );
            return;
        }
        if (projectDetailAdapter.getData().get(position).isCollect()){
            presenter.cancelColletEssay(position, projectDetailAdapter.getData().get(position));
        }else {
            presenter.addColletEssay(position, projectDetailAdapter.getData().get(position));
        }

    }
}
