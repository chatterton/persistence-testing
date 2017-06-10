package jc.testing.databasetest2.persistence.realm;

import java.util.List;

import javax.inject.Inject;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import jc.testing.databasetest2.model.Legislator;

public class RealmLegislatorService {

    @Inject
    public RealmLegislatorService() { }

    public void persistEntities(List<Legislator> legislators) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (Legislator l : legislators) {
            RealmLegislatorEntity entity = realm.createObject(RealmLegislatorEntity.class, l.id());
            entity.name = l.name();
            entity.setParty(l.party());
            entity.religion = l.religion();
            entity.termCount = l.termCount();
        }
        realm.commitTransaction();
        realm.close();
    }

    public RealmResults<RealmLegislatorEntity> getAll(String str, Realm realm) {
        RealmResults<RealmLegislatorEntity> results = realm.where(RealmLegislatorEntity.class)
                .contains("name", str, Case.INSENSITIVE)
                .findAll();
        return results;
    }

    public void deleteAll() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
    }

}
