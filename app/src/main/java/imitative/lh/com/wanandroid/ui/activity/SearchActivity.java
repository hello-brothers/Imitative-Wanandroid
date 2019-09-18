package imitative.lh.com.wanandroid.ui.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.base.activity.BaseActivity;
import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.contract.mainpager.SearchContract;
import imitative.lh.com.wanandroid.presenter.SearchPresenter;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.toolbar_search)
    Toolbar toolbar_search;

    @BindView(R.id.search_container)
    FrameLayout search_container;
    private ConstraintLayout empty_layout;

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
        initLayout();
        showNoData();
        if (presenter != null){

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

    private void initLayout() {
        View.inflate(this, R.layout.empty_data, search_container);
        empty_layout = search_container.findViewById(R.id.search_empty);
        empty_layout.setVisibility(View.GONE);
    }

    /**
     * 展示初始的搜索界面
     */
    @Override
    public void showSearchView() {

    }

    /**
     * 展示搜索后数据界面
     */
    @Override
    public void showSearchData() {

    }

    /**
     * 展示无数据界面
     */
    @Override
    public void showNoData() {
        empty_layout.setVisibility(View.VISIBLE);
    }
}
