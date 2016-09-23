package gt.lskj.com.geeknew.https.retrofit2;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import gt.lskj.com.geeknew.BuildConfig;
import gt.lskj.com.geeknew.app.Constants;
import gt.lskj.com.geeknew.https.apis.WeiXinApi;
import gt.lskj.com.geeknew.https.beans.WXItemBean;
import gt.lskj.com.geeknew.https.response.WxResPonse;
import gt.lskj.com.geeknew.utils.NetworkUtil;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Home on 16/9/20.
 */

public class RetrofitHelper {

    private static OkHttpClient mOkHttpClient = null;
    private static WeiXinApi mWxAPiService = null;

    public RetrofitHelper() {
        init();
    }

    private void init() {
        initOkHttp();
        mWxAPiService = getWxApiService();
    }

    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // https://drakeet.me/retrofit-2-0-okhttp-3-0-config
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        mOkHttpClient = builder.build();
    }

    //获取WxApiService 实例
    public WeiXinApi getWxApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeiXinApi.BaseURL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(WeiXinApi.class);
    }

    /**
     * @param num  每页显示数据条数
     * @param page 当前页
     * @return
     */
    public Observable<WxResPonse<List<WXItemBean>>> fetchWechatListInfo(int num, int page) {
        return mWxAPiService.getWXHotData(Constants.TIANXING_API_KET, num, page);
    }

    /**
     * @param num 每页显示数据条数
     * @param page 当前页
     * @param word 搜索关键字
     * @return
     */
    public Observable<WxResPonse<List<WXItemBean>>> fetchWechatSearchListInfo(int num, int page, String word) {
        return mWxAPiService.getWXHotSearchData(Constants.TIANXING_API_KET, num, page, word);
    }
}
