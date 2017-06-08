package jc.testing.databasetest2.persistence;

import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import jc.testing.databasetest2.model.Legislator;
import jc.testing.databasetest2.persistence.room.LegislatorDao;
import jc.testing.databasetest2.persistence.room.RoomLegislatorEntity;

public class LegislatorStore {

    private Mode mode = Mode.ROOM;

    private LegislatorDao legislatorDao;

    public LegislatorStore(LegislatorDao legislatorDao) {
        this.legislatorDao = legislatorDao;
    }

    public Flowable<Object> deleteAll2() {
        return Flowable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                switch (mode) {
                    case ROOM:
                        legislatorDao.deleteAll();
                        break;
                    case REALM:
                        break;
                }
                return new Object();
            }
        });
    }

    public void deleteAll() {
        switch (mode) {
            case ROOM:
                legislatorDao.deleteAll();
                break;
            case REALM:
                break;
        }
    }

    public Consumer<Legislator> legislatorPersister() {
        switch (mode) {
            case ROOM:
                return roomLegislatorPersister();
            case REALM:
                break;
        }
        return null;
    }

    private Consumer<Legislator> roomLegislatorPersister() {
        return new Consumer<Legislator>() {
            @Override
            public void accept(@NonNull Legislator legislator) throws Exception {
                RoomLegislatorEntity entity = new RoomLegislatorEntity();
                entity.id = legislator.id();
                entity.name = legislator.name();
                entity.party = legislator.party();
                entity.religion = legislator.religion();
                entity.termCount = legislator.termCount();
                legislatorDao.insert(entity);
            }
        };
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }

    public enum Mode {
        REALM,
        ROOM
    }

}
