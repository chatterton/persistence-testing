package jc.testing.databasetest2.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jc.testing.databasetest2.App;

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