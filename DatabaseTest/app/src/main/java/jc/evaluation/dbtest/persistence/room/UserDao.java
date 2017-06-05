package jc.evaluation.dbtest.persistence.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import jc.evaluation.dbtest.persistence.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users ORDER BY last_name")
    List<UserEntity> getAll();

    @Query("SELECT * FROM users WHERE uid IN (:userIds)")
    List<UserEntity> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM users WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    UserEntity findByName(String first, String last);

    @Query("SELECT users.uid, users.first_name, users.last_name, clk.cnt AS 'clickCount' " +
            "FROM users LEFT JOIN (SELECT parent_id, count(1) cnt FROM clicks GROUP BY parent_id) clk " +
            "ON users.uid = clk.parent_id ORDER BY last_name")
    List<UserWithClicks> getAllWithClicks();

    @Insert
    void insertAll(UserEntity... users);

    @Delete
    void delete(UserEntity user);

    class UserWithClicks extends UserEntity {

        public int clickCount;

        @Override
        public int getClicks() {
            return clickCount;
        }

    }

}