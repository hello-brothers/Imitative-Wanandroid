package imitative.lh.com.wanandroid.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import imitative.lh.com.wanandroid.R;

/**
 * @Date 2019/9/18
 * @created by lh
 * @Describe:
 */
public class SearchView extends LinearLayout implements View.OnClickListener {

    private TextView tv_clearall;
    private EditText et_search;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.tool_search, this);
        tv_clearall = view.findViewById(R.id.tv_clearall);
        et_search = view.findViewById(R.id.et_search);
        tv_clearall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_clearall:
                et_search.setText("");
                break;
        }
    }
}
