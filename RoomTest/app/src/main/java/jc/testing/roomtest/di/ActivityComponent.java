package jc.testing.roomtest.di;

import javax.inject.Singleton;

import dagger.Component;
import jc.testing.roomtest.MainActivity;

@Singleton
@Component(modules = { ApplicationModule.class })
public interface ActivityComponent {

    void inject(MainActivity screen);

}
