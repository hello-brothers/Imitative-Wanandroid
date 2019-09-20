package imitative.lh.com.wanandroid.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.ui.holder.EssayListViewHolder;

/**
 * @Date 2019/8/29
 * @created by lh
 * @Describe:
 */
public class EssayListAdapter extends BaseQuickAdapter<EssayListData.EssayData, EssayListViewHolder> {

    public EssayListAdapter(int layoutResId, @Nullable List<EssayListData.EssayData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(EssayListViewHolder helper, EssayListData.EssayData essayData) {
        helper.addOnClickListener(R.id.im_start);
        helper.setText(R.id.essay_title, essayData.getTitle());
        helper.setText(R.id.item_essay_date, essayData.getNiceDate());
        helper.setText(R.id.essay_author, essayData.getAuthor());
    }


}
