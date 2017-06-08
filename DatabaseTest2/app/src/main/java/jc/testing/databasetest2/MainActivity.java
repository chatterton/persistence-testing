package jc.testing.databasetest2;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import jc.testing.databasetest2.model.Legislator;

public class MainActivity extends LifecycleActivity {

    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getLegislatorList().observe(this, new Observer<List<Legislator>>() {
            @Override
            public void onChanged(@Nullable List<Legislator> legislators) {
                System.out.println("LIST LENGTH: "+legislators.size());
            }
        });

    }

    public void loadButtonClicked(View view) {
        viewModel.reloadSelected();
    }

}
