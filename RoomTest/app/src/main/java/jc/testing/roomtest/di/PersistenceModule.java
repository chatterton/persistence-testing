package jc.testing.roomtest.di;

import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jc.testing.roomtest.App;
import jc.testing.roomtest.persistence.RoomTestDatabase;
import jc.testing.roomtest.persistence.UserDao;

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
