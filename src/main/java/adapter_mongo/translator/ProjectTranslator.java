package adapter_mongo.translator;

import adapter_mongo.AdapterImpl;
import interfaces.ApplicationFramework;
import org.bson.Document;
import sql.tokens.*;
import sql.tokens.helpers.SelectParameter;

import java.util.List;

public class ProjectTranslator extends Translator{

    @Override
    public void translate(Query query) {
        SelectClause sc = query.getSelectClause();
        List<SelectParameter> selectParams = sc.getParameters();

        String $project = "{ $project: {";
        for(SelectParameter sp : selectParams){
            String name = sp.getName();
            if(sp.getAggregateFunction() != null)
                name = aggrParamNewName(sp);
            $project += name + ": 1, ";
        }
        $project += "_id: 0 } }";

        Document doc = Document.parse($project);
        ((AdapterImpl) ApplicationFramework.getInstance().getAdapter()).getDocs().add(doc);
        //System.out.println(projectDoc);
    }
    private String aggrParamNewName(SelectParameter sp){
        return sp.getAggregateFunction().toString().replace("$", "") + sp.getName();
    }
}
