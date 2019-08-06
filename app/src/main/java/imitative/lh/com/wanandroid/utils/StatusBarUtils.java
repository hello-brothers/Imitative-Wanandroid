package imitative.lh.com.wanandroid.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

public class StatusBarUtils {
    public static void immersive(Activity activity){
        immersive(activity.getWindow());
    }

    private static void immersive(Window window) {
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
}
