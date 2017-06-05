package jc.evaluation.dbtest.persistence.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

public class RoomMigrations {

    static final int INITIAL_DATABASE = 1;
    static final int ADD_CLICKS_TABLE = 2;

    public static final Migration MIGRATION_1_2 = new Migration(INITIAL_DATABASE, ADD_CLICKS_TABLE) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                "CREATE TABLE clicks ('id' INTEGER, 'timestamp' INTEGER NOT NULL, 'parent_id' INTEGER, " +
                        " PRIMARY KEY('id')" +
                        " FOREIGN KEY('parent_id') REFERENCES users(uid) ON DELETE CASCADE)"
            );
        }
    };

}
