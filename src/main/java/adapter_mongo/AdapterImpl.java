package adapter_mongo;

import adapter_mongo.translator.*;
import interfaces.Adapter;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import sql.tokens.Query;
import java.util.*;

@Getter
@Setter
public class AdapterImpl implements Adapter {

    private String collectionName;                              //table name
    private List<Translator> translators;
    private List<Document> docs;
    private Map<String, String> tablesInLookups;

    public AdapterImpl(){
        this.translators = new ArrayList<>();
        this.docs = new ArrayList<>();
        this.tablesInLookups = new HashMap<>();

        LookupTranslator lookup = new LookupTranslator();
        MatchTranslator match = new MatchTranslator();
        GroupTranslator group = new GroupTranslator();
        SortTranslator sort = new SortTranslator();
        ProjectTranslator project = new ProjectTranslator();
        translators.addAll(Arrays.asList(lookup, match, group, sort, project));
    }

    @Override
    public void adaptQueryForMongo(Query query) {
        this.docs.clear();
        for(Translator t : translators)
            t.translate(query);
    }
}
