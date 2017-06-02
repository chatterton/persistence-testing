package jc.evaluation.dbtest.persistence.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RealmUserEntity extends RealmObject {

    @PrimaryKey
    public String id;

    @Required
    public String firstName;

    @Required
    public String lastName;

}
