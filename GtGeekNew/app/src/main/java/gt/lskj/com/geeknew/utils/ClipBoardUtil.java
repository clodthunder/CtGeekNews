package gt.lskj.com.geeknew.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by Home on 16/9/22.
 */

public class ClipBoardUtil {
    /**
     * 保存文字到剪贴板
     *
     * @param context
     * @param text
     */
    public static void copyToClipBoard(Context context, String text) {
        ClipData clipData = ClipData.newPlainText("url", text);
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        ToastUtil.shortShow("已复制到剪贴板");
    }
}
