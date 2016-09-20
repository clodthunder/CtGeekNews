package gt.lskj.com.geeknew.ui.module.base;

/**
 * Created by codeest on 2016/8/2.
 * Presenter基类
 */
public interface BasePresenter<T extends BaseView> {
    //banding view
    void attachView(T view);

    //unbanding
    void detachView();
}
