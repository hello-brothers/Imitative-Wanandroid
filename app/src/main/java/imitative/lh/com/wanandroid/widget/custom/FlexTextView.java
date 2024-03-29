package imitative.lh.com.wanandroid.widget.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.network.bean.NavigationListData;

/**
 * @Date 2019/9/11
 * @created by lh
 * @Describe:
 */
public class FlexTextView extends TextView {
    private Drawable bg = getResources().getDrawable(R.drawable.text_withgray_bg);

    public FlexTextView(Context context) {
        this(context, null);
    }

    public FlexTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlexTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setBackground(bg);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) this.getLayoutParams();
        layoutParams.setMargins(10, 10, 10, 10);
        this.setLayoutParams(layoutParams);
    }

    public interface OnFlexClickListener{
        void onClick(String title, String link, int id, boolean isCollect);
    }
    
}
