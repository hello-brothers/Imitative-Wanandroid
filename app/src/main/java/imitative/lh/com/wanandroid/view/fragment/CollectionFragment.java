package imitative.lh.com.wanandroid.view.fragment;

import imitative.lh.com.wanandroid.R;

/**
 * @Date 2019/8/28
 * @created by lh
 * @Describe:
 */
public class CollectionFragment extends BaseFragment{

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection_pager;
    }

    public static CollectionFragment newInstance(){
        return new CollectionFragment();
    }
}
