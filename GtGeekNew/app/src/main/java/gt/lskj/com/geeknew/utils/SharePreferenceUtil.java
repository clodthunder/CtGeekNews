package gt.lskj.com.geeknew.utils;

import android.content.Context;
import android.content.SharedPreferences;

import gt.lskj.com.geeknew.app.App;
import gt.lskj.com.geeknew.app.Constants;


/**
 * Created by Home on 16/9/18.
 */
public class SharePreferenceUtil {

    private static final int DEFAULT_CURRENT_ITEM = Constants.TYPE_1;
    //无图模式
    private static final boolean DEFAULT_NO_IMAGE = false;
    //webview
    private static final boolean DEFAULT_AUTO_SAVE = true;

    public static SharedPreferences getAppSp() {
        return App.getInstance().getSharedPreferences(Constants.SP_CURRENT_ITEM, Context.MODE_PRIVATE);
    }

    public static int getCurrentItem() {
        return getAppSp().getInt(Constants.SP_CURRENT_ITEM, DEFAULT_CURRENT_ITEM);
    }

    public static void setCurrentItem(int item) {
        getAppSp().edit().putInt(Constants.SP_CURRENT_ITEM, item).commit();
    }

    public static boolean getNoImageState() {
        return getAppSp().getBoolean(Constants.SP_NO_IMAGE, DEFAULT_NO_IMAGE);
    }

    public static void setNoImageState(boolean state) {
        getAppSp().edit().putBoolean(Constants.SP_NO_IMAGE, state).commit();
    }


    public static boolean getAutoCacheState() {
        return getAppSp().getBoolean(Constants.SP_AUTO_CACHE, DEFAULT_AUTO_SAVE);
    }

    public static boolean setautoCacheState(boolean state) {
        return getAppSp().edit().putBoolean(Constants.SP_AUTO_CACHE, state).commit();
    }

}
