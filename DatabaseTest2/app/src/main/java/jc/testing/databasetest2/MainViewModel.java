package jc.testing.databasetest2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jc.testing.databasetest2.model.Legislator;
import jc.testing.databasetest2.persistence.LegislatorStore;

public class MainViewModel extends AndroidViewModel {

    @Inject Loader loader;
    @Inject LegislatorStore legislatorStore;

    private MutableLiveData<List<Legislator>> mutableLegislatorList = new MutableLiveData<>();

    public MainViewModel(Application app) {
        super(app);
        ((App)app).getViewModelComponent().inject(this);
    }

    public LiveData<List<Legislator>> getLegislatorList() {
        return mutableLegislatorList;
    }

    public void reloadSelected() {
        final long startTime = System.currentTimeMillis();
        legislatorStore.deleteAll()
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object ignore) throws Exception {
                        mutableLegislatorList.postValue(new ArrayList<Legislator>());
                        System.out.println("INTERIM: "+(System.currentTimeMillis() - startTime));
                        loader.loadData()
                                .doFinally(new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        System.out.println("FINISHED: "+(System.currentTimeMillis() - startTime));
                                        refreshLegislatorList();
                                    }
                                })
                                .subscribeOn(Schedulers.computation())
                                .toList()
                                .subscribe(legislatorStore.legislatorPersister());
                    }
                });
    }

    private void refreshLegislatorList() {
        legislatorStore.listAll()
                .subscribe(new Consumer<List<Legislator>>() {
                    @Override
                    public void accept(@NonNull List<Legislator> legislators) throws Exception {
                        mutableLegislatorList.postValue(legislators);
                    }
                });
    }

}
