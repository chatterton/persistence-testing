package jc.testing.databasetest2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jc.testing.databasetest2.model.Legislator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadButtonClicked(View view) {
        Loader l = new Loader(this);
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
