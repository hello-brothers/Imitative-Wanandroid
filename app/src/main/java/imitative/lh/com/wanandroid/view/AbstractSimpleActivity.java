package imitative.lh.com.wanandroid.view;

import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class AbstractSimpleActivity extends SupportActivity {

    private Unbinder unbind;
    protected AbstractSimpleActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbind = ButterKnife.bind(this);
        mActivity = this;
        initToolbar();
        onViewCreated();
        initDataAndEvent();
    }

    protected abstract void onViewCreated();

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
