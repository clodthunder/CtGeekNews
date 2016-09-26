package gt.lskj.com.geeknew.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import gt.lskj.com.geeknew.utils.ActivityStackUtil;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Home on 16/9/22.
 */

public abstract class BaseNActivity extends SupportActivity {
    private Unbinder mUnBinder;
    protected Activity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        ActivityStackUtil.getmActivityStack().add(this);
        initToolBar();
        initEventAndData();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        ActivityStackUtil.getmActivityStack().remove(this);
    }

    protected abstract void initToolBar();

    protected abstract int getLayout();

    protected abstract void initEventAndData();
}
