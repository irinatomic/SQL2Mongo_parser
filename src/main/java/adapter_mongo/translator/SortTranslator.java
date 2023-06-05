package adapter_mongo.translator;

import adapter_mongo.AdapterImpl;
import interfaces.ApplicationFramework;
import org.bson.Document;
import sql.tokens.*;
import sql.tokens.helpers.OrderByParameter;

import java.util.List;

public class SortTranslator extends Translator{

    @Override
    public void translate(Query query) {
        AdapterImpl adapter = (AdapterImpl) ApplicationFramework.getInstance().getAdapter();
        OrderByClause obc = query.getOrderByClause();
        List<OrderByParameter> params = obc.getParameters();

        if(obc == null) return;

        String $sort = "{ $sort: {";
        for(OrderByParameter param : params){
            if(param.getTable() == null)
                $sort += param.getName() + ": " + param.getOrder() + ", ";
            else
                $sort += "\"" + adapter.getTablesInLookups().get(param.getTable()) + "." + param.getName() + "\": " + param.getOrder() + ", ";
        }
        $sort += " } }";

        Document doc = Document.parse($sort);
        ((AdapterImpl) ApplicationFramework.getInstance().getAdapter()).getDocs().add(doc);
        System.out.println($sort);
    }
}
