package adapter.translator;

import sql.tokens.OrderByClause;
import sql.tokens.Query;

import java.util.Map;

public class OrderByTranslator extends Translator{

    @Override
    public void translate(Query query) {
        OrderByClause obc = query.getOrderByClause();

        if(obc == null) return;

        String sortDoc = "{ $sort: {";
        for (Map.Entry<String, Integer> entry : obc.getParameters().entrySet()) {
            sortDoc += entry.getKey() + ": " + entry.getValue() + ", ";
        }
        sortDoc += " } }";

        System.out.println(sortDoc);
    }
}
