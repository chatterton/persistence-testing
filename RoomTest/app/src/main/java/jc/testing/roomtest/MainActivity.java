package jc.testing.roomtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import jc.testing.roomtest.persistence.UserDao;

public class MainActivity extends AppCompatActivity {

    @Inject UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App)getApplication()).getActivityComponent().inject(this);
        Log.i("_JC", "here's the userdao: "+userDao);

    }
}
