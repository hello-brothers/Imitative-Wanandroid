package imitative.lh.com.wanandroid.view.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.view.holder.KnowledgeHierarchyListViewHolder;
import imitative.lh.com.wanandroid.widget.custom.FlexTextView;

/**
 * @Date 2019/9/2
 * @created by lh
 * @Describe:
 */
public class NavigationAdapter extends BaseQuickAdapter<String, KnowledgeHierarchyListViewHolder> {
    private final List data;

    public NavigationAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(KnowledgeHierarchyListViewHolder helper, String item) {
        TextView tv_haed = helper.getView(R.id.knowledge_head);
        tv_haed.setText(item);
        FlexboxLayout knowleage_flexbox = helper.getView(R.id.knowledge_flexBox);
        for (int i = 0; i < 7; i++) {
            FlexTextView flexTextView = new FlexTextView(mContext);
            flexTextView.setTag(i);
            flexTextView.setText("sfd");
            if (knowleage_flexbox.getChildCount() < 7){
                knowleage_flexbox.addView(flexTextView);
            }
        }
    }
}
