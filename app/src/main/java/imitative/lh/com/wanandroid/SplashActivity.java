package imitative.lh.com.wanandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.utils.StatusBarUtils;

public class SplashActivity extends AbstractSimpleActivity {

    @BindView(R.id.splash_view)
    LottieAnimationView splash_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }





    @Override
    protected void onDestroy() {
        cancelAnimation();
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initToolbar() {
        if (!WanAndroidApp.isFirstrun){

        }
        WanAndroidApp.isFirstrun = false;
        StatusBarUtils.immersive(this);

    }

    @Override
    protected void initDataAndEvent() {
        startAnimation(splash_view, "splash.json");
    }

    private void cancelAnimation() {
        cancelAnimation(splash_view);
    }

    private void startAnimation(LottieAnimationView mLottieAnimationView, String animationName) {
        mLottieAnimationView.setAnimation(animationName);
        mLottieAnimationView.playAnimation();
    }

    private void cancelAnimation(LottieAnimationView mLottieAnimationView) {
        if (mLottieAnimationView!=null){
            mLottieAnimationView.cancelAnimation();
        }
    }
}
