package imitative.lh.com.wanandroid.view.fragment;

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
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.contract.mainpager.CollectionPagerContract;
import imitative.lh.com.wanandroid.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.presenter.CollectionPresenter;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.view.adapter.CollectionAdapter;

/**
 * @Date 2019/8/28
 * @created by lh
 * @Describe:
 */
public class CollectionFragment extends BaseFragment<CollectionPresenter> implements CollectionPagerContract.View {

    @BindView(R.id.collection_recycler)
    RecyclerView collection_recycler;
    @BindView(R.id.normal_view)
    SmartRefreshLayout collection_refresh;
    private CollectionAdapter collectionAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List showData = new ArrayList();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection_pager;
    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    @Override
    protected void initDataAndView() {
        super.initDataAndView();
        initRefresh();
        if (presenter != null){
            presenter.getCollectionListData();
        }
        if (CommonUtils.isNetworkConnected()){
            showLoadingView();
        }
    }

    public static CollectionFragment newInstance(){
        return new CollectionFragment();
    }

    @Override
    protected CollectionPresenter createPresenter() {
        return new CollectionPresenter();
    }

    @Override
    public void showCollectionListData(List data) {
        this.showData = data;
        collectionAdapter.replaceData(showData);
        showNormalView();
    }

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(_mActivity);
        collection_recycler.setLayoutManager(linearLayoutManager);
        collection_recycler.addItemDecoration(new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL));
        collectionAdapter = new CollectionAdapter(R.layout.item_collection, showData);
        collectionAdapter.setOnItemClickListener((adapter, view, position) -> {});
        collectionAdapter.setOnItemChildClickListener((adapter, view, position) -> clickChildEvent(view, position));
        collection_recycler.setAdapter(collectionAdapter);
    }



    private void initRefresh(){
        collection_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.refresh();
                refreshLayout.finishRefresh(2000);
            }
        });

        collection_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(true);
            }
        });
    }

    private void clickChildEvent(View view, int position) {
        switch (view.getId()){
            case R.id.tv_delet:
                showData.remove(position);
                collectionAdapter.replaceData(showData);
                CommonUtils.showMessage(_mActivity, position + "");

                break;
        }
    }
}
