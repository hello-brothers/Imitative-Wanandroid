package imitative.lh.com.wanandroid.ui.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.activity.BaseActivity;
import imitative.lh.com.wanandroid.contract.mainpager.SearchContract;
import imitative.lh.com.wanandroid.presenter.SearchPresenter;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;
import imitative.lh.com.wanandroid.widget.custom.SearchView;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View, SearchView.OnStatusChangListener {

    @BindView(R.id.toolbar_search)
    Toolbar toolbar_search;

    @BindView(R.id.search_container)
    FrameLayout search_container;
    @BindView(R.id.search_view)
    SearchView searchView;
    private ConstraintLayout inEffective_layout;
    private FrameLayout effective_layout;
    private int CURRENT_INDEX = -1;
    private FrameLayout defaule_layout;

    @Override
    protected SearchPresenter createPresneter() {
        return new SearchPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(toolbar_search);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(true);

        StatusBarUtils.immersive(this, toolbar_search, false);
    }

    @Override
    protected void initDataAndEvent() {
        initSearchView();
        initLayout();
        showSearchView();
        if (checkPresenter()){

        }
    }

    @Override
    public void onBackPressedSupport() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toolbar的事件---返回
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void initSearchView() {
        searchView.setOnStatusChangListener(this);
    }

    private void initLayout() {
        View.inflate(this, R.layout.search_default, search_container);
        View.inflate(this, R.layout.empty_data, search_container);
        View.inflate(this, R.layout.search_effectivedata, search_container);


        defaule_layout = search_container.findViewById(R.id.default_search);
        inEffective_layout = search_container.findViewById(R.id.search_empty);
        effective_layout = search_container.findViewById(R.id.search_effectiveLayout);

        defaule_layout.setVisibility(View.GONE);
        inEffective_layout.setVisibility(View.GONE);
        effective_layout.setVisibility(View.GONE);
    }

    /**
     * 展示初始的搜索界面
     */
    @Override
    public void showSearchView() {
        adjustContentView(Constants.TYPE_DEFAULE);
    }

    /**
     * 展示搜索后数据界面
     */
    @Override
    public void showSearchData() {
        adjustContentView(Constants.TYPE_EFFECTIVE);
    }

    /**
     * 展示无数据界面
     */
    @Override
    public void showNoData() {
        adjustContentView(Constants.TYPE_INEFFECTIVE);
    }

    /**
     * 点击EditText事件回调
     */
    @Override
    public void touchEditText() {
        adjustContentView(Constants.TYPE_DEFAULE);
    }

    /**
     * 点击软键盘的搜索
     * 开始进行搜索回调
     * @param content 需要搜索的字段
     */
    @Override
    public void searchContent(String content) {
        if (checkPresenter() && !TextUtils.isEmpty(content)){
            presenter.getSearchData(content);
        }
    }

    private boolean checkPresenter(){
        return presenter == null ? false : true;
    }

    private void adjustContentView(int showIndex){
        if (showIndex == CURRENT_INDEX){
            return;
        }
        activeContentView(CURRENT_INDEX, true);
        activeContentView(showIndex, false);
        CURRENT_INDEX = showIndex;


    }

    private void activeContentView(int index, boolean isHide){
        switch (index){
            case Constants.TYPE_DEFAULE:
                defaule_layout.setVisibility(isHide == true ? View.GONE : View.VISIBLE);
                break;
            case Constants.TYPE_EFFECTIVE:
                effective_layout.setVisibility(isHide == true ? View.GONE : View.VISIBLE);
                break;
            case Constants.TYPE_INEFFECTIVE:
                inEffective_layout.setVisibility(isHide == true ? View.GONE : View.VISIBLE);
                break;
            case Constants.TYPE_ERROR:
                break;
        }
    }
}
