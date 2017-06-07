package jc.testing.databasetest2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadButtonClicked(View view) {
        Yaml yaml = new Yaml();
        List<Map<String, Map<String, String>>> list = null;
        try {

            list = (List<Map<String, Map<String, String>>>) yaml.load(getAssets().open("legislators-current.yaml"));
        } catch (Exception e) {
            Log.e("_JC", e.getMessage());
        }
        if (null != list) {
            System.out.println(list.get(0));
        }
    }


}
