package jc.testing.databasetest2;

import android.content.Context;
import android.util.Log;

import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.functions.BiConsumer;
import jc.testing.databasetest2.model.ImmutableLegislator;
import jc.testing.databasetest2.model.Legislator;

public class Loader {

    private final Context context;

    public Loader(Context c) {
        context = c;
    }

    @SuppressWarnings("unchecked")
    private class LoaderState {
        int position = 0;

        private List<Map<String, Map<String, String>>> list = null;

        void load() {
            Yaml yaml = new Yaml();
            try {
                list = (List<Map<String, Map<String, String>>>) yaml.load(context.getAssets().open("legislators-current.yaml"));
            } catch (Exception e) {
                Log.e("_JC", e.getMessage());
            }
        }

        int getLegislatorCount() {
            if (null != list) {
                return list.size();
            }
            return -1;
        }

        Legislator getLegislatorAtPosition(int position) {
            Map<String, Map<String, String>> map = list.get(position);
            String party = ((List<Map<String, String>>)map.get("terms")).get(0).get("party");
            Legislator.Party partyValue = Legislator.Party.OTHER;
            if ("Democrat".equals(party)) {
                partyValue = Legislator.Party.DEMOCRAT;
            } else if ("Republican".equals(party)) {
                partyValue = Legislator.Party.REPUBLICAN;
            }
            return ImmutableLegislator.builder()
                    .name(map.get("name").get("official_full"))
                    .religion(map.get("bio").get("religion") != null ? map.get("bio").get("religion") : "")
                    .termCount(((List)map.get("terms")).size())
                    .party(partyValue)
                    .id(map.get("id").get("wikidata"))
                    .build();
        }

    }

    public Flowable<Legislator> loadData() {
        return Flowable.generate(new Callable<LoaderState>() {
            @Override
            public LoaderState call() throws Exception {
                LoaderState loader = new LoaderState();
                loader.load();
                return loader;
            }
        }, new BiConsumer<LoaderState, Emitter<Legislator>>() {
            @Override
            public void accept(LoaderState loader, Emitter<Legislator> emitter) throws Exception {
                if (loader.position >= loader.getLegislatorCount()) {
                    emitter.onComplete();
                } else {
                    emitter.onNext(loader.getLegislatorAtPosition(loader.position));
                    loader.position++;
                }
            }
        });
    }

}
