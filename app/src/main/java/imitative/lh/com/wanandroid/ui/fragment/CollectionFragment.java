package imitative.lh.com.wanandroid.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.base.fragment.BaseFragment;
import imitative.lh.com.wanandroid.contract.mainpager.CollectionPagerContract;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.presenter.CollectionPresenter;
import imitative.lh.com.wanandroid.ui.activity.LoginActivity;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.ui.adapter.CollectionAdapter;
import imitative.lh.com.wanandroid.utils.SkipUtils;

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
    private CollectionAdapter           collectionAdapter;
    private LinearLayoutManager         linearLayoutManager;
    private List                        showData = new ArrayList();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection_pager;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter ==null){
            return;
        }
        if (!presenter.getLoginState()){
            showUnloginView();
        }else {
            presenter.refresh();
        }
    }

    @Override
    protected void initDataAndView() {
        super.initDataAndView();
        initRefresh();
        if (!presenter.getLoginState()){
            showUnloginView();
            return;
        }

        if ( CommonUtils.isNetworkConnected()){
            showLoadingView();
        }

        if (presenter != null){
            presenter.getCollectionListData();
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
    public void showCollectionListData(List<EssayData> data) {
        this.showData = data;
        collectionAdapter.replaceData(showData);
        if (!presenter.getLoginState()){
            showUnloginView();
        }else {
            showNormalView();
        }
    }

    @Override
    public void showCancelPageCollectEssay(int position) {
        showData.remove(position);
        collectionAdapter.replaceData(showData);
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

        collection_refresh.setOnRefreshListener(refreshLayout -> {
            presenter.refresh();
            refreshLayout.finishRefresh(2000);
        });

        collection_refresh.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.setNoMoreData(true);
            refreshLayout.finishLoadMore(2000);
        });
    }

    private void clickChildEvent(View view, int position) {
        switch (view.getId()){
            case R.id.tv_delet:
//                showData.remove(position);
//                collectionAdapter.replaceData(showData);
                presenter.cancelPageCollectEssay(position, collectionAdapter.getData().get(position));
                break;
            case R.id.liker_content:
                EssayData essayData = collectionAdapter.getData().get(position);
                SkipUtils.startEssayDetailActivity(_mActivity,
                        essayData.getTitle(),
                        essayData.getLink(),
                        essayData.getId(),
                        true, true);
                break;
        }
    }

    @Override
    public void reload() {
        super.reload();
        if (presenter != null && collection_recycler.getVisibility() == View.INVISIBLE){
            presenter.refresh();
        }
    }
}
