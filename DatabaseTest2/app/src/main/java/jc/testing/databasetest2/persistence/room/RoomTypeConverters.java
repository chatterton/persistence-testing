package jc.testing.databasetest2.persistence.room;

import android.arch.persistence.room.TypeConverter;

import jc.testing.databasetest2.model.Legislator;

public class RoomTypeConverters {

    @TypeConverter
    public static String fromLegislatorParty(Legislator.Party value) {
        switch (value) {
            case DEMOCRAT:
                return "DEMOCRAT";
            case REPUBLICAN:
                return "REPUBLICAN";
        }
        return "OTHER";
    }

    @TypeConverter
    public static Legislator.Party stringToParty(String string) {
        switch (string) {
            case "DEMOCRAT":
                return Legislator.Party.DEMOCRAT;
            case "REPUBLICAN":
                return Legislator.Party.REPUBLICAN;
        }
        return Legislator.Party.OTHER;
    }

}
