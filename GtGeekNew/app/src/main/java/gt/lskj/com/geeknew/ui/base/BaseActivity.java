package gt.lskj.com.geeknew.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import gt.lskj.com.geeknew.app.App;
import gt.lskj.com.geeknew.dragg2.component.ActivityComponent;
import gt.lskj.com.geeknew.dragg2.component.DaggerActivityComponent;
import gt.lskj.com.geeknew.dragg2.module.ActivityModule;
import gt.lskj.com.geeknew.utils.ActivityStackUtil;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Home on 16/9/19.
 */
public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements BaseView {

    @Inject
    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
        ActivityStackUtil.getmActivityStack().add(this);
        initToolBar();
        initEventAndData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        mUnBinder.unbind();
        ActivityStackUtil.getmActivityStack().remove(this);
    }

    @Override
    public void useNightMode(boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    protected abstract void initToolBar();

    protected abstract void initInject();

    protected abstract int getLayout();

    protected abstract void initEventAndData();
}
