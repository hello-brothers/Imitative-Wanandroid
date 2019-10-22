package imitative.lh.com.wanandroid.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class EditWatch implements TextWatcher {

    private TextWatcher textWatcher;
    private static List<EditText> watcherList = new ArrayList<>();

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        for (int i = 0; i < watcherList.size() - 1; i++) {
            if (watcherList.get(i).getText().length() == 0){
                textWatcher.hasEmptyValue();
                return;
            }
        }
        if (watcherList.get(watcherList.size()-1).getText().length() > 0){
            textWatcher.hasNoEmptyValue();
        }else {
            textWatcher.hasEmptyValue();
        }
    }


    public interface TextWatcher{
        void hasNoEmptyValue();
        void hasEmptyValue();
    }


    public static void addTextWatcher(EditText textWatcher) {
        watcherList.add(textWatcher);
    }

    public void setTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }
}
