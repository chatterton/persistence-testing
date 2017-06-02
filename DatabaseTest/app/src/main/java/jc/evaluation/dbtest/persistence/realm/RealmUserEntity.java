package jc.evaluation.dbtest.persistence.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import jc.evaluation.dbtest.persistence.User;

public class RealmUserEntity extends RealmObject implements User {

    @PrimaryKey
    public String id;

    @Required
    public String firstName;

    @Required
    public String lastName;

    public RealmList<RealmClickEntity> clicks;

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public int getClicks() {
        return clicks.size();
    }

}
