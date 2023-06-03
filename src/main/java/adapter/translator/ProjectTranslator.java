package adapter.translator;

import sql.tokens.Query;
import sql.tokens.SelectClause;
import sql.tokens.helpers.SelectParameter;

import java.util.List;

public class ProjectTranslator extends Translator{

    @Override
    public void translate(Query query) {
        SelectClause sc = query.getSelectClause();
        List<SelectParameter> selectParams = sc.getParameters();

        String projectDoc = "{ $project: {";
        for(SelectParameter sp : selectParams){
            String name = sp.getName();
            if(sp.getAggregateFunction() != null)
                name = aggrParamNewName(sp);
            projectDoc += name + ": 1, ";
        }
        projectDoc += "_id: 0 } }";

        System.out.println(projectDoc);
    }
    private String aggrParamNewName(SelectParameter sp){
        return sp.getAggregateFunction().toString().replace("$", "") + sp.getName();
    }
}
