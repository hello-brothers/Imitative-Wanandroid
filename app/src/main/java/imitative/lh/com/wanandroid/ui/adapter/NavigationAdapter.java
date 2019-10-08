package imitative.lh.com.wanandroid.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.network.bean.NavigationListData;
import imitative.lh.com.wanandroid.ui.fragment.NavigationFragment;
import imitative.lh.com.wanandroid.ui.holder.KnowledgeHierarchyListViewHolder;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import imitative.lh.com.wanandroid.widget.custom.FlexTextView;

/**
 * @Date 2019/9/2
 * @created by lh
 * @Describe:
 */
public class NavigationAdapter extends BaseQuickAdapter<NavigationListData, KnowledgeHierarchyListViewHolder> {

    private final FlexTextView.OnFlexClickListener onFlexClickListener;
    private FlexboxLayout knowleage_flexbox;

    public NavigationAdapter(int layoutResId, @Nullable List<NavigationListData> data, FlexTextView.OnFlexClickListener onFlexClickListener) {
        super(layoutResId, data);
        this.onFlexClickListener = onFlexClickListener;
    }


    @Override
    protected void convert(KnowledgeHierarchyListViewHolder helper, NavigationListData item) {
        helper.setText(R.id.knowledge_head, item.getName());
        knowleage_flexbox = helper.getView(R.id.knowledge_flexBox);
        knowleage_flexbox.removeAllViews();
        for (NavigationListData.NavigationData article : item.getArticles()) {
            FlexTextView flexTextView = new FlexTextView(mContext);
            flexTextView.setTextSize(Constants.KNOLEDGE_TEXT_SIZE);
            flexTextView.setText(article.getTitle());
            if (knowleage_flexbox.getChildCount() < item.getArticles().size()){
                knowleage_flexbox.addView(flexTextView);
            }
            flexTextView.setOnClickListener(v -> onFlexClickListener.onClick(article.getTitle(), article.getLink(), article.getId(), article.isCollect()));
        }

    }




}
