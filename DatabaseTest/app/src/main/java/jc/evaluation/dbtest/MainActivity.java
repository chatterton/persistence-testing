package jc.evaluation.dbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import jc.evaluation.dbtest.persistence.realm.RealmUserEntity;
import jc.evaluation.dbtest.persistence.realm.RealmUserService;
import jc.evaluation.dbtest.persistence.room.UserDao;

public class MainActivity extends AppCompatActivity {

    @Inject UserDao userDao;
    @Inject RealmUserService userService;

    private TextView userList;
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

        userList = (TextView)findViewById(R.id.main_userlist);
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
            userService.createUser(first, last);
            firstName.setText("");
            lastName.setText("");
            firstName.requestFocus();
        } else {
            Log.e("_JC", "name is fully or partially blank");
        }
    }

    private void updateList() {
        final StringBuilder sb = new StringBuilder();
        for (RealmUserEntity user : userListResults) {
            sb.append(user.firstName);
            sb.append(" ");
            sb.append(user.lastName);
            sb.append("\n");
        }
        userList.setText(sb.toString());
    }

    public void toggleButtonClicked(View view) {
        switch(mode) {
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
