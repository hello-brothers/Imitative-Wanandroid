package imitative.lh.com.wanandroid.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexboxLayout;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.List;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.network.bean.KnowledgeHierarchyData;
import imitative.lh.com.wanandroid.ui.holder.KnowledgeHierarchyListViewHolder;
import imitative.lh.com.wanandroid.widget.custom.FlexTextView;

/**
 * @Date 2019/9/11
 * @created by lh
 * @Describe:
 */
public class KnowledgeAdapter extends BaseQuickAdapter<KnowledgeHierarchyData, KnowledgeHierarchyListViewHolder> {

    public KnowledgeAdapter(int layoutResId, @Nullable List<KnowledgeHierarchyData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(KnowledgeHierarchyListViewHolder helper, KnowledgeHierarchyData item) {
        /**
         * 取消recyclerview的回收机制，避免滑动数据显示混乱
         */
//        helper.setIsRecyclable(false);
        helper.setText(R.id.knowledge_head, item.getName());
        FlexboxLayout knowleage_flexbox = helper.getView(R.id.knowledge_flexBox);
        if (knowleage_flexbox.getChildCount() >= item.getChildren().size()){
            return;
        }
        for (KnowledgeHierarchyData child : item.getChildren()) {
            FlexTextView flexTextView = new FlexTextView(mContext);
            flexTextView.setText(child.getName());
            flexTextView.setOnClickListener(v -> {
                if (listener != null){
                    listener.onItemClick(child.getName());
                }
            });
            knowleage_flexbox.addView(flexTextView);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(String position);
    }
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(KnowledgeHierarchyListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        return super.getItemView(layoutResId, parent);
    }


}
