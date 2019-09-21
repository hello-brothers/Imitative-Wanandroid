package imitative.lh.com.wanandroid.widget.custom;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.base.fragment.AbstractSimpleFragment;
import imitative.lh.com.wanandroid.base.fragment.BaseFragment;

public class SearchContainer extends FrameLayout {

    private ArrayList<BaseFragment> fragments;

    public SearchContainer(Context context) {
        this(context, null);
    }

    public SearchContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void serUpFragment(BaseFragment... fragmentViews){
        fragments = new ArrayList<>();
        for (int i = 0; i < fragmentViews.length; i++) {
            fragments.add(fragmentViews[i]);
        }
    }


}
