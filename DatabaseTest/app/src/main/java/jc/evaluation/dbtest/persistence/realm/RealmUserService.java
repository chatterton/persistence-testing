package jc.evaluation.dbtest.persistence.realm;

import java.util.UUID;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmUserService {

    @Inject Realm realm;

    @Inject
    public RealmUserService() { }

    public RealmUserEntity createUser(String firstName, String lastName) {
        realm.beginTransaction();
        RealmUserEntity user = realm.createObject(RealmUserEntity.class, UUID.randomUUID().toString());
        user.firstName = firstName;
        user.lastName = lastName;
        realm.commitTransaction();
        return user;
    }

    public RealmResults<RealmUserEntity> getAll() {
        return realm.where(RealmUserEntity.class)
                .findAllSortedAsync("lastName");
    }

    public void finish() {
        realm.close();
    }

}
