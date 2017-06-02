package jc.evaluation.dbtest.di;

import javax.inject.Singleton;

import dagger.Component;
import jc.evaluation.dbtest.App;

@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {

    void inject(App application);

}
