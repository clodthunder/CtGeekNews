package gt.lskj.com.geeknew.ui.base;

/**
 * Created by codeest on 2016/8/2.
 * Presenter基类
 */
public interface BasePresenter<T> {
    void attachView(T view);

    void detachView();
}
