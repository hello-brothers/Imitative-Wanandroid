package imitative.lh.com.wanandroid.view.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.view.adapter.WxDetailArticleAdapter;

/**
 * @Date 2019/9/11
 * @created by lh
 * @Describe:
 */
public class WxDetailArticleFragment extends BaseFragment {

    @BindView(R.id.wx_detail_recycler)
    RecyclerView recyclerView;
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
    protected AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wx_detail_article;
    }

    private void initRecyclerView() {
        List data = createData();
        wxDetailArticleAdapter = new WxDetailArticleAdapter(R.layout.item_essay, data);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(wxDetailArticleAdapter);
    }

    private List createData() {
        List data = new ArrayList();
        for (int i = 0; i < 10; i++) {
            data.add(i);
        }
        return data;
    }
}
