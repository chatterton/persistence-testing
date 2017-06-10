package jc.testing.databasetest2;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import jc.testing.databasetest2.model.Legislator;

public class MainActivity extends LifecycleActivity {

    private MainViewModel viewModel;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final LegislatorAdapter adapter = new LegislatorAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getLegislatorList().observe(this, new Observer<List<Legislator>>() {
            @Override
            public void onChanged(@Nullable List<Legislator> legislators) {
                System.out.println("LIST LENGTH: "+legislators.size());
                adapter.dataset = legislators;
                adapter.notifyDataSetChanged();
            }
        });

        final Button switchButton = (Button) findViewById(R.id.switch_button);
        switchButton.setText(viewModel.getMode().getValue());
        viewModel.getMode().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                switchButton.setText(s);
            }
        });

        EditText editText = (EditText) findViewById(R.id.searchtext);
        RxTextView.textChanges(editText)
                .debounce(200, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(@NonNull CharSequence s) throws Exception {
                        viewModel.setSearchString(s.toString());
                    }
                });
    }

    public void loadButtonClicked(View view) {
        viewModel.reloadSelected();
    }

    public void switchButtonClicked(View view) {
        viewModel.switchSelected();
    }

    private class LegislatorAdapter extends RecyclerView.Adapter<LegislatorAdapter.ViewHolder> {

        public List<Legislator> dataset = new ArrayList<>();

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;
            public ViewHolder(TextView tv) {
                super(tv);
                this.textView = tv;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_textview, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Legislator l = dataset.get(position);
            holder.textView.setText(l.name() + " -- " + l.party());
            switch (l.party()) {
                case REPUBLICAN:
                    holder.textView.setBackgroundColor(Color.argb(255, 255, 200, 200));
                    break;
                case DEMOCRAT:
                    holder.textView.setBackgroundColor(Color.argb(255, 200, 200, 255));
                    break;
                default:
                    holder.textView.setBackgroundColor(Color.argb(255, 225, 225, 225));
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return dataset.size();
        }

    }

}
