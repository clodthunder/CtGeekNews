package gt.lskj.com.geeknew.dragg2.component;

import android.app.Activity;

import dagger.Component;
import gt.lskj.com.geeknew.dragg2.FragmentScope;
import gt.lskj.com.geeknew.dragg2.module.FragmentModule;
import gt.lskj.com.geeknew.ui.module.main.fragment.MainFragment;

/**
 * Created by Home on 16/9/21.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(MainFragment mainFragment);

}