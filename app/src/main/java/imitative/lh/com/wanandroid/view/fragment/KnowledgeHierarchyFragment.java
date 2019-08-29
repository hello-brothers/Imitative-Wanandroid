package imitative.lh.com.wanandroid.view.fragment;

import imitative.lh.com.wanandroid.R;

public class KnowledgeHierarchyFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_hierarchy;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDataAndView() {

    }

    public static KnowledgeHierarchyFragment getInstance(){
        KnowledgeHierarchyFragment fragment = new KnowledgeHierarchyFragment();
        return fragment;
    }
}
