package jc.testing.databasetest2.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import jc.testing.databasetest2.model.ImmutableLegislator;
import jc.testing.databasetest2.model.Legislator;
import jc.testing.databasetest2.persistence.room.LegislatorDao;
import jc.testing.databasetest2.persistence.room.RoomLegislatorEntity;

public class LegislatorStore {

    public Mode mode = Mode.ROOM;

    private LegislatorDao legislatorDao;

    public LegislatorStore(LegislatorDao legislatorDao) {
        this.legislatorDao = legislatorDao;
    }

    public Flowable<Object> deleteAll() {
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

    public Flowable<List<Legislator>> listAll() {
        switch (mode) {
            case ROOM:
                return roomListAll();
            case REALM:
                break;
        }
        return null;
    }

    public Flowable<List<Legislator>> roomListAll() {
        return legislatorDao.getAll()
                .map(new Function<List<RoomLegislatorEntity>, List<Legislator>>() {
                    @Override
                    public List<Legislator> apply(@NonNull List<RoomLegislatorEntity> roomLegislatorEntities) throws Exception {
                        List<Legislator> list = new ArrayList<>();
                        for (RoomLegislatorEntity roomLegislatorEntity : roomLegislatorEntities) {
                            Legislator l = ImmutableLegislator.builder()
                                    .id(roomLegislatorEntity.id)
                                    .name(roomLegislatorEntity.name)
                                    .party(roomLegislatorEntity.party)
                                    .religion(roomLegislatorEntity.religion)
                                    .termCount(roomLegislatorEntity.termCount)
                                    .build();
                            list.add(l);
                        }
                        return list;
                    }
                });
    }

    public Consumer<List<Legislator>> legislatorPersister() {
        switch (mode) {
            case ROOM:
                return roomLegislatorPersister();
            case REALM:
                break;
        }
        return null;
    }

    private Consumer<List<Legislator>> roomLegislatorPersister() {
        return new Consumer<List<Legislator>>() {
            @Override
            public void accept(@NonNull List<Legislator> legislatorList) throws Exception {
                List<RoomLegislatorEntity> entityList = new ArrayList<>();
                for (Legislator legislator : legislatorList) {
                    RoomLegislatorEntity entity = new RoomLegislatorEntity();
                    entity.id = legislator.id();
                    entity.name = legislator.name();
                    entity.party = legislator.party();
                    entity.religion = legislator.religion();
                    entity.termCount = legislator.termCount();
                    entityList.add(entity);
                }
                legislatorDao.insert(entityList.toArray(new RoomLegislatorEntity[entityList.size()]));
            }
        };
    }

    public enum Mode {
        REALM,
        ROOM
    }

}
