package jc.testing.roomtest.di;

import javax.inject.Singleton;

import dagger.Component;
import jc.testing.roomtest.App;

@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {

    void inject(App application);

}
