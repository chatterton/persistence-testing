package jc.evaluation.dbtest.persistence.realm;

import java.util.UUID;

import javax.inject.Inject;

import io.realm.Realm;

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

    public void finish() {
        realm.close();
    }

}
