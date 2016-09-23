package gt.lskj.com.geeknew.ui.module.main.presenter;

import javax.inject.Inject;

import gt.lskj.com.geeknew.ui.base.RxPresenter;
import gt.lskj.com.geeknew.ui.module.main.contract.MainContract;

/**
 * Created by Home on 16/9/21.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Persenter {

    @Inject
    public MainPresenter() {
    }
}
