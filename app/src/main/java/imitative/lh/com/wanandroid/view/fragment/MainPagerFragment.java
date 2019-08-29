package imitative.lh.com.wanandroid.view.fragment;



import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.contract.mainpager.MainPagerContract;
import imitative.lh.com.wanandroid.view.adapter.EssayListAdapter;

public class MainPagerFragment extends BaseFragment implements MainPagerContract.View {


    @BindView(R.id.essay_recycler)
    RecyclerView recyclerView;
    private EssayListAdapter adapter;
    private ArrayList<String> mEssayDataList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_pager;
    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    @Override
    protected void initDataAndView() {
        addData();
        if (adapter != null){
            adapter.replaceData(mEssayDataList);
        }
    }

    public static MainPagerFragment getInstance(){
        MainPagerFragment fragment = new MainPagerFragment();
        return fragment;
    }

    public void initRecyclerView(){
        mEssayDataList = new ArrayList<>();
        adapter = new EssayListAdapter(R.layout.item_essay, mEssayDataList);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void addData() {
        for (int i = 0; i < 100; i++) {
            mEssayDataList.add("1");
        }
    }

    @Override
    public void showEssayListView(List data) {

    }
}
