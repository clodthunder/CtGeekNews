package gt.lskj.com.geeknew.dragg2.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gt.lskj.com.geeknew.app.App;
import gt.lskj.com.geeknew.dragg2.ContextLife;
import gt.lskj.com.geeknew.https.retrofit2.RetrofitHelper;

/**
 * Created by Home on 16/9/21.
 */

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    App provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    RetrofitHelper provideRetrofitHelper() {
        return new RetrofitHelper();
    }
}