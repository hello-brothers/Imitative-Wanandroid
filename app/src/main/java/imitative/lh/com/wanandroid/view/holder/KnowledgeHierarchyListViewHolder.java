package imitative.lh.com.wanandroid.view.holder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import imitative.lh.com.wanandroid.R;

/**
 * @Date 2019/9/11
 * @created by lh
 * @Describe:
 */


public class KnowledgeHierarchyListViewHolder extends BaseViewHolder {
    @BindView(R.id.knowledge_head)
    TextView tv_head;
    @BindView(R.id.knowledge_flexBox)
    FlexboxLayout knowledge_flexbox;
    public KnowledgeHierarchyListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
