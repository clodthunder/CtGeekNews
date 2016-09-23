package gt.lskj.com.geeknew.https.response;

/**
 * Created by Home on 16/9/20.
 */

public class WxResPonse<T> extends BaseResponse<T> {

    private T newslist;

    public T getNewslist() {
        return newslist;
    }

    public void setNewslist(T newslist) {
        this.newslist = newslist;
    }
}
