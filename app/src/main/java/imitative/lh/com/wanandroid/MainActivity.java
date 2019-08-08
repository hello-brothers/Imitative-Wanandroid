package imitative.lh.com.wanandroid;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import imitative.lh.com.wanandroid.presenter.BasePresenter;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;
import imitative.lh.com.wanandroid.view.BaseActivity;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.common_toolbar)
    Toolbar home_toolbar;
    @BindView(R.id.home_drawer)
    DrawerLayout home_drawer;
    @BindView(R.id.common_toolbar_title_tv)
    TextView toolbar_title;
    @BindView(R.id.home_nav)
    NavigationView home_nav;

    @Override
    protected BasePresenter createPresneter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(home_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, home_drawer, home_toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();
        toolbar_title.setText("开发小白");
        home_drawer.addDrawerListener(toggle);
        StatusBarUtils.immersive(this, home_toolbar);
        home_nav.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void initDataAndEvent() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (home_drawer!=null){
            home_drawer.closeDrawer(Gravity.START);
        }
        switch (menuItem.getItemId()){
            case R.id.nav_item_wan_android:
                break;
            case R.id.nav_item_collection:
                Toast.makeText(this, "nav_item_collection", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_item_logout:
                Toast.makeText(this, "nav_item_logout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_item_baout:
                Toast.makeText(this, "nav_item_baout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_item_setting:
                Toast.makeText(this, "nav_item_setting", Toast.LENGTH_SHORT).show();

                break;
        }
        return true;
    }
}
