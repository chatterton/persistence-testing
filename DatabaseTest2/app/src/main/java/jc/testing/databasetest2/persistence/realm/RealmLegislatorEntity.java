package jc.testing.databasetest2.persistence.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import jc.testing.databasetest2.model.Legislator;

public class RealmLegislatorEntity extends RealmObject {

    @PrimaryKey
    public String id;

    @Required
    public String name;

    public String religion;

    public int termCount;

    private String party;

    public Legislator.Party getParty() {
        if (null != party) {
            return Legislator.Party.valueOf(party);
        }
        return null;
    }

    public void setParty(Legislator.Party p) {
        this.party = p.toString();
    }

}
