package imitative.lh.com.wanandroid.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.billy.android.swipe.SmartSwipe;
import com.billy.android.swipe.consumer.SlidingConsumer;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.ui.holder.CollectionListViewHolder;

public class CollectionAdapter extends BaseQuickAdapter<EssayData, CollectionListViewHolder>  {

    private RelativeLayout liker_content;
    private TextView tv_delete;

    public CollectionAdapter(int layoutResId, @Nullable List<EssayData> data) {
        super(layoutResId, data);
    }


    @Override
    public void onBindViewHolder(CollectionListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        SmartSwipe.wrap(liker_content)
                .addConsumer(new SlidingConsumer())
                .setRightDrawerView(tv_delete);
    }

    @Override
    protected void convert(CollectionListViewHolder helper, EssayData item) {
        liker_content = helper.getView(R.id.liker_content);
        tv_delete = helper.getView(R.id.tv_delet);
        helper.addOnClickListener(R.id.tv_delet);

        helper.setText(R.id.item_text, item.getTitle());
        helper.setText(R.id.essay_author, item.getAuthor());
        helper.setText(R.id.liker_chapter, item.getSuperChapterName() + " · " + item.getChapterName());
        helper.setText(R.id.item_essay_date, item.getNiceDate());
        Glide.with(mContext).load(item.getEnvelopePic()).into((ImageView) helper.getView(R.id.item_img));

        helper.addOnClickListener(R.id.tv_delet);
        helper.addOnClickListener(R.id.liker_content);
    }
}
