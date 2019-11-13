package imitative.lh.com.wanandroid.ui.fragment;

import android.util.Log;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCheckedTextView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.base.fragment.BaseFragment;
import imitative.lh.com.wanandroid.base.presenter.AbstractPresenter;
import imitative.lh.com.wanandroid.component.RxBus;
import imitative.lh.com.wanandroid.contract.mainpager.SettingContract;
import imitative.lh.com.wanandroid.core.event.HottopEvent;
import imitative.lh.com.wanandroid.presenter.SettingPresenter;
import imitative.lh.com.wanandroid.widget.custom.ItemwithCheckBox;

/**
 * @Date 2019/8/28
 * @created by lh
 * @Describe:
 */
public class SettingFragment extends BaseFragment<SettingPresenter> implements SettingContract.View {
    @BindView(R.id.model_noimg)
    ItemwithCheckBox model_noimg;
    @BindView(R.id.show_hottop)
    ItemwithCheckBox show_hottop;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting_pager;
    }

    @Override
    protected void initView() {
        model_noimg.getCb_checkbox().setChecked(presenter.getNoImageState());
        show_hottop.getCb_checkbox().setChecked(!presenter.getIsLoadTopEssayData());
    }

    @Override
    protected void initDataAndView() {
        registerClickEvent();
    }

    public static SettingFragment newInstance(){
        return new SettingFragment();
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    public void registerClickEvent(){


        presenter.addDisposible(RxCompoundButton.checkedChanges(model_noimg.getCb_checkbox())
                .filter(o -> presenter != null)
                .subscribe(aBoolean -> presenter.setNoImageState(aBoolean)));

        presenter.addDisposible(RxCompoundButton.checkedChanges(show_hottop.getCb_checkbox())
                .filter(o -> presenter != null)
                .subscribe(aBoolean -> {
                    presenter.setIsLoadTopEssayData(!aBoolean);
                    RxBus.getDefault().post(new HottopEvent());
                }));
    }
}
