package jc.testing.roomtest;

import android.app.Application;

import jc.testing.roomtest.di.ActivityComponent;
import jc.testing.roomtest.di.ApplicationComponent;
import jc.testing.roomtest.di.ApplicationModule;
import jc.testing.roomtest.di.DaggerActivityComponent;
import jc.testing.roomtest.di.DaggerApplicationComponent;

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
    }


    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

}
