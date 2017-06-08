package jc.testing.databasetest2.di;

import javax.inject.Singleton;

import dagger.Component;
import jc.testing.databasetest2.App;

@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {

    void inject(App application);

}
