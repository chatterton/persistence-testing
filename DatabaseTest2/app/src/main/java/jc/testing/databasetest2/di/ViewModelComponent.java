package jc.testing.databasetest2.di;

import javax.inject.Singleton;

import dagger.Component;
import jc.testing.databasetest2.MainViewModel;

@Singleton
@Component(modules = { ApplicationModule.class, StorageModule.class })
public interface ViewModelComponent {

    void inject(MainViewModel vm);

}
