package jc.evaluation.dbtest.persistence.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface ClickDao {

    @Insert
    void insertAll(ClickEntity... clicks);

}
