package jc.testing.roomtest.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = { UserEntity.class }, version = 1)
public abstract class RoomTestDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
