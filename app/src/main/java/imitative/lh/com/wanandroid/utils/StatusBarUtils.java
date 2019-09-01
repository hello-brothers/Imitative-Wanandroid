package imitative.lh.com.wanandroid.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import imitative.lh.com.wanandroid.MainActivity;
import imitative.lh.com.wanandroid.R;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.View.SYSTEM_UI_FLAG_VISIBLE;
import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

public class StatusBarUtils {
    /**
     * 设置沉浸式
     * @param activity 上下文
     * @param toolbar 如果不传toolbar 则直接设置全屏
     * @param isFull true: 滑动时状态栏一起消失
     *               false: 滑动时留下状态栏
     */
    public static void immersive(Activity activity, Toolbar toolbar, boolean isFull){
        if (toolbar == null){
            immersiveWithNoStatue(activity.getWindow());
            return;
        }
        if (!isFull){
            immersiveWithStatue(activity, toolbar);
        }else {
            immersiveWithNoStatue(activity.getWindow());
            addCustomStatus(activity.getWindow(), toolbar);
        }
    }

    private static void immersiveWithStatue(Activity activity, Toolbar toolbar){
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            createStatus(activity);
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility |= SYSTEM_UI_FLAG_LAYOUT_STABLE ;
            window.getDecorView().setSystemUiVisibility(visibility);
            window.clearFlags(FLAG_TRANSLUCENT_STATUS|FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.addFlags(FLAG_TRANSLUCENT_STATUS);
        }
    }

    private static void createStatus(Activity activity){
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        /**
         * 判断是否已经添加了StatusBarView
         */
        int childCount = decorView.getChildCount();
        if (childCount>0 && decorView.getChildAt(childCount-1) instanceof StatusBarView){
            decorView.getChildAt(childCount-1).setBackgroundColor(Color.BLACK);
            decorView.getChildAt(childCount - 1).setBackground(activity.getApplicationContext().getDrawable(R.drawable.common_toolbar_bg));
        }
        //绘制一个和状态栏一样高的view 透明
        StatusBarView statusBarView = new StatusBarView(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity.getWindow()));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackground(activity.getApplicationContext().getDrawable(R.drawable.common_toolbar_bg));
        decorView.addView(statusBarView);
    }

    public static void immersiveWithNoStatue(Window window){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility |= SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(visibility);
            window.clearFlags(FLAG_TRANSLUCENT_STATUS|FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.addFlags(FLAG_TRANSLUCENT_STATUS);
        }
    }


    private static void setStatusColor(Window window, int color, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){

            window.clearFlags(FLAG_TRANSLUCENT_STATUS);
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
            window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    private static void addCustomStatus(Window window, Toolbar toolbar){
        ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
        layoutParams.height += getStatusBarHeight(window );
        toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getPaddingTop() + getStatusBarHeight(window), toolbar.getPaddingRight(), toolbar.getPaddingBottom());

    }

    private static int getStatusBarHeight(Window window) {
        int identifier = window.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0){
            return window.getContext().getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }
}
