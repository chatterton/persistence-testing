package jc.testing.databasetest2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jc.testing.databasetest2.model.Legislator;

public class MainViewModel extends AndroidViewModel {

    public MainViewModel(Application app) {
        super(app);
    }

    public void reloadSelected() {
        Loader l = new Loader(this.getApplication());
        l.loadData()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Legislator>() {
                    @Override
                    public void accept(@NonNull Legislator legislator) throws Exception {
                        System.out.println(legislator);
                    }
                });
    }

}
