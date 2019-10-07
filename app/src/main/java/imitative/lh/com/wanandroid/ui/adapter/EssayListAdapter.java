package imitative.lh.com.wanandroid.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
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
public class EssayListAdapter extends BaseMultiItemQuickAdapter<EssayData, EssayListViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public EssayListAdapter(List<EssayData> data) {
        super(data);
        addItemType(EssayData.TYPE_TOP, R.layout.item_topessay);
        addItemType(EssayData.TYPE_NORMAL, R.layout.item_essay);
    }

    @Override
    protected int getDefItemViewType(int position) {
        return super.getDefItemViewType(position);
    }

    @Override
    protected void convert(EssayListViewHolder helper, EssayData essayData) {
        helper.addOnClickListener(R.id.im_start);
        switch (helper.getItemViewType()){
            case EssayData.TYPE_TOP:
                helper.addOnClickListener(R.id.im_start);
                helper.setText(R.id.essay_title, essayData.getTitle());
                helper.setText(R.id.item_essay_date, essayData.getNiceDate());
                helper.setText(R.id.essay_author, TextUtils.isEmpty(essayData.getAuthor()) ? essayData.getShareUser() : essayData.getAuthor());
                helper.setText(R.id.chapter, essayData.getSuperChapterName() + " · " + essayData.getChapterName());
                helper.setImageResource(R.id.im_start, essayData.isCollect() ? R.drawable.ic_star : R.drawable.ic_unstar);
                break;
            case EssayData.TYPE_NORMAL:
                helper.addOnClickListener(R.id.im_start);
                helper.setText(R.id.essay_title, essayData.getTitle());
                helper.setText(R.id.item_essay_date, essayData.getNiceDate());
                helper.setText(R.id.essay_author, TextUtils.isEmpty(essayData.getAuthor()) ? essayData.getShareUser() : essayData.getAuthor());
                helper.setText(R.id.chapter, essayData.getSuperChapterName() + " · " + essayData.getChapterName());
                helper.setImageResource(R.id.im_start, essayData.isCollect() ? R.drawable.ic_star : R.drawable.ic_unstar);
                break;
        }
    }




}
