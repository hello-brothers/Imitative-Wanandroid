package imitative.lh.com.wanandroid.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.network.bean.ProjectListData;
import imitative.lh.com.wanandroid.ui.holder.ProjectDetailViewHolder;

/**
 * @Date 2019/9/17
 * @created by lh
 * @Describe:
 */
public class ProjectDetailAdapter extends BaseQuickAdapter<ProjectListData.ProjectData, ProjectDetailViewHolder> {
    public ProjectDetailAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ProjectDetailViewHolder helper, ProjectListData.ProjectData item) {
        helper.setText(R.id.item_text, item.getTitle());
        helper.setText(R.id.chaptername, item.getSuperChapterName() + " · " + item.getChapterName());
        helper.setText(R.id.item_desc, item.getDesc());
        helper.setText(R.id.item_essay_date, item.getNiceDate());
        helper.setText(R.id.essay_author,item.getAuthor());
        helper.setImageResource(R.id.im_start, item.isCollect() ? R.drawable.ic_star : R.drawable.ic_unstar);
        Glide.with(mContext).load(item.getEnvelopePic()).into((ImageView) helper.getView(R.id.item_img));

    }
}
