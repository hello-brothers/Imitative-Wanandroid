package imitative.lh.com.wanandroid.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.view.Window;

import imitative.lh.com.wanandroid.MainActivity;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.View.SYSTEM_UI_FLAG_VISIBLE;
import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

public class StatusBarUtils {
    public static void immersive(Activity activity){
        immersive(activity, null);
    }

    public static void immersive(Activity activity, Toolbar isSolveImmersive){
        immersive(activity.getWindow());
        if (isSolveImmersive != null){
            solveImmersive(activity.getWindow(), isSolveImmersive);
        }
    }

    public static void immersive(Window window) {
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

    public static void setStatusColot(Window window, int color, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){

            window.clearFlags(FLAG_TRANSLUCENT_STATUS);
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
            window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    private static void solveImmersive(Window window, Toolbar toolbar){
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
