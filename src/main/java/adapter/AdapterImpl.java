package adapter;

import adapter.translator.*;
import interfaces.Adapter;
import lombok.Getter;
import lombok.Setter;
import sql.tokens.Query;
import java.util.*;

@Getter
@Setter
public class AdapterImpl implements Adapter {

    private String collectionName;                              //table name
    private List<Translator> translators;
    private List<String> stages;
    private String mainCollecition;

    public AdapterImpl(){
        this.translators = new ArrayList<>();
        this.stages = new ArrayList<>();

        LookupTranslator lookup = new LookupTranslator();
        MatchTranslator match = new MatchTranslator();
        GroupTranslator group = new GroupTranslator();
        SortTranslator sort = new SortTranslator();
        ProjectTranslator project = new ProjectTranslator();
        translators.addAll(Arrays.asList(lookup, match, group, sort, project));
    }

    @Override
    public void adaptQueryForMongo(Query query) {

        for(Translator t : translators)
            t.translate(query);
    }
}
