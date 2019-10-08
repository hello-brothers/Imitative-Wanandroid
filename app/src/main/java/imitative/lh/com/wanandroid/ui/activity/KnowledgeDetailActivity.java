package imitative.lh.com.wanandroid.ui.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.activity.BaseActivity;
import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;

public class KnowledgeDetailActivity extends BaseActivity {

    @BindView(R.id.common_toolbar)
    Toolbar toolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView title;
    private String know_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getBoundData();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected AbstractPresenter createPresneter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge_detail;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
        StatusBarUtils.immersive(this, toolbar, false);
    }

    @Override
    protected void initDataAndEvent() {
        title.setText(know_title);
    }

    private void getBoundData() {
        Bundle bundle = getIntent().getExtras();
        know_title = bundle.getString(Constants.KNOW_TITLE);
    }
}
