package jc.testing.databasetest2;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
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


        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1) // increment this on schema update
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public ViewModelComponent getViewModelComponent() {
        return viewModelComponent;
    }

}
