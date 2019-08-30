package imitative.lh.com.wanandroid.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class AbstractSimpleFragment extends SupportFragment {
    private long clickTime;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initDataAndView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null && unbinder != Unbinder.EMPTY){
            unbinder.unbind();
            unbinder = null;
        }
    }

    /**
     * 处理回退事件
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {

            long currentTime = System.currentTimeMillis();
            if ((currentTime - clickTime) > Constants.DOUBLE_INTERVAL_TIME) {
                CommonUtils.showSnackMessage(getActivity(), getString(R.string.double_click_exit_tint));
                clickTime = System.currentTimeMillis();
            } else {
                getActivity().finish();
            }
        }
        return true;
    }

    /**
     * 获取当前activity的uI布局
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 用于处理必须在activityView中处理的ui操作
     */
    protected  void initView(){

    }

    /**
     * 初始化数据
     */
    protected abstract void initDataAndView();
}
