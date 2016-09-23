package gt.lskj.com.geeknew.ui.module.main.presenter;

import java.util.List;

import javax.inject.Inject;

import gt.lskj.com.geeknew.app.Constants;
import gt.lskj.com.geeknew.component.RxBus;
import gt.lskj.com.geeknew.https.beans.WXItemBean;
import gt.lskj.com.geeknew.https.event.SearchEvent;
import gt.lskj.com.geeknew.https.response.WxResPonse;
import gt.lskj.com.geeknew.https.retrofit2.RetrofitHelper;
import gt.lskj.com.geeknew.ui.base.RxPresenter;
import gt.lskj.com.geeknew.ui.module.main.contract.MainFragmentContract;
import gt.lskj.com.geeknew.utils.RxUtil;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Home on 16/9/20.
 */

public class MainFragmentPresenter
        extends RxPresenter<MainFragmentContract.View> implements MainFragmentContract.Presenter {
    private static final int NUM_OF_PAGE = 20;

    private int currentPage = 1;
    private String queryStr = null;
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public MainFragmentPresenter(RetrofitHelper mRetrofitHelper) {
        mCompositeSubscription = new CompositeSubscription();
        this.mRetrofitHelper = mRetrofitHelper;
        registerEvent();
    }

    void registerEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(SearchEvent.class)
                .compose(RxUtil.<SearchEvent>rxSchedulerHelper())
                .filter(new Func1<SearchEvent, Boolean>() {
                    @Override
                    public Boolean call(SearchEvent searchEvent) {
                        return searchEvent.getType() == Constants.TYPE_WECHAT;
                    }
                })
                .map(new Func1<SearchEvent, String>() {
                    @Override
                    public String call(SearchEvent searchEvent) {
                        return searchEvent.getQuery();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        queryStr = s;
                        getSearchWechatData(s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.setEmpty(true);
                        mView.setToastMsg("加载失败!");
                    }
                });
        addSubscrebe(rxSubscription);
    }

    //搜索条件
    private void getSearchWechatData(String queryString) {
        Subscription rxSubscription = (Subscription) mRetrofitHelper.fetchWechatListInfo(NUM_OF_PAGE, currentPage)
                .compose(RxUtil.<WxResPonse<List<WXItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<WXItemBean>>handleWXResult())
                .subscribe(new Action1<List<WXItemBean>>() {
                    @Override
                    public void call(List<WXItemBean> wxItemBeen) {
                        mView.showData(wxItemBeen);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.setEmpty(true);
                        mView.setToastMsg("加载数据失败!");
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getWechatData() {
        currentPage = 1;
        Subscription subscription = mRetrofitHelper.fetchWechatListInfo(NUM_OF_PAGE, currentPage)
                .compose(RxUtil.<WxResPonse<List<WXItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<WXItemBean>>handleWXResult())
                .subscribe(new Action1<List<WXItemBean>>() {
                    @Override
                    public void call(List<WXItemBean> wxItemBeen) {
                        mView.showData(wxItemBeen);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.setEmpty(true);
                        mView.setToastMsg("数据加载失败!" + throwable.getMessage());
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void getMoreWechatData() {
        Observable<WxResPonse<List<WXItemBean>>> observable;
        if (queryStr != null) {
            observable = mRetrofitHelper.fetchWechatSearchListInfo(NUM_OF_PAGE, ++currentPage, queryStr);
        } else {
            observable = mRetrofitHelper.fetchWechatListInfo(NUM_OF_PAGE, ++currentPage);
        }
        Subscription rxSubscription = observable
                .compose(RxUtil.<WxResPonse<List<WXItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<WXItemBean>>handleWXResult())
                .subscribe(new Action1<List<WXItemBean>>() {
                    @Override
                    public void call(List<WXItemBean> wxItemBeen) {
                        mView.showData(wxItemBeen);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.setEmpty(true);
                        mView.setToastMsg("数据加载失败!");
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
