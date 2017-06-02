package jc.evaluation.dbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import jc.evaluation.dbtest.persistence.room.UserEntity;
import jc.evaluation.dbtest.persistence.room.UserDao;

public class MainActivity extends AppCompatActivity {

    @Inject UserDao userDao;

    private TextView userList;
    private EditText firstName;
    private EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App)getApplication()).getActivityComponent().inject(this);
        Log.i("_JC", "here's the userdao: "+userDao);

        userList = (TextView)findViewById(R.id.main_userlist);
        firstName = (EditText)findViewById(R.id.main_firstname);
        lastName = (EditText)findViewById(R.id.main_lastname);

        updateList();
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
                    updateList();
                }
            }).start();
            firstName.setText("");
            lastName.setText("");
            firstName.requestFocus();
        } else {
            Log.e("_JC", "name is fully or partially blank");
        }
    }

    private void updateList() {
        new Thread(new Runnable() {
            public void run() {
                List<UserEntity> users = userDao.getAll();
                final StringBuilder sb = new StringBuilder();
                for (UserEntity user : users) {
                    sb.append(user.firstName + " " + user.lastName + "\n");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userList.setText(sb.toString());
                    }
                });
            }
        }).start();
    }

}
