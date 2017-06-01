package jc.testing.roomtest;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import jc.testing.roomtest.persistence.RoomTestDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoomTestDatabase db = Room.databaseBuilder(getApplicationContext(), RoomTestDatabase.class, "room-test-database").build();

        Log.i("JC", "here's the database: "+db);

    }
}
