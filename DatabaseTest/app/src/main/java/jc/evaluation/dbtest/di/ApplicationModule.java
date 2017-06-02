package jc.evaluation.dbtest.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jc.evaluation.dbtest.App;

@Module
public class ApplicationModule {

    private final App application;

    public ApplicationModule(App a) {
        this.application = a;
    }

    @Provides
    @Singleton
    App provideApplication() {
        return application;
    }

}