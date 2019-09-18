package imitative.lh.com.wanandroid.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import imitative.lh.com.wanandroid.R;

/**
 * @Date 2019/8/29
 * @created by lh
 * @Describe:
 */
public class EssayListAdapter extends BaseQuickAdapter {

    public EssayListAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        helper.addOnClickListener(R.id.im_start);
    }




}
