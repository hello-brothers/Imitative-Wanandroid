package imitative.lh.com.wanandroid.widget.custom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
    private static final String TAG = SearchView.class.getSimpleName();


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


        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    CharSequence content = v.getText();
                    if (checkListener()){
                        listener.searchContent(content.toString());
                    }
                }
                return false;
            }
        });

        et_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                if (checkListener()){
                    listener.touchEditText();
                }
            }
        });
    }

    /**
     * 判断listener是否存在
     * @return
     */
    private boolean checkListener() {
        if (listener == null){
            return false;
        }
        return true;
    }

    /**
     * 清空EditText内容
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_clearall:
                et_search.setText("");
                break;
        }
    }



    public interface OnStatusChangListener{

        /**
         * 点击EditText回调
         */
        void touchEditText();

        /**
         *回调EditText的内容
         * @param content 需要搜索的字段
         */
        void searchContent(String content);

    }

    private OnStatusChangListener listener;

    public void setOnStatusChangListener(OnStatusChangListener onStatusChangListener) {
        this.listener = onStatusChangListener;
    }

}
