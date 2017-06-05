package jc.evaluation.dbtest.persistence.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = { UserEntity.class, ClickEntity.class }, version = 2)
@TypeConverters({RoomTypeConverters.class})
public abstract class RoomTestDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ClickDao clickDao();
}
