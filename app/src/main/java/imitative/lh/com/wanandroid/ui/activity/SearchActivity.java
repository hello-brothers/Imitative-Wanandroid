package imitative.lh.com.wanandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.flexbox.FlexboxLayout;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.activity.BaseActivity;
import imitative.lh.com.wanandroid.contract.mainpager.SearchContract;
import imitative.lh.com.wanandroid.core.dao.HistoryData;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.HotKey;
import imitative.lh.com.wanandroid.presenter.SearchPresenter;
import imitative.lh.com.wanandroid.ui.adapter.WxDetailArticleAdapter;
import imitative.lh.com.wanandroid.utils.CommonAlertDialog;
import imitative.lh.com.wanandroid.utils.SkipUtils;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;
import imitative.lh.com.wanandroid.widget.custom.FlexTextView;
import imitative.lh.com.wanandroid.widget.custom.SearchView;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View, SearchView.OnStatusChangListener {

    @BindView(R.id.toolbar_search)
    Toolbar toolbar_search;

    @BindView(R.id.search_container)
    FrameLayout search_container;
    @BindView(R.id.search_view)
    SearchView searchView;
    RecyclerView mRecyclerView;

    private ConstraintLayout            inEffective_layout;
    private FrameLayout                 effective_layout;
    private int                         CURRENT_INDEX = -1;
    private FrameLayout                 defaule_layout;
    private WxDetailArticleAdapter      adapter;
    private FlexboxLayout               flexboxLayout;
    private FlexboxLayout               history_search_flexBox;
    private LinearLayout                history_clearall;

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
    protected void onViewCreated() {
        super.onViewCreated();
    }

    @Override
    protected void initDataAndEvent() {
        /**监听自定义view searchView**/
        initSearchView();
        /**初始化三种界面的布局**/
        initLayout();
        subscribeClickEvent();
        /**初始化recyclerview**/
        initRecyclerView();

        showSearchView();
        if (checkPresenter()){
            //请求热门搜索数据
            presenter.getSearchHotData();
            presenter.refreshHistoryData();

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

        mRecyclerView = search_container.findViewById(R.id.search_datarecycler);
        flexboxLayout = search_container.findViewById(R.id.hot_search_flexBox);
        history_search_flexBox = search_container.findViewById(R.id.history_search_flexBox);
        history_clearall = search_container.findViewById(R.id.history_clearall);

        defaule_layout.setVisibility(View.GONE);
        inEffective_layout.setVisibility(View.GONE);
        effective_layout.setVisibility(View.GONE);
    }

    @Override
    public void showHotKey(List<HotKey> hotKeyList) {
        flexboxLayout.removeAllViews();
        for (HotKey hotKey : hotKeyList) {
            FlexTextView flexTextView = new FlexTextView(this);
            flexTextView.setText(hotKey.getName());

            addFlexClickEvent(flexTextView, hotKey.getName());
            flexboxLayout.addView(flexTextView);

        }
    }

    private void addFlexClickEvent(FlexTextView flexText, String name) {
        presenter.addDisposible(RxView.clicks(flexText)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    searchView.setText(name);
                    presenter.getSearchData(searchView.getText());
                }));
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
    public void showSearchData(List<EssayData> dataList, boolean isRefresh) {
        if (isRefresh){
            adapter.replaceData(dataList);
        }else {
            adapter.addData(dataList);
        }
        adjustContentView(Constants.TYPE_EFFECTIVE);
    }

    /**
     * 展示无数据界面
     */
    @Override
    public void showNoData() {
        adjustContentView(Constants.TYPE_INEFFECTIVE);
    }

    @Override
    public void showCancelColletEssay(int position, EssayData essayData) {
        adapter.setData(position, essayData);
    }

    @Override
    public void showAddColletEssay(int position, EssayData essayData) {
        adapter.setData(position, essayData);

    }

    @Override
    public void showRefreshHistoryData(List<HistoryData> historyDataList) {
        history_search_flexBox.removeAllViews();
        for (HistoryData historyData : historyDataList) {
            FlexTextView flexTextView = new FlexTextView(this);
            flexTextView.setText(historyData.getValue());
            addFlexClickEvent(flexTextView, historyData.getValue());
            history_search_flexBox.addView(flexTextView);
        }
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

    @Override
    public void clearContent() {
        adjustContentView(Constants.TYPE_DEFAULE);
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

    private void initRecyclerView() {
        List data = new ArrayList();
        adapter = new WxDetailArticleAdapter(R.layout.item_essay, data);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        adapter.setOnItemClickListener((adapter, view, position) -> startEssayDetailPager(view, position));
        adapter.setOnItemChildClickListener((adapter, view, position) -> clickChildEvent(view, position));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);
    }

    private void startEssayDetailPager(android.view.View view, int position) {
        EssayData wxArticalData = adapter.getData().get(position);
        SkipUtils.startEssayDetailActivity(this,
                wxArticalData.getTitle(),
                wxArticalData.getLink(),
                wxArticalData.getId(),
                wxArticalData.isCollect(), false);
    }

    private void clickChildEvent(android.view.View view, int position) {
        switch (view.getId()){
            case R.id.im_start:
                startEvent(position);
                break;
        }

    }

    private void startEvent(int position) {
        if (adapter.getData().size() < position || adapter.getData().size() < 0){
            return;
        }
        if (!presenter.getLoginState()){
            CommonAlertDialog.newInstance().showDialog(this,
                    getString(R.string.unlogin), getString(R.string.unlogin_text), getString(R.string.no), getString(R.string.ok),
                    v -> CommonAlertDialog.newInstance().cancelDialog(true),
                    v -> {
                        startActivity(new Intent(this, LoginActivity.class));
                        CommonAlertDialog.newInstance().cancelDialog(true);
                    }
            );
            return;
        }
        if (adapter.getData().get(position).isCollect()){
            presenter.cancelColletEssay(position, adapter.getData().get(position));
        }else {
            presenter.addColletEssay(position, adapter.getData().get(position));
        }
    }

    private void subscribeClickEvent() {
        presenter.addDisposible(RxView.clicks(history_clearall)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .filter(o -> presenter != null)
                .subscribe(o -> presenter.clearAllHistoryData()));
    }
}
