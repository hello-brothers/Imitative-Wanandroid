package imitative.lh.com.wanandroid;

import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbstractSimpleActivity extends AppCompatActivity {

    private Unbinder unbind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbind = ButterKnife.bind(this);
        initToolbar();
        initDataAndEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbind != null && unbind != Unbinder.EMPTY){
            unbind.unbind();
            unbind = null;
        }
    }

    /**
     * 获取当前activity的ui布局
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化Toolbar
     */
    protected abstract void initToolbar();

    /**
     * 初始化数据
     */
    protected abstract void initDataAndEvent();
}
