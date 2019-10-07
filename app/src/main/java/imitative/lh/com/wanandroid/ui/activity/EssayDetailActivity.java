package imitative.lh.com.wanandroid.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;

import java.lang.reflect.Method;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.activity.BaseActivity;
import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.contract.mainpager.EssayDetailContract;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.presenter.EssayDetailPresenter;
import imitative.lh.com.wanandroid.utils.CommonAlertDialog;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;

public class EssayDetailActivity extends BaseActivity<EssayDetailPresenter> implements EssayDetailContract.View {

    @BindView(R.id.essay_toolbar)
    Toolbar toolbar;
    @BindView(R.id.essay_title)
    TextView title;
    @BindView(R.id.content_webview)
    FrameLayout         contenWebView;
    private String      essay_title;
    private boolean     isCollection;
    private AgentWeb    mAgentWeb;
    private String      essayLink;
    private int         essay_id;
    private MenuItem    essay_collection;
    private boolean     is_pagecollect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getBoundData();
        super.onCreate(savedInstanceState);

    }

    @Override
    protected EssayDetailPresenter createPresneter() {
        return new EssayDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_essay_detail;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> {
            if (!mAgentWeb.back()){
                onBackPressedSupport();
            }
        });
        StatusBarUtils.immersive(this, toolbar, false);
    }

    @Override
    protected void initDataAndEvent() {
        showToolData();
        initWebView();
        setWebView();
    }

    /**
     * 设置menu图标文字一起显示
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null){
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")){
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.essay_menu, menu);
        essay_collection = menu.findItem(R.id.essay_collect);
        essay_collection.setIcon(isCollection ? R.drawable.ic_lover : R.drawable.ic_unlover);
        essay_collection.setTitle(isCollection ? getString(R.string.cancel_collectin) : getString(R.string.collection));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.essay_collect:
                collectEvent();
                break;
            case R.id.essay_browser:
                break;
            case R.id.essay_share:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();

    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            supportFinishAfterTransition();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getBoundData() {
        Bundle bundle   = getIntent().getExtras();
        essay_title     = bundle.getString(Constants.ESSEY_TITLE);
        isCollection    = bundle.getBoolean(Constants.IS_COLLECTION);
        essayLink       = bundle.getString(Constants.ESSAY_LINK);
        essay_id        = bundle.getInt(Constants.ESSAY_ID);
        is_pagecollect  = bundle.getBoolean(Constants.IS_PAGECOLLECT);
    }

    private void showToolData() {
        title.setText(essay_title);
    }

    private void initWebView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(contenWebView, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setMainFrameErrorView(R.layout.error_view, -1)
                .createAgentWeb()
                .ready()
                .go(essayLink);
    }

    private void setWebView() {
        WebView webView = mAgentWeb.getWebCreator().getWebView();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        if (CommonUtils.isNetworkConnected()){
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
    }


    /**
     * 收藏事件
     */
    private void collectEvent() {
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
        if (isCollection){
            if (is_pagecollect){
                presenter.cancelPageColletEssay(essay_id);
            }else {
                presenter.cancelColletEssay(essay_id);
            }
        }else {
            presenter.addColletEssay(essay_id);
        }

    }

    @Override
    public void showCancelColletEssay(EssayListData essayListData) {
        essay_collection.setTitle(getString(R.string.collection));
        essay_collection.setIcon(R.drawable.ic_unlover);
    }

    @Override
    public void showAddColletEssay(EssayListData essayListData) {
        essay_collection.setTitle(getString(R.string.cancel_collectin));
        essay_collection.setIcon(R.drawable.ic_lover);
    }
}
