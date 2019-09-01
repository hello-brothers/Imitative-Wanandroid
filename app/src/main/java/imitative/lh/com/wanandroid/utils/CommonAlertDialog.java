package imitative.lh.com.wanandroid.utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import imitative.lh.com.wanandroid.R;
import imitative.lh.com.wanandroid.app.WanAndroidApp;

/**
 * @Date 2019/8/28
 * @created by lh
 * @Describe:
 */
public class CommonAlertDialog {

    private AlertDialog alertDialog;

    public static CommonAlertDialog newInstance(){
        return CommonAlertDialogHolder.COMMON_ALERT_DIALOG;
    }

    private static class CommonAlertDialogHolder{
        private static final CommonAlertDialog COMMON_ALERT_DIALOG = new CommonAlertDialog();
    }

    public void showDialog(Activity mActivity, String content, String secondContent, String btnFirstContent, String btnNextContent,
                           View.OnClickListener onFirstClickListener,
                           View.OnClickListener onNextClickListener){
        if (mActivity == null){
            return;
        }
        if (alertDialog == null){
            alertDialog = new AlertDialog.Builder(mActivity, R.style.common_dialog).create();

        }
        if (!alertDialog.isShowing()){
            alertDialog.show();
        }
        alertDialog.setCanceledOnTouchOutside(false);
        Window window = alertDialog.getWindow();
        if (window != null){

            window.setContentView(R.layout.common_alert_dialog);
            TextView tv_content = window.findViewById(R.id.dialog_content);
            tv_content.setText(content);
            TextView contentSecond = window.findViewById(R.id.dialog_content_second);
            contentSecond.setText(secondContent);
            Button btn_ok = window.findViewById(R.id.dialog_btn);
            btn_ok.setText(btnFirstContent);
            Button btn_no = window.findViewById(R.id.dialog_negative_btn);
            btn_no.setText(btnNextContent);
            btn_no.setVisibility(View.VISIBLE);
            View btnDivider = window.findViewById(R.id.dialog_btn_divider);
            btnDivider.setVisibility(View.VISIBLE);
            btn_no.setOnClickListener(onNextClickListener);
            btn_ok.setOnClickListener(onFirstClickListener);

        }
    }

    public void cancelDialog(boolean isAction){
        if (isAction && alertDialog != null && alertDialog.isShowing()){
            alertDialog.dismiss();
            alertDialog = null;
        }
    }
}
