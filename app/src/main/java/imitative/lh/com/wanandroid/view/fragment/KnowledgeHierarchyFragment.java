package imitative.lh.com.wanandroid.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.contract.mainpager.KnowledgePagerContract;
import imitative.lh.com.wanandroid.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.presenter.KnowledgePagerPresenter;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.view.adapter.NavigationAdapter;


public class KnowledgeHierarchyFragment extends BaseFragment<KnowledgePagerPresenter> implements KnowledgePagerContract.View {
    @BindView(R.id.knowledge_recycler)
    RecyclerView knowledge_recycler;
    private List<String> knowledgeData;
    private NavigationAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_hierarchy;
    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    @Override
    protected void initDataAndView() {
        super.initDataAndView();
        if(presenter != null){
            presenter.getKnowledgeData();
        }
        if (CommonUtils.isNetworkConnected()){
            showLoadingView();
        }
    }

    public static KnowledgeHierarchyFragment getInstance(){
        KnowledgeHierarchyFragment fragment = new KnowledgeHierarchyFragment();
        return fragment;
    }

    @Override
    protected KnowledgePagerPresenter createPresenter() {
        return new KnowledgePagerPresenter();
    }

    private void initRecyclerView() {
        knowledgeData = new ArrayList<>();
        knowledge_recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new NavigationAdapter(R.layout.item_knowledge, knowledgeData);
        knowledge_recycler.setAdapter(adapter);
    }


    @Override
    public void showKnowledgeList(List data) {
        if (adapter == null){
            return;
        }
        knowledgeData = data;
        adapter.replaceData(data);
        showNormalView();
    }

    @Override
    public void jumpToTheTop() {
        super.jumpToTheTop();
        if (knowledge_recycler != null){
            knowledge_recycler.smoothScrollToPosition(0);
        }
    }
}
