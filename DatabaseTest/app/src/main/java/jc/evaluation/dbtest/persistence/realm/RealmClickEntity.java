package jc.evaluation.dbtest.persistence.realm;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class RealmClickEntity extends RealmObject {

    @Required
    Date timestamp;

}
