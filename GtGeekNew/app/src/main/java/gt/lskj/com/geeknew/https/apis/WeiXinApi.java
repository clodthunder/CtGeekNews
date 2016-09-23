package gt.lskj.com.geeknew.https.apis;

import java.util.List;

import gt.lskj.com.geeknew.https.beans.WXItemBean;
import gt.lskj.com.geeknew.https.response.WxResPonse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Home on 16/9/20.
 * 微信Api
 */
//http://www.tianapi.com/#wxnew
public interface WeiXinApi {
    static final String BaseURL = "http://api.tianapi.com";

    /**
     * 微信精选列表
     */
    @GET("/wxnew")
    Observable<WxResPonse<List<WXItemBean>>> getWXHotData(@Query("key") String key, @Query("num") int num, @Query("page") int page);


    /**
     * 微信精选列表
     */
    @GET("wxnew")
    Observable<WxResPonse<List<WXItemBean>>> getWXHotSearchData(@Query("key") String key, @Query("num") int num, @Query("page") int page, @Query("word") String word);


}
