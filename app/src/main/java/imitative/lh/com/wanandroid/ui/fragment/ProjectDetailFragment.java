package imitative.lh.com.wanandroid.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.base.fragment.BaseFragment;
import imitative.lh.com.wanandroid.contract.mainpager.ProjectPagerDetailContract;
import imitative.lh.com.wanandroid.presenter.ProjectDetailPresenter;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.ui.adapter.ProjectDetailAdapter;

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

    public static ProjectDetailFragment getInstance(){
        return new ProjectDetailFragment();
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
    }

    @Override
    protected void initDataAndView() {
        super.initDataAndView();
        initRefresh();
        if (presenter != null){
            presenter.getProjectDetailData();
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
        ArrayList<String> data = new ArrayList<>();
        projectDetailAdapter = new ProjectDetailAdapter(R.layout.item_essay_with_img, data);
        manager = new LinearLayoutManager(_mActivity);
        project_detail_recycler.addItemDecoration(new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL));
        project_detail_recycler.setLayoutManager(manager);
        project_detail_recycler.setAdapter(projectDetailAdapter);
    }

    @Override
    public void showProjectDetailData(List data, boolean isRefresh) {
        if (isRefresh){
            projectDetailAdapter.replaceData(data);
        }else {
            projectDetailAdapter.addData(data);
        }
        showNormalView();
    }

    @Override
    public void jumpToTheTop() {
        super.jumpToTheTop();
        project_detail_recycler.smoothScrollToPosition(0);
    }
}
