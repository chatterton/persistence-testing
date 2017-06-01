package jc.testing.roomtest.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jc.testing.roomtest.App;

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