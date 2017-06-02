package jc.evaluation.dbtest;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import jc.evaluation.dbtest.di.ActivityComponent;
import jc.evaluation.dbtest.di.ApplicationComponent;
import jc.evaluation.dbtest.di.ApplicationModule;
import jc.evaluation.dbtest.di.DaggerActivityComponent;
import jc.evaluation.dbtest.di.DaggerApplicationComponent;
import jc.evaluation.dbtest.persistence.realm.AppRealmMigration;

public class App extends Application {

    private ActivityComponent activityComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);

        activityComponent = DaggerActivityComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1) // increment this on schema update
                .migration(new AppRealmMigration())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }


    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

}
