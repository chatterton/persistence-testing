package jc.testing.databasetest2.persistence.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import jc.testing.databasetest2.model.Legislator;

@Entity(tableName = "legislators")
public class RoomLegislatorEntity {

    @PrimaryKey
    public String id;

    public String name;

    public Legislator.Party party;

    public String religion;

    public int termCount;

}
