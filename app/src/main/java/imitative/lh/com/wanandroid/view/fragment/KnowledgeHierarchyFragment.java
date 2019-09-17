package imitative.lh.com.wanandroid.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.contract.mainpager.KnowledgePagerContract;
import imitative.lh.com.wanandroid.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.presenter.KnowledgePagerPresenter;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.view.adapter.KnowledgeAdapter;
import imitative.lh.com.wanandroid.view.adapter.NavigationAdapter;
import imitative.lh.com.wanandroid.widget.custom.FlexTextView;


public class KnowledgeHierarchyFragment extends BaseFragment<KnowledgePagerPresenter> implements KnowledgePagerContract.View {
    @BindView(R.id.knowledge_recycler)
    RecyclerView knowledge_recycler;
    private List<String> knowledgeData;
    private KnowledgeAdapter knowledgeAdapter;

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
        knowledgeAdapter = new KnowledgeAdapter(R.layout.item_knowledge, knowledgeData);
        knowledgeAdapter.setListener(position -> startToKnowledgeNext(position));
        knowledge_recycler.setAdapter(knowledgeAdapter);
    }

    @Override
    public void showKnowledgeList(List data) {
        if (knowledgeAdapter == null){
            return;
        }
        knowledgeData = data;
        knowledgeAdapter.replaceData(data);
        showNormalView();
    }

    @Override
    public void jumpToTheTop() {
        super.jumpToTheTop();
        if (knowledge_recycler != null){
            knowledge_recycler.smoothScrollToPosition(0);
        }
    }


    private void startToKnowledgeNext(int position) {
        CommonUtils.showMessage(_mActivity, position+"");
    }



    @Override
    public void reload() {
        super.reload();
        if (presenter != null && knowledge_recycler.getVisibility() == View.INVISIBLE){
            presenter.refresh();
        }
    }
}
