package gt.lskj.com.geeknew.ui.module.main.contract;

import java.util.List;

import gt.lskj.com.geeknew.https.beans.WXItemBean;
import gt.lskj.com.geeknew.ui.base.BasePresenter;
import gt.lskj.com.geeknew.ui.base.BaseView;

/**
 * Created by Home on 16/9/20.
 */

public interface MainFragmentContract {

    interface View extends BaseView {
        void setToastMsg(String msg);

        //显示数据
        void showData(List<WXItemBean> data);

        //显示空的view
        void setEmpty(boolean isShow);

    }

    interface Presenter extends BasePresenter<MainFragmentContract.View> {

        void getWechatData();

        void getMoreWechatData();
    }
}
