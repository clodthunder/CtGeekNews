package gt.lskj.com.geeknew.gilde;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import gt.lskj.com.geeknew.utils.SharePreferenceUtil;

/**
 * Created by Home on 16/9/22.
 */

public class ImageLoader {

    //无图模式
    public static void load(Context context, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
//        if (!SharePreferenceUtil.getNoImageState()) {
        Glide.with(context).load(url).asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
//        }
    }

    public static void load(Activity activity, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (!SharePreferenceUtil.getNoImageState()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (!activity.isDestroyed()) {
                    Glide.with(activity).load(url).asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
                }
            }
        }
    }

    //不缓存，全部从网络加载
    public static void loadNoCache(Context context, String url, ImageView iv) {
        if (!SharePreferenceUtil.getNoImageState()) {
            Glide.with(context).load(url)
                    .crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
        }
    }

    public static void loadNoCache(Activity activity, String url, ImageView iv) {    //不缓存，全部从网络加载
        if (!SharePreferenceUtil.getNoImageState()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (!activity.isDestroyed()) {
                    Glide.with(activity).load(url).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
                }
            }
        }
    }

    //    String baseImageUrl = "https://futurestud.io/images/example.png";
    public static void loadDynamic(Context context, String url, ImageView imageView) {
        CustomImageSizeModel customImageRequest = new CustomImageSizeModelFutureStudio(url);
        Glide.with(context)
                .using(new CustomImageSizeUrlLoader(context))
                .load(new CustomImageSizeModelFutureStudio(url))
                .into(imageView);

    }
}
