package jc.evaluation.dbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

import javax.inject.Inject;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import jc.evaluation.dbtest.persistence.User;
import jc.evaluation.dbtest.persistence.realm.RealmUserEntity;
import jc.evaluation.dbtest.persistence.realm.RealmUserService;
import jc.evaluation.dbtest.persistence.room.UserDao;
import jc.evaluation.dbtest.persistence.room.UserEntity;

public class MainActivity extends AppCompatActivity {

    @Inject UserDao userDao;
    @Inject RealmUserService userService;

    private LinearLayout userList;
    private EditText firstName;
    private EditText lastName;
    private Button toggleButton;

    private RealmResults<RealmUserEntity> userListResults;

    private PersistenceMode mode = PersistenceMode.REALM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App)getApplication()).getActivityComponent().inject(this);
        Log.i("_JC", "here's the userdao: "+userDao);
        Log.i("_JC", "here's the userservice: "+userService);

        userList = (LinearLayout) findViewById(R.id.main_userlist);
        firstName = (EditText)findViewById(R.id.main_firstname);
        lastName = (EditText)findViewById(R.id.main_lastname);
        toggleButton = (Button)findViewById(R.id.main_toggle);

        updateToggle();

        userListResults = userService.getAll();
        userListResults.addChangeListener(new RealmChangeListener<RealmResults<RealmUserEntity>>() {
            @Override
            public void onChange(RealmResults<RealmUserEntity> realmUserEntities) {
                updateList();
            }
        });
        updateList();
    }

    public void addButtonClicked(View view) {
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        if (!first.isEmpty() && !last.isEmpty()) {
            switch (mode) {
                case REALM:
                    userService.createUser(first, last);
                    break;
                case ROOM:
                    final UserEntity newUser = new UserEntity();
                    newUser.firstName = first;
                    newUser.lastName = last;
                    new Thread(new Runnable() {
                        public void run() {
                            userDao.insertAll(newUser);
                            updateList();
                        }
                    }).start();
                    break;
            }
            firstName.setText("");
            lastName.setText("");
            firstName.requestFocus();
        } else {
            Log.e("_JC", "name is fully or partially blank");
        }
    }

    private void updateList() {
        switch (mode) {
            case REALM:
                updateListRealm();
                break;
            case ROOM:
                updateListRoom();
                break;
        }
    }

    private void appendToList(User user) {
        Button button = new Button(this);
        button.setTag(user);
        button.setText(user.getName()+": "+user.getClicks());
        userList.addView(button);
    }

    private void updateListRealm() {
        userList.removeAllViews();
        for (RealmUserEntity user : userListResults) {
            appendToList(user);
        }
    }

    private void updateListRoom() {
        new Thread(new Runnable() {
            public void run() {
                final List<UserEntity> users = userDao.getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userList.removeAllViews();
                        for (User user : users) {
                            appendToList(user);
                        }
                    }
                });
            }
        }).start();
    }

    public void toggleButtonClicked(View view) {
        switch (mode) {
            case REALM:
                mode = PersistenceMode.ROOM;
                break;
            case ROOM:
                mode = PersistenceMode.REALM;
                break;
        }
        updateList();
        updateToggle();
    }

    private void updateToggle() {
        toggleButton.setText("Persistence mode: "+mode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userListResults.removeAllChangeListeners();
        userService.finish();
    }

    private enum PersistenceMode {
        REALM,
        ROOM
    }

}
