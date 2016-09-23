package gt.lskj.com.geeknew.app;

import java.io.File;

/**
 * Created by Home on 16/9/18.
 */
public class Constants {
    //NavigationView default checked menuitem
    public static final String SP_CURRENT_ITEM = "current_item";
    public static final String TIANXING_API_KET = "0fb75b0ad97f9a5d0c10a1fb49aac3bf";
    public static final String CACHE_PATH = "";
    //请求成功
    public static final int SUCCESS = 200;
    //请求成功内容为空  用于设置empty view
    public static final int EMPTY = 204;

    //type
    public static final int TYPE_1 = 101;
    public static final int TYPE_2 = 102;
    public static final int TYPE_3 = 103;
    public static final int TYPE_4 = 104;
    public static final int TYPE_5 = 105;

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/response_cache";

    public static final int TYPE_WECHAT = 106;
    //无图模式TAG
    public static final String SP_NO_IMAGE = "no_image";
    public static final String SP_AUTO_CACHE = "auto_cache";



}
