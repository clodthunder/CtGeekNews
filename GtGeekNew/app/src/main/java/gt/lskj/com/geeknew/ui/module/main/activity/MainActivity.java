package gt.lskj.com.geeknew.ui.module.main.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lskj.ct.geeknew.R;
import com.lskj.ct.geeknew.app.Constants;
import com.lskj.ct.geeknew.ui.module.base.BaseActivity;
import com.lskj.ct.geeknew.ui.module.main.presenter.MainPresenter;
import com.lskj.ct.geeknew.ui.module.main.presenter.contract.MainContract;
import com.lskj.ct.geeknew.utils.ActivityStackUtil;
import com.lskj.ct.geeknew.utils.SharePreferenceUtil;
import com.lskj.ct.geeknew.utils.ToastUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import timber.log.Timber;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.tb_main)
    Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    //左侧导航
    @BindView(R.id.nv_main)
    NavigationView mNavigationView;
    MenuItem mLastMenuItem;
    private int defalut = R.id.nav_main_one;
    private FragmentManager mFragmentManager;
    private long clickTime = 0; //记录第一次点击的时间
    private MainPresenter mMainPresenter;
    private final String[] RW_PERMISSION =
            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Timber.tag("MainActivity");
        mToolbar.setNavigationIcon(R.drawable.ic_reorder_white_24dp);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_nav_content);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timber.d("setNavigationOnClickListener");
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        if (mLastMenuItem == null) {
            //判断是否具有权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                RxPermissions.getInstance(this).request(RW_PERMISSION).subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("RW_Permission onCompleted!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            //get default  menuitem from sp
                            defalut = SharePreferenceUtil.getAppSp().getInt(Constants.SP_CURRENT_ITEM, R.id.nav_main_one);
                            if (defalut != R.id.nav_main_one) {
                                Timber.d("get SP_CURRENT_ITEM from sp!");
                            } else {
                                Timber.d("get SP_CURRENT_ITEM default!");
                            }
                        }
                    }
                });
            } else {
                defalut = SharePreferenceUtil.getAppSp().getInt(Constants.SP_CURRENT_ITEM, R.id.nav_main_one);
                if (defalut != R.id.nav_main_one) {
                    Timber.d("get SP_CURRENT_ITEM from sp!");
                } else {
                    Timber.d("get SP_CURRENT_ITEM default!");
                }
            }
        }
        mLastMenuItem = mNavigationView.getMenu().findItem(defalut);
        mLastMenuItem.setChecked(true);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ToastUtil.shortShow(item.getTitle().toString());
                //修改选中状态
                if (mLastMenuItem != null) {
                    mLastMenuItem.setChecked(false);
                }
                mLastMenuItem = item;
                item.setChecked(true);
                //关闭
                mDrawerLayout.closeDrawers();
                //存储数据到sharepreference
                boolean isCommite = SharePreferenceUtil.getAppSp().edit().putInt(Constants.SP_CURRENT_ITEM, item.getItemId()).commit();
                if (isCommite) {
                    Timber.e("Save SP_CURRENT_ITEM complete!");
                } else {
                    Timber.e("Save SP_CURRENT_ITEM failed!");
                }
                return false;
            }
        });
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    //处理双击退出事件
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - clickTime > 2000) {
            clickTime = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "再次点击退出", Toast.LENGTH_SHORT).show();
        } else {
            ActivityStackUtil.finishAllActivity();
        }
    }


    @Override
    public void setLoadingIndicator(boolean active) {

    }

}
