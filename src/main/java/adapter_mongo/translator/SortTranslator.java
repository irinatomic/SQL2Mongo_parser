package adapter_mongo.translator;

import adapter_mongo.AdapterImpl;
import interfaces.ApplicationFramework;
import org.bson.Document;
import sql.tokens.*;
import java.util.Map;

public class SortTranslator extends Translator{

    @Override
    public void translate(Query query) {
        OrderByClause obc = query.getOrderByClause();

        if(obc == null) return;

        String $sort = "{ $sort: {";
        for (Map.Entry<String, Integer> entry : obc.getParameters().entrySet()) {
            $sort += entry.getKey() + ": " + entry.getValue() + ", ";
        }
        $sort += " } }";

        Document doc = Document.parse($sort);
        ((AdapterImpl) ApplicationFramework.getInstance().getAdapter()).getDocs().add(doc);
        //System.out.println(sortDoc);
    }
}
