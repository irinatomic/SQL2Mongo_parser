package adapter.translator;

import adapter.AdapterImpl;
import interfaces.ApplicationFramework;
import sql.tokens.OrderByClause;
import sql.tokens.Query;

import java.util.Map;

public class SortTranslator extends Translator{

    @Override
    public void translate(Query query) {
        OrderByClause obc = query.getOrderByClause();

        if(obc == null) return;

        String sortDoc = "{ $sort: {";
        for (Map.Entry<String, Integer> entry : obc.getParameters().entrySet()) {
            sortDoc += entry.getKey() + ": " + entry.getValue() + ", ";
        }
        sortDoc += " } }";

        ((AdapterImpl) ApplicationFramework.getInstance().getAdapter()).getStages().add(sortDoc);
        System.out.println(sortDoc);
    }
}
