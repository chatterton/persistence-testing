package jc.testing.databasetest2.persistence.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = { RoomLegislatorEntity.class }, version = 1)
@TypeConverters({RoomTypeConverters.class})
public abstract class TestRoomDatabase extends RoomDatabase {

    public abstract LegislatorDao legislatorDao();

}
