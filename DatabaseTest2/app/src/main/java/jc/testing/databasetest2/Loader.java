package jc.testing.databasetest2;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jc.testing.databasetest2.model.ImmutableLegislator;
import jc.testing.databasetest2.model.Legislator;

public class Loader {

    private Context context;

    public Loader(Context c) {
        context = c;
    }

    public List<Legislator> load() {
        Yaml yaml = new Yaml();
        List<Map<String, Map<String, String>>> list = null;
        try {
            list = (List<Map<String, Map<String, String>>>) yaml.load(context.getAssets().open("legislators-current.yaml"));
        } catch (Exception e) {
            Log.e("_JC", e.getMessage());
        }
        if (null != list) {
            List<Legislator> loaded = new ArrayList<>();
            for (Map<String, Map<String, String>> map : list) {
                String party = ((List<Map<String, String>>)map.get("terms")).get(0).get("party");
                Legislator.Party partyValue = Legislator.Party.OTHER;
                if ("Democrat".equals(party)) {
                    partyValue = Legislator.Party.DEMOCRAT;
                } else if ("Republican".equals(party)) {
                    partyValue = Legislator.Party.REPUBLICAN;
                }
                Legislator legislator = ImmutableLegislator.builder()
                        .name(map.get("name").get("official_full"))
                        .religion(map.get("bio").get("religion") != null ? map.get("bio").get("religion") : "")
                        .termCount(((List)map.get("terms")).size())
                        .party(partyValue)
                        .id(map.get("id").get("wikidata"))
                        .build();
                loaded.add(legislator);
                System.out.println(legislator);
            }
            return loaded;
        }
        return null;
    }

}
