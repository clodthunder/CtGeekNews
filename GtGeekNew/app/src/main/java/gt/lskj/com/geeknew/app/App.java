package gt.lskj.com.geeknew.app;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.squareup.leakcanary.LeakCanary;

import gt.lskj.com.geeknew.BuildConfig;
import gt.lskj.com.geeknew.dragg2.component.AppComponent;
import gt.lskj.com.geeknew.dragg2.component.DaggerAppComponent;
import gt.lskj.com.geeknew.dragg2.module.AppModule;
import gt.lskj.com.geeknew.utils.ActivityStackUtil;
import timber.log.Timber;

/**
 * Created by Home on 16/9/18.
 */
public class App extends Application {
    private static App instance;
    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    public static synchronized App getInstance() {
        return instance;
    }

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initTimer();
        Timber.tag("App");
        instance = this;
        //初始化屏幕宽高
        getScreenSize();
        //初始化错误收集
//        CrashHandler.init(new CrashHandler(getApplicationContext()));
        //初始化内存泄漏检测
        LeakCanary.install(this);
        Timber.d("LeakCanary init");
    }

    public void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
        Timber.d("-->getScreenSize");
    }

    //退出
    public void exitApp() {
        ActivityStackUtil.finishAllActivity();
    }


    /**
     * 初始化日志
     */
    private void initTimer() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }


    //dragg2
    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }
}
