package imitative.lh.com.wanandroid.ui.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.fragment.BaseFragment;
import imitative.lh.com.wanandroid.contract.mainpager.KnowledgePagerContract;
import imitative.lh.com.wanandroid.network.bean.KnowledgeHierarchyData;
import imitative.lh.com.wanandroid.presenter.KnowledgePagerPresenter;
import imitative.lh.com.wanandroid.ui.activity.KnowledgeDetailActivity;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.ui.adapter.KnowledgeAdapter;


public class KnowledgeHierarchyFragment extends BaseFragment<KnowledgePagerPresenter> implements KnowledgePagerContract.View {
    @BindView(R.id.knowledge_recycler)
    RecyclerView                                knowledge_recycler;
    private List<KnowledgeHierarchyData>        knowledgeData;
    private KnowledgeAdapter                    knowledgeAdapter;

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
        knowledgeAdapter.setListener(data -> startToKnowledgeNext(data));
        knowledgeAdapter.openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", 0.5f, 1.0f),
                        ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1.0f)};
            }
        });
        knowledge_recycler.setAdapter(knowledgeAdapter);
    }

    @Override
    public void showKnowledgeList(List<KnowledgeHierarchyData> data) {
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

    private void startToKnowledgeNext(KnowledgeHierarchyData data) {
        if (data == null){
            return;
        }
        Intent intent = new Intent(_mActivity, KnowledgeDetailActivity.class);
        String know_title = data.getName();
        intent.putExtra(Constants.KNOW_TITLE, know_title);
        startActivity(intent);
    }

    @Override
    public void reload() {
        super.reload();
        if (presenter != null && knowledge_recycler.getVisibility() == View.INVISIBLE){
            presenter.refresh();
        }
    }
}
