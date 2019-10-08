package imitative.lh.com.wanandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.fragment.BaseFragment;
import imitative.lh.com.wanandroid.contract.mainpager.KnowledgePagerDetailContract.View;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.presenter.KnowledgePagerDetailPresenter;
import imitative.lh.com.wanandroid.ui.activity.LoginActivity;
import imitative.lh.com.wanandroid.ui.adapter.WxDetailArticleAdapter;
import imitative.lh.com.wanandroid.utils.CommonAlertDialog;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.utils.SkipUtils;

public class KnowledgeHierarchyDetailFragment extends BaseFragment<KnowledgePagerDetailPresenter> implements View {

    @BindView(R.id.knowledge_detail_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout knowledge_refreshLayout;
    private int cid = -1;
    private WxDetailArticleAdapter adapter;

    @Override
    protected KnowledgePagerDetailPresenter createPresenter() {
        return new KnowledgePagerDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_hierarchy_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
    }

    @Override
    protected void initDataAndView() {
        super.initDataAndView();
        cid = getArguments().getInt(Constants.ARG_PARAM1);
        initRefresh();
        if (presenter != null && cid != -1){
            presenter.getKnowledgeDetailData(cid);
        }
        if (CommonUtils.isNetworkConnected()){
            showLoadingView();
        }
    }

    public static KnowledgeHierarchyDetailFragment getInstance(int cid){
        KnowledgeHierarchyDetailFragment knowledgeHierarchyDetailFragment = new KnowledgeHierarchyDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARG_PARAM1, cid);
        knowledgeHierarchyDetailFragment.setArguments(bundle);
        return knowledgeHierarchyDetailFragment;
    }


    @Override
    public void showKnowledgeDetailData(List<EssayData> data, boolean isRefresh) {
        if (data == null){
            return;
        }
        if (data.size() < Constants.PAGE_SIZE){
            knowledge_refreshLayout.finishLoadMoreWithNoMoreData();
        }
        if (isRefresh){
            adapter.replaceData(data);
        }else {
            adapter.addData(data);
        }
        showNormalView();
    }

    @Override
    public void showCancelColletEssay(int position, EssayData essayData) {
        adapter.setData(position, essayData);
    }

    @Override
    public void showAddColletEssay(int position, EssayData essayData) {
        adapter.setData(position, essayData);

    }

    private void initRecyclerView() {
        List data = new ArrayList();
        adapter = new WxDetailArticleAdapter(R.layout.item_essay, data);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(manager);
        adapter.setOnItemClickListener((adapter, view, position) -> startEssayDetailPager(view, position));
        adapter.setOnItemChildClickListener((adapter, view, position) -> clickChildEvent(view, position));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);
    }

    private void clickChildEvent(android.view.View view, int position) {
        switch (view.getId()){
            case R.id.im_start:
                startEvent(position);
                break;
        }

    }

    private void startEssayDetailPager(android.view.View view, int position) {
        EssayData wxArticalData = adapter.getData().get(position);
        SkipUtils.startEssayDetailActivity(_mActivity,
                wxArticalData.getTitle(),
                wxArticalData.getLink(),
                wxArticalData.getId(),
                wxArticalData.isCollect(), false);
    }

    private void startEvent(int position) {
        if (adapter.getData().size() < position || adapter.getData().size() < 0){
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
        if (adapter.getData().get(position).isCollect()){
            presenter.cancelColletEssay(position, adapter.getData().get(position));
        }else {
            presenter.addColletEssay(position, adapter.getData().get(position));
        }
    }


    private void initRefresh() {
        knowledge_refreshLayout.setOnRefreshListener(refreshLayout -> {
            presenter.refresh();
            refreshLayout.finishRefresh(Constants.REFRESH_TIME);
        });

        knowledge_refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            presenter.loadMore();
            refreshLayout.finishLoadMore(Constants.REFRESH_TIME);
        });
    }

    @Override
    public void reload() {
        super.reload();
        if (presenter != null
                && CommonUtils.isNetworkConnected()) {
            presenter.refresh();
        }
    }
}
