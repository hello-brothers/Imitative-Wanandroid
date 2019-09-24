package imitative.lh.com.wanandroid.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import imitative.lh.com.wanandroid.R;

/**
 * @Date 2019/9/24
 * @created by lh
 * @Describe:
 */
public class ProjectDetailViewHolder extends BaseViewHolder {

    public ProjectDetailViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
