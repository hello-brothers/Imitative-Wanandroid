package imitative.lh.com.wanandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.fragment.BaseFragment;
import imitative.lh.com.wanandroid.contract.mainpager.WxArticlePagerDetailContract;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.presenter.WxArticleDetailPresenter;
import imitative.lh.com.wanandroid.ui.activity.LoginActivity;
import imitative.lh.com.wanandroid.utils.CommonAlertDialog;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.ui.adapter.WxDetailArticleAdapter;
import imitative.lh.com.wanandroid.utils.SkipUtils;

/**
 * @Date 2019/9/11
 * @created by lh
 * @Describe:
 */
public class WxDetailArticleFragment extends BaseFragment<WxArticleDetailPresenter> implements WxArticlePagerDetailContract.View {

    @BindView(R.id.wx_detail_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout                  smartRefreshLayout;
    private WxDetailArticleAdapter      wxDetailArticleAdapter;

    public static WxDetailArticleFragment getInstance(int authorId){
        WxDetailArticleFragment wxDetailArticleFragment = new WxDetailArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARG_PARAM1, authorId);
        wxDetailArticleFragment.setArguments(bundle);
        return wxDetailArticleFragment;
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
        Bundle arguments = getArguments();
        int authorid = arguments.getInt(Constants.ARG_PARAM1, 0);
        if (authorid == 0){
            return;
        }
        if (presenter != null){
            presenter.getWxDetailData(authorid);
        }
        if (CommonUtils.isNetworkConnected()){
            showLoadingView();
        }
    }

    @Override
    protected WxArticleDetailPresenter createPresenter() {
        return new WxArticleDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wx_detail_article;
    }

    private void initRecyclerView() {
        List data = new ArrayList();
        wxDetailArticleAdapter = new WxDetailArticleAdapter(R.layout.item_essay, data);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        recyclerView.setLayoutManager(manager);
        wxDetailArticleAdapter.setOnItemClickListener((adapter, view, position) -> startEssayDetailPager(view, position));
        wxDetailArticleAdapter.setOnItemChildClickListener((adapter, view, position) -> clickChildEvent(view, position));
        recyclerView.addItemDecoration(new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(wxDetailArticleAdapter);
    }

    @Override
    public void showWxDetailData(List<EssayData> data, boolean isRefresh) {
        if (isRefresh){
            wxDetailArticleAdapter.replaceData(data);
        }else {
            wxDetailArticleAdapter.addData(data);
        }
        showNormalView();
    }

    @Override
    public void showCancelColletEssay(int position, EssayData essayData) {
        wxDetailArticleAdapter.setData(position, essayData);
    }

    @Override
    public void showAddColletEssay(int position, EssayData essayData) {
        wxDetailArticleAdapter.setData(position, essayData);

    }

    private void initRefresh() {
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            presenter.loadMore();
            refreshLayout.finishLoadMore(Constants.REFRESH_TIME);

        });

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            presenter.refresh();
            refreshLayout.finishRefresh(Constants.REFRESH_TIME);

        });
    }

    @Override
    public void jumpToTheTop() {
        super.jumpToTheTop();
        if (recyclerView != null){
            recyclerView.smoothScrollToPosition(0);
        }
    }


    private void startEssayDetailPager(View view, int position) {
        EssayData wxArticalData = wxDetailArticleAdapter.getData().get(position);
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
        if (wxDetailArticleAdapter.getData().size() < position || wxDetailArticleAdapter.getData().size() < 0){
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
        if (wxDetailArticleAdapter.getData().get(position).isCollect()){
            presenter.cancelColletEssay(position, wxDetailArticleAdapter.getData().get(position));
        }else {
            presenter.addColletEssay(position, wxDetailArticleAdapter.getData().get(position));
        }
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
