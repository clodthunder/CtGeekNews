package gt.lskj.com.geeknew.ui.module.navigation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import gt.lskj.com.geeknew.R;
import gt.lskj.com.geeknew.ui.module.base.BaseActivity;
import gt.lskj.com.geeknew.ui.module.base.BaseView;
import gt.lskj.com.geeknew.ui.module.navigation.persenter.contract.NavigationContract;


/**
 * Created by Home on 16/9/19.
 */
public class NavigationActivity extends BaseActivity<NavigationContract.Presenter> implements BaseView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_navigation;
    }
}
