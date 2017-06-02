package jc.evaluation.dbtest.di;

import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jc.evaluation.dbtest.App;
import jc.evaluation.dbtest.persistence.room.RoomTestDatabase;
import jc.evaluation.dbtest.persistence.room.UserDao;

@Module
public class PersistenceModule {

    private RoomTestDatabase db;

    private void createDatabaseIfNecessary(App app) {
        if (null == db) {
            db = Room.databaseBuilder(app, RoomTestDatabase.class, "room-test-database").build();
        }
    }

    @Singleton
    @Provides
    UserDao userDaoProvider(App app) {
        createDatabaseIfNecessary(app);
        return db.userDao();
    }

}
