package imitative.lh.com.wanandroid.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.network.bean.WxArticalListData;
import imitative.lh.com.wanandroid.ui.holder.WxDetailArticleViewHolder;

/**
 * @Date 2019/9/11
 * @created by lh
 * @Describe:
 */
public class WxDetailArticleAdapter extends BaseQuickAdapter<WxArticalListData.WxArticalData, WxDetailArticleViewHolder> {
    public WxDetailArticleAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }
    @BindView(R.id.essay_title)
    TextView essay_title;
    @BindView(R.id.item_essay_date)
    TextView essay_data;
    @Override
    protected void convert(WxDetailArticleViewHolder helper, WxArticalListData.WxArticalData item) {
        helper.setText(R.id.essay_title, item.getTitle());
        helper.setText(R.id.essay_author, item.getAuthor());
        helper.setText(R.id.chapter, item.getSuperChapterName() + " · " + item.getChapterName());
        helper.setText(R.id.item_essay_date, item.getNiceDate());
        helper.setImageResource(R.id.im_start, item.isCollect() ? R.drawable.ic_star : R.drawable.ic_unstar);
    }
}
