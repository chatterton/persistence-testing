package jc.testing.databasetest2;

import android.app.Application;

import jc.testing.databasetest2.di.ApplicationComponent;
import jc.testing.databasetest2.di.ApplicationModule;
import jc.testing.databasetest2.di.DaggerApplicationComponent;
import jc.testing.databasetest2.di.DaggerViewModelComponent;
import jc.testing.databasetest2.di.ViewModelComponent;

public class App extends Application {

    private ViewModelComponent viewModelComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);

        viewModelComponent = DaggerViewModelComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ViewModelComponent getViewModelComponent() {
        return viewModelComponent;
    }


}
