package gt.lskj.com.geeknew.ui.module.main.presenter.contract;


import gt.lskj.com.geeknew.ui.module.base.BasePresenter;
import gt.lskj.com.geeknew.ui.module.base.BaseView;

/**
 * Created by Home on 16/9/19.
 * 建立view 和 presenter 的联系
 */
public interface MainContract {

    interface View extends BaseView {
        //显示加载
        void setLoadingIndicator(boolean active);

        void setButtomBar();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
