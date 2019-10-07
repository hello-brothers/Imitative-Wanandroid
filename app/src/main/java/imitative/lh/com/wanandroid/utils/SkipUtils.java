package imitative.lh.com.wanandroid.utils;

import android.content.Context;
import android.content.Intent;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.ui.activity.EssayDetailActivity;

/**
 * 页面跳转
 */
public class SkipUtils {
    public static void startEssayDetailActivity(Context _mActivity, String title, String link, int id, boolean isCollected, boolean isCollectPage){
        Intent intent = new Intent(_mActivity, EssayDetailActivity.class);
        intent.putExtra(Constants.ESSEY_TITLE, title);
        intent.putExtra(Constants.ESSAY_LINK, link);
        intent.putExtra(Constants.ESSAY_ID, id);
        intent.putExtra(Constants.IS_COLLECTION, isCollected);
        intent.putExtra(Constants.IS_PAGECOLLECT, isCollectPage);
        _mActivity.startActivity(intent);
    }
}
