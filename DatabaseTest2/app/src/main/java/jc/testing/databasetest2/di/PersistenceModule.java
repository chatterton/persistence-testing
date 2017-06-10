package jc.testing.databasetest2.di;

import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jc.testing.databasetest2.App;
import jc.testing.databasetest2.Loader;
import jc.testing.databasetest2.persistence.room.LegislatorDao;
import jc.testing.databasetest2.persistence.room.TestRoomDatabase;

@Module
public class PersistenceModule {

    private TestRoomDatabase testRoomDatabase;

    private void createDatabaseIfNecessary(App app) {
        if (null == testRoomDatabase) {
            testRoomDatabase = Room.databaseBuilder(app, TestRoomDatabase.class, "room-test-database")
                    .build();
        }
    }

    @Singleton
    @Provides
    public Loader provideLoader(App app) {
        return new Loader(app);
    }

    @Singleton
    @Provides
    public LegislatorDao legislatorDaoProvider(App app) {
        createDatabaseIfNecessary(app);
        return testRoomDatabase.legislatorDao();
    }

}
