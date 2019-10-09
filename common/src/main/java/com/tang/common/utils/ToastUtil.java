package com.tang.common.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.tang.common.R;

/**
 * 描述: 重写toast
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/17.
 */
public class ToastUtil {

    /**
     * @param context 使用时的上下文
     * @param hint Toast
     */
    public static void show(Context context, String hint)
    {
        ToastMessage(context, hint);
    }

    /**
     * @param context
     * 		上下文
     * @param message
     * 		显示内容
     */
    private static void ToastMessage(Context context, String message)
    {
        ToastMessage(context, message, 200);
    }

    private static void ToastMessage(Context context, String message, int duration)
    {
        TextView text = new TextView(context);
        text.setText(message);
        text.setBackgroundResource(R.drawable.bg_toast);
        text.setTextColor(Color.parseColor("#FFFFFF"));
        text.setTextSize(12);
        text.setPadding(AppUtil.dip2px(context,10f), AppUtil.dip2px(context,5f), AppUtil.dip2px(context,10f), AppUtil.dip2px(context,5f));
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        toast.setView(text);
        toast.show();
    }
}
