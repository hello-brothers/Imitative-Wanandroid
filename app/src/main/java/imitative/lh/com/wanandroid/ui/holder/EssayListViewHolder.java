package imitative.lh.com.wanandroid.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import imitative.lh.com.wanandroid.R;

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe:
 */
public class EssayListViewHolder extends BaseViewHolder {
    @BindView(R.id.essay_title)
    TextView essay_title;
    @BindView(R.id.item_essay_date)
    TextView essay_data;
    public EssayListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
