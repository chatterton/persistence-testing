package jc.testing.roomtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import jc.testing.roomtest.persistence.UserEntity;
import jc.testing.roomtest.persistence.UserDao;

public class MainActivity extends AppCompatActivity {

    @Inject UserDao userDao;

    private EditText firstName;
    private EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App)getApplication()).getActivityComponent().inject(this);
        Log.i("_JC", "here's the userdao: "+userDao);

        firstName = (EditText)findViewById(R.id.main_firstname);
        lastName = (EditText)findViewById(R.id.main_lastname);

    }

    public void addButtonClicked(View view) {
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        if (!first.isEmpty() && !last.isEmpty()) {
            final UserEntity newUser = new UserEntity();
            newUser.firstName = first;
            newUser.lastName = last;
            new Thread(new Runnable() {
                public void run() {
                    userDao.insertAll(newUser);
                }
            }).start();
            firstName.setText("");
            lastName.setText("");
            firstName.requestFocus();
        } else {
            Log.e("_JC", "name is fully or partially blank");
        }
    }

}
