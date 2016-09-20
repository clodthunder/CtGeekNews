package gt.lskj.com.geeknew.ui.module.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import gt.lskj.com.geeknew.utils.ActivityStackUtil;

/**
 * Created by Home on 16/9/19.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnBinder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        //加入ActivityStack
        ActivityStackUtil.getmActivityStack().add(this);
        initEventAndData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        mUnBinder.unbind();
        //退出
        ActivityStackUtil.getmActivityStack().remove(this);
    }

    protected abstract void initEventAndData();

    protected abstract int getLayout();
}
