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
import imitative.lh.com.wanandroid.view.adapter.NavigationAdapter;


public class KnowledgeHierarchyFragment extends BaseFragment<KnowledgePagerPresenter> implements KnowledgePagerContract.View {
    @BindView(R.id.knowledge_recycler)
    RecyclerView knowledge_recycler;
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
        List<String> date = createDate();
        knowledge_recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        NavigationAdapter adapter = new NavigationAdapter(R.layout.item_knowledge, date);
        knowledge_recycler.setAdapter(adapter);
    }

    private List<String> createDate() {
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datas.add("22");
        }
        return datas;
    }
}
