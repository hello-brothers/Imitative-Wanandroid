package imitative.lh.com.wanandroid.view.fragment;

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
import imitative.lh.com.wanandroid.contract.mainpager.WxArticlePagerDetailContract;
import imitative.lh.com.wanandroid.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.presenter.WxArticleDetailPresenter;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.view.adapter.WxDetailArticleAdapter;

/**
 * @Date 2019/9/11
 * @created by lh
 * @Describe:
 */
public class WxDetailArticleFragment extends BaseFragment<WxArticleDetailPresenter> implements WxArticlePagerDetailContract.View {

    @BindView(R.id.wx_detail_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    private WxDetailArticleAdapter wxDetailArticleAdapter;

    public static WxDetailArticleFragment getInstance(){
        return new WxDetailArticleFragment();
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
            presenter.getWxDetailData();
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
        recyclerView.addItemDecoration(new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(wxDetailArticleAdapter);
    }



    @Override
    public void showWxDetailData(List data, boolean isRefresh) {
        if (isRefresh){
            wxDetailArticleAdapter.replaceData(data);
        }else {
            wxDetailArticleAdapter.addData(data);
        }
        showNormalView();
    }

    private void initRefresh() {
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            presenter.loadMore();
            refreshLayout.finishLoadMore(3000);

        });

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            presenter.refresh();
            refreshLayout.finishRefresh(3000);

        });
    }

    @Override
    public void jumpToTheTop() {
        super.jumpToTheTop();
        if (recyclerView != null){
            recyclerView.smoothScrollToPosition(0);
        }
    }
}
