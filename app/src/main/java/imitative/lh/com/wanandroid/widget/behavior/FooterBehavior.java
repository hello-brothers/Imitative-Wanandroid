package imitative.lh.com.wanandroid.widget.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;

/**
 * @Date 2019/8/29
 * @created by lh
 * @Describe:
 */
public class FooterBehavior extends FloatingActionButton.Behavior {

    private boolean isAnimating = false;
    private boolean isShow = true;

    public FooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        //向上滑动 隐藏
        if ( (dyConsumed > 0 || dyUnconsumed > 0) && !isAnimating && isShow){
            ViewCompat.animate(child)
                    .translationY(350)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .setDuration(400)
                    .setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {
                            isAnimating = true;
                            isShow = false;
                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            isAnimating = false;
                        }

                        @Override
                        public void onAnimationCancel(View view) {
                            isAnimating = false;
                        }
                    })
                    .start();

        }else if ((dyConsumed < 0 || dyUnconsumed < 0)&& !isAnimating && !isShow){
            ViewCompat.animate(child)
                    .setDuration(400)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .translationY(0)
                    .setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {
                            isAnimating = true;
                            isShow = true;
                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            isAnimating = false;
                        }

                        @Override
                        public void onAnimationCancel(View view) {
                            isAnimating = false;
                        }
                    })
                    .start();
        }
    }
}
