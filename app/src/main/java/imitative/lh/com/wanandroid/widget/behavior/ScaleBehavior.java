package imitative.lh.com.wanandroid.widget.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;

/**
 * 向下滑动 缩小 向上 还原
 * @param <V>
 */
public class ScaleBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    FastOutLinearInInterpolator mFastOutLinearInInterpolator = new FastOutLinearInInterpolator();
    LinearOutSlowInInterpolator mLinearOutSlowInInterpolator =  new LinearOutSlowInInterpolator();
    /**
     * 必须实现两个参数的构造方法
     * @param context
     * @param attrs
     */
    public ScaleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE && !animatorRunning){//向下滑动 隐藏
            scaledHide(child);
        }else if (dyConsumed < 0 && child.getVisibility() == View.INVISIBLE && !animatorRunning){//向上滑动 显示
            scaledShow(child);
        }
    }

    private void scaledShow(final View child) {
        ViewCompat.animate(child)
                .scaleX(1)
                .scaleY(1)
                .setDuration(600)
                .setInterpolator(mLinearOutSlowInInterpolator)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        animatorRunning = true;
                        child.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        animatorRunning = false;
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        animatorRunning = false;
                    }
                });
    }
    boolean animatorRunning;
    private void scaledHide(View child) {
        ViewCompat.animate(child)
                .scaleX(0)
                .scaleY(0)
                .setDuration(600)
                .setInterpolator(mFastOutLinearInInterpolator)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        animatorRunning = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        view.setVisibility(View.INVISIBLE);
                        animatorRunning = false;
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        animatorRunning = false;
                    }
                });
    }
}
