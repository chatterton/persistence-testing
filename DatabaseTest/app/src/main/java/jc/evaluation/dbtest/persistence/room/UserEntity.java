package jc.evaluation.dbtest.persistence.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import jc.evaluation.dbtest.persistence.User;

@Entity(tableName = "users")
public class UserEntity implements User {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public int getClicks() {
        return -1;
    }

    @Override
    public int getId() {
        return uid;
    }
}
