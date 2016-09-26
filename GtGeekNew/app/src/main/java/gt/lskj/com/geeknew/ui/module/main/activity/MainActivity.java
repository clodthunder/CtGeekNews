package gt.lskj.com.geeknew.ui.module.main.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.smtt.sdk.TbsVideo;

import butterknife.BindView;
import butterknife.ButterKnife;
import gt.lskj.com.geeknew.R;
import gt.lskj.com.geeknew.app.Constants;
import gt.lskj.com.geeknew.ui.base.BaseActivity;
import gt.lskj.com.geeknew.ui.module.main.contract.MainContract;
import gt.lskj.com.geeknew.ui.module.main.fragment.FavoritesFragment;
import gt.lskj.com.geeknew.ui.module.main.fragment.FriendsFragment;
import gt.lskj.com.geeknew.ui.module.main.fragment.MainFragment;
import gt.lskj.com.geeknew.ui.module.main.fragment.NearByFragment;
import gt.lskj.com.geeknew.ui.module.main.fragment.UserFragment;
import gt.lskj.com.geeknew.ui.module.main.presenter.MainPresenter;
import gt.lskj.com.geeknew.utils.ActivityStackUtil;
import gt.lskj.com.geeknew.utils.ActivityUtils;
import gt.lskj.com.geeknew.utils.SharePreferenceUtil;
import rx.Subscriber;
import timber.log.Timber;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.tb_main)
    Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    //左侧导航
    @BindView(R.id.nv_main)
    NavigationView mNavigationView;
    @BindView(R.id.bb_main)
    BottomBar mBottomBar;
    //默认选中的menuitem
    private MenuItem mLastMenuItem;
    private int defalut = R.id.nav_main_one;
    private long clickTime = 0; //记录第一次点击的时间
    private final String[] RW_PERMISSION =
            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE};
    private FragmentManager mFragmentManager;
    private MainFragment mMainFragment;
    private FriendsFragment mFriendsFragment;
    private NearByFragment mNearByFragment;
    private FavoritesFragment mFavoritesFragment;
    private UserFragment mUserFragment;

    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Timber.tag("MainActivity");
//        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());
        //fragmentManager
        mFragmentManager = getSupportFragmentManager();
        if (mMainFragment == null) {
            mMainFragment = MainFragment.newInstance();
        }
        ActivityUtils.addFragmentToActivity(mFragmentManager, mMainFragment, R.id.fl_main_content);
    }

    //dragg activity
    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        if (mLastMenuItem != null) {
            mLastMenuItem.setChecked(true);
        }
        if (mNavigationView != null) {
            setDrawerContent(mNavigationView);
        }
        if (mBottomBar != null) {
            setBottomBar(mBottomBar);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolBar() {
        mToolbar.setNavigationIcon(R.drawable.ic_reorder_white_24dp);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_nav_content);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timber.d("setNavigationOnClickListener");
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    //处理双击退出事件
    @Override
    public void onBackPressedSupport() {
        if (System.currentTimeMillis() - clickTime > 2000) {
            clickTime = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "再次点击退出", Toast.LENGTH_SHORT).show();
        } else {
            ActivityStackUtil.finishAllActivity();
        }
    }

    //设置左侧layout
    private void setDrawerContent(NavigationView navigationView) {
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
        //设置监听item 点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_main_one:
                        //Tbs播放器是否已经可以使用。
                        if (TbsVideo.canUseTbsPlayer(mContext)) {
                            TbsVideo.openVideo(mContext, "");
                            //  public static void openVideo(Context context, String videoUrl, Bundle extraData)
                            //  extraData对象是根据定制需要传入约定的信息，没有需要可以传如null
                        }
                        break;
                    case R.id.nav_main_two:

                        break;
                    case R.id.nav_main_Four:

                        break;
                    case R.id.nav_main_Five:

                        break;
                    case R.id.nav_main_Six:

                        break;
                }
                //修改选中状态
                if (mLastMenuItem != null) {
                    mLastMenuItem.setChecked(false);
                }
                //存储数据到sharepreference
                boolean isCommite = SharePreferenceUtil.getAppSp()
                        .edit().putInt(Constants.SP_CURRENT_ITEM, item.getItemId()).commit();
                if (isCommite) {
                    Timber.e("Save SP_CURRENT_ITEM complete!");
                } else {
                    Timber.e("Save SP_CURRENT_ITEM failed!");
                }

                mLastMenuItem = item;
                // Close the navigation drawer when an item is selected.
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    //设置底部的Bar
    private void setBottomBar(BottomBar bottombar) {
        bottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_main_home:
                        if (mMainFragment != null) {
                            mMainFragment = MainFragment.newInstance();
                            ActivityUtils.addFragmentToActivity(mFragmentManager, mMainFragment, R.id.fl_main_content);

                        }
                        break;
                    case R.id.tab_main_Favorites:
                        if (mFavoritesFragment == null) {
                            mFavoritesFragment = new FavoritesFragment();
                            ActivityUtils.addFragmentToActivity(mFragmentManager, mFavoritesFragment, R.id.fl_main_content);
                        }
                        break;
                    case R.id.tab_main_friends:
                        if (mFriendsFragment == null) {
                            mFriendsFragment = new FriendsFragment();
                            ActivityUtils.addFragmentToActivity(mFragmentManager, mFriendsFragment, R.id.fl_main_content);
                        }
                        break;
                    case R.id.tab_main_nearby:
                        if (mNearByFragment == null) {
                            mNearByFragment = new NearByFragment();
                            ActivityUtils.addFragmentToActivity(mFragmentManager, mNearByFragment, R.id.fl_main_content);
                        }
                        break;
                    case R.id.tab_main_user:
                        if (mUserFragment == null) {
                            mUserFragment = new UserFragment();
                            ActivityUtils.addFragmentToActivity(mFragmentManager, mUserFragment, R.id.fl_main_content);
                        }
                        break;
                }
            }
        });
    }

}
