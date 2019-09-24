package imitative.lh.com.wanandroid.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.ui.holder.EssayListViewHolder;

/**
 * @Date 2019/8/29
 * @created by lh
 * @Describe:
 */
public class EssayListAdapter extends BaseQuickAdapter<EssayData, EssayListViewHolder> {

    public EssayListAdapter(int layoutResId, @Nullable List<EssayData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(EssayListViewHolder helper, EssayData essayData) {
        helper.addOnClickListener(R.id.im_start);
        helper.setText(R.id.essay_title, essayData.getTitle());
        helper.setText(R.id.item_essay_date, essayData.getNiceDate());
        helper.setText(R.id.essay_author, TextUtils.isEmpty(essayData.getAuthor()) ? essayData.getShareUser() : essayData.getAuthor());
        helper.setText(R.id.chapter, essayData.getSuperChapterName() + " · " + essayData.getChapterName());
        helper.setImageResource(R.id.im_start, essayData.isCollect() ? R.drawable.ic_star : R.drawable.ic_unstar);
    }


}
