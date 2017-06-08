package jc.testing.databasetest2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jc.testing.databasetest2.persistence.LegislatorStore;

public class MainViewModel extends AndroidViewModel {

    @Inject Loader loader;
    @Inject LegislatorStore legislatorStore;

    public MainViewModel(Application app) {
        super(app);
        ((App)app).getViewModelComponent().inject(this);
    }

    public void reloadSelected() {
        System.out.println("STARTED: "+System.currentTimeMillis());
        legislatorStore.deleteAll2()
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object ignore) throws Exception {
                        loader.loadData()
                                .doFinally(new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        System.out.println("FINISHED: "+System.currentTimeMillis());
                                    }
                                })
                                .subscribeOn(Schedulers.computation())
                                .subscribe(legislatorStore.legislatorPersister());
                    }
                });
    }

}
