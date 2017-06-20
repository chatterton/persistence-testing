package jc.testing.databasetest2.persistence.realm;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import jc.testing.databasetest2.model.ImmutableLegislator;
import jc.testing.databasetest2.model.Legislator;

public class RealmLegislatorService {

    @Inject
    public RealmLegislatorService() { }

    public void persistEntities(final List<Legislator> legislators) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Legislator l : legislators) {
                    RealmLegislatorEntity entity = realm.createObject(RealmLegislatorEntity.class, l.id());
                    entity.name = l.name();
                    entity.setParty(l.party());
                    entity.religion = l.religion();
                    entity.termCount = l.termCount();
                }
            }
        });
        realm.close();
    }

    public List<Legislator> getAll(String name) {
        final Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmLegislatorEntity> results = realm.where(RealmLegislatorEntity.class)
                .contains("name", name, Case.INSENSITIVE)
                .findAll();
        List<Legislator> list = new ArrayList<>();
        for (RealmLegislatorEntity entity : results) {
            Legislator l = ImmutableLegislator.builder()
                    .id(entity.id)
                    .name(entity.name)
                    .party(entity.getParty())
                    .religion(entity.religion)
                    .termCount(entity.termCount)
                    .build();
            list.add(l);
        }
        realm.close();
        return list;
    }

    public void deleteAll() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
        realm.close();
    }

}
