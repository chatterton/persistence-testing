package jc.testing.databasetest2.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jc.testing.databasetest2.App;
import jc.testing.databasetest2.Loader;

@Module
public class StorageModule {

    @Singleton
    @Provides
    public Loader provideLoader(App app) {
        Loader loader = new Loader(app);
        return loader;
    }

}
