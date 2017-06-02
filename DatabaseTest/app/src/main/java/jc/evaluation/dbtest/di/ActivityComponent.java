package jc.evaluation.dbtest.di;

import javax.inject.Singleton;

import dagger.Component;
import jc.evaluation.dbtest.MainActivity;

@Singleton
@Component(modules = { ApplicationModule.class, PersistenceModule.class })
public interface ActivityComponent {

    void inject(MainActivity screen);

}
