package imitative.lh.com.wanandroid.ui.holder;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import imitative.lh.com.wanandroid.R;

/**
 * @Date 2019/9/24
 * @created by lh
 * @Describe:
 */
public class WxDetailArticleViewHolder extends BaseViewHolder {

    @BindView(R.id.im_start)
    ImageView img_start;
    public WxDetailArticleViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
