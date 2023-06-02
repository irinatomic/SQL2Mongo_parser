package adapter;

import adapter.translator.*;
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
    private List<Document> documents;
    private List<Translator> translators;

    private List<String> stages;

    public AdapterImpl(){
        this.documents = new ArrayList<>();
        this.translators = new ArrayList<>();
        this.stages = new ArrayList<>();

        SelectTranslator st = new SelectTranslator();
        OrderByTranslator obt = new OrderByTranslator();
        translators.addAll(Arrays.asList(st, obt));
    }

    @Override
    public void adaptQueryForMongo(Query query) {

        for(Translator t : translators)
            t.translate(query);
    }
}
