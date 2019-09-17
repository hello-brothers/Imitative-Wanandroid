package imitative.lh.com.wanandroid.view.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.billy.android.swipe.SmartSwipe;
import com.billy.android.swipe.consumer.SlidingConsumer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.view.holder.CollectionListViewHolder;
import imitative.lh.com.wanandroid.view.holder.KnowledgeHierarchyListViewHolder;

public class CollectionAdapter extends BaseQuickAdapter<String, CollectionListViewHolder>  {

    private RelativeLayout liker_content;
    private TextView tv_delete;

    public CollectionAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(CollectionListViewHolder helper, String item) {
        liker_content = helper.getView(R.id.liker_content);
        tv_delete = helper.getView(R.id.tv_delet);
        helper.addOnClickListener(R.id.tv_delet);
    }

    @Override
    public void onBindViewHolder(CollectionListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        SmartSwipe.wrap(liker_content)
                .addConsumer(new SlidingConsumer())
                .setHorizontalDrawerView(tv_delete);
    }
}
