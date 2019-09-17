package imitative.lh.com.wanandroid.view.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import imitative.lh.com.wanandroid.R;

public class CollectionListViewHolder extends BaseViewHolder {
    @BindView(R.id.item_liker_total)
    LinearLayout liker_total;
    @BindView(R.id.tv_delet)
    TextView liker_delete;
    public CollectionListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
