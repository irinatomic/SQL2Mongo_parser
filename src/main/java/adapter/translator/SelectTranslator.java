package adapter.translator;

import sql.tokens.*;
import sql.tokens.helpers.SelectParameter;

import java.util.*;

public class SelectTranslator extends Translator{

    private SelectClause sc;
    private GroupByClause gbc;

    @Override
    public void translate(Query query) {
        this.sc = query.getSelectClause();
        this.gbc = query.getGroupByClause();

        // Select -> $project
        List<SelectParameter> selectParams = sc.getParameters();
        List<SelectParameter> selectParamsAggr = new ArrayList<>();

        String projectDoc = "{ $project: {";
        for(SelectParameter sp : selectParams){
            if(sp.getAggregateFunction() != null)
                selectParamsAggr.add(sp);

            projectDoc += sp.getName() + ": '$" + sp.getName() + "', ";
        }
        projectDoc += "} }";

        // $group contains the parameters from the GROUP BY CLAUSE in _id: {}
        // and contains the select params that are under the aggregate function

        String groupIdDoc = "{ $group: { _id: {";
        for(String param : gbc.getParameters()){
            String paramMongo = param + ": '$" + param + "', ";
            groupIdDoc += paramMongo;
        }
        groupIdDoc += "}, ";

        for(SelectParameter spa : selectParamsAggr){
            String newName = spa.getAlias().replace("$", "") + spa.getName();
            groupIdDoc += newName + ": { " + spa.getAggregateFunction() + ": '$" + spa.getName() + "' }, ";
        }
        groupIdDoc += "} }";


        System.out.println(projectDoc);
        System.out.println(groupIdDoc);
    }
}
