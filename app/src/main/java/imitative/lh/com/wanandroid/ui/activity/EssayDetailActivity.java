package imitative.lh.com.wanandroid.ui.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Method;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.base.activity.BaseActivity;
import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;

public class EssayDetailActivity extends BaseActivity {

    @BindView(R.id.essay_toolbar)
    Toolbar toolbar;
    @BindView(R.id.essay_title)
    TextView title;
    private String essay_title;
    private boolean isCollection;

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
        return R.layout.activity_essay_detail;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
        StatusBarUtils.immersive(this, toolbar, false);
    }

    @Override
    protected void initDataAndEvent() {

        showToolData();
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
        MenuItem essay_collection = menu.findItem(R.id.essay_collect);
        essay_collection.setIcon(isCollection ? R.drawable.ic_lover : R.drawable.ic_unlover);
        essay_collection.setTitle(isCollection ? getString(R.string.cancel_collectin) : getString(R.string.collection));
        return true;
    }

    private void getBoundData() {
        Bundle bundle = getIntent().getExtras();
        essay_title = bundle.getString(Constants.ESSEY_TITLE);
        isCollection = bundle.getBoolean(Constants.IS_COLLECTION);
    }

    private void showToolData() {
        title.setText(essay_title);
    }
}
