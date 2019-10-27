package imitative.lh.com.wanandroid.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import imitative.lh.com.wanandroid.R;

public class ItemwithCheckBox extends RelativeLayout {

    private TextView item_title;
    private TextView item_subtitle;
    private CheckBox cb_checkbox;
    private RelativeLayout rl_item;

    public ItemwithCheckBox(Context context) {
        this(context, null);
    }

    public ItemwithCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemwithCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.item_with_checkbox, this);
        init();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemwithCheckBox);
        String item_title = typedArray.getString(R.styleable.ItemwithCheckBox_item_title);
        String item_subtitle = typedArray.getString(R.styleable.ItemwithCheckBox_item_subtitle);
        boolean item_checked = typedArray.getBoolean(R.styleable.ItemwithCheckBox_ischecked, false);
        setText(item_title, item_subtitle, item_checked);
        typedArray.recycle();


    }

    private void init() {
        item_title      = findViewById(R.id.item_title);
        item_subtitle   = findViewById(R.id.item_subtitle);
        cb_checkbox     = findViewById(R.id.cb_checkbox);
        rl_item         = findViewById(R.id.rl_item);
        rl_item.setOnClickListener(v -> {
            if (cb_checkbox.isChecked()){
                cb_checkbox.setChecked(false);
            }else {
                cb_checkbox.setChecked(true);
            }
        });
    }

    private void setText(String title, String subTitle, boolean isChecked){
        item_subtitle.setText(subTitle);
        item_title.setText(title);
        cb_checkbox.setChecked(isChecked);
    }

    public void setOnItemViewClickListener(OnClickListener listener){
        rl_item.setOnClickListener(listener);
    }

    public boolean isChecked(){
        return cb_checkbox.isChecked();
    }

    public CheckBox getCb_checkbox(){
        return cb_checkbox;
    }
}
