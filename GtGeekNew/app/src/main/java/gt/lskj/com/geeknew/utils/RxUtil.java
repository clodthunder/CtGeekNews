package gt.lskj.com.geeknew.utils;

import gt.lskj.com.geeknew.https.ApiException;
import gt.lskj.com.geeknew.https.response.WxResPonse;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Home on 16/9/20.
 */

public class RxUtil {
    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * WxResPonse<T>
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<WxResPonse<T>, T> handleWXResult() {   //compose判断结果
        return new Observable.Transformer<WxResPonse<T>, T>() {
            @Override
            public Observable<T> call(Observable<WxResPonse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<WxResPonse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(WxResPonse<T> tWXHttpResponse) {
                        if (tWXHttpResponse.getCode() == 200) {
                            return createData(tWXHttpResponse.getNewslist());
                        } else {
                            return Observable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
