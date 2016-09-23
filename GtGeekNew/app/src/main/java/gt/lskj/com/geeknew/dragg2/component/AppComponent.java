package gt.lskj.com.geeknew.dragg2.component;

import javax.inject.Singleton;

import dagger.Component;
import gt.lskj.com.geeknew.app.App;
import gt.lskj.com.geeknew.dragg2.ContextLife;
import gt.lskj.com.geeknew.dragg2.module.AppModule;
import gt.lskj.com.geeknew.https.retrofit2.RetrofitHelper;

/**
 * Created by Home on 16/9/21.
 */


@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    @ContextLife("Application")
    App getContext();  // 提供App的Context
    RetrofitHelper retrofitHelper();  //提供http的帮助类
}
