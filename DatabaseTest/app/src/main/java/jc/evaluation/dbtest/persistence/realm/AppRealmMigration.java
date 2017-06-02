package jc.evaluation.dbtest.persistence.realm;

import java.util.Date;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class AppRealmMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        long old = oldVersion;
        RealmSchema schema = realm.getSchema();

        // Add clicks to model
        if (old == 0) {
            RealmObjectSchema clickSchema = schema.create("RealmClickEntity")
                    .addField("timestamp", Date.class, FieldAttribute.REQUIRED);

            schema.get("RealmUserEntity")
                    .addRealmListField("clicks", clickSchema);

            old++;
        }


    }
}
