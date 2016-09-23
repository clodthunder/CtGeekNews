package gt.lskj.com.geeknew.dragg2.component;

import android.app.Activity;

import dagger.Component;
import gt.lskj.com.geeknew.dragg2.ActivityScope;
import gt.lskj.com.geeknew.dragg2.module.ActivityModule;
import gt.lskj.com.geeknew.ui.module.main.activity.MainActivity;

/**
 * Created by Home on 16/9/21.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(MainActivity mainActivity);
}
