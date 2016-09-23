package gt.lskj.com.geeknew.dragg2.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import gt.lskj.com.geeknew.dragg2.ActivityScope;

/**
 * Created by Home on 16/9/21.
 */


@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}