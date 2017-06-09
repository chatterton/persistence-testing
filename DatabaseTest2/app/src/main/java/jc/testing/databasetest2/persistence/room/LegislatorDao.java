package jc.testing.databasetest2.persistence.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface LegislatorDao {

    @Query("SELECT * FROM legislators WHERE name LIKE :name")
    Flowable<List<RoomLegislatorEntity>> getAll(String name);

    @Insert
    void insert(RoomLegislatorEntity... legislators);

    @Query("DELETE FROM legislators")
    void deleteAll();

}
