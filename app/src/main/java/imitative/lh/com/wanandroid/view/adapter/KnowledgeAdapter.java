package imitative.lh.com.wanandroid.view.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.view.holder.KnowledgeHierarchyListViewHolder;
import imitative.lh.com.wanandroid.widget.custom.FlexTextView;

/**
 * @Date 2019/9/11
 * @created by lh
 * @Describe:
 */
public class KnowledgeAdapter extends BaseQuickAdapter<String, KnowledgeHierarchyListViewHolder> {
    private final List data;

    public KnowledgeAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
        this.data = data;
    }


    @Override
    protected void convert(KnowledgeHierarchyListViewHolder helper, String item) {
        FlexboxLayout knowleage_flexbox = helper.getView(R.id.knowledge_flexBox);
        for (int i = 0; i < data.size(); i++) {
            FlexTextView flexTextView = new FlexTextView(mContext);
            flexTextView.setTag(i);
            flexTextView.setText("sfd");
            int finalI = i;
            flexTextView.setOnClickListener(v -> {
                if (listener != null){
                    listener.onItemClick(finalI);
                }
            });
            if (knowleage_flexbox.getChildCount() < data.size()){
                knowleage_flexbox.addView(flexTextView);
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
