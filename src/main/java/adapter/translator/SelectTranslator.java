package adapter.translator;

import sql.tokens.*;
import sql.tokens.helpers.SelectParameter;

import java.util.*;

/* SQL: select firt_name, last_name, min(salary) from employees
db.employees.aggregate([
    { $group: { _id: {},
                first_name: { $first: "$first_name" },
                last_name: { $first: "$last_name" },
                min_salary: { $min: "$salary" }
              }
    },
    { $project: {first_name: 1, last_name: 1,min_salary: 1, _id: 0} }
])
 */

public class SelectTranslator extends Translator{

    @Override
    public void translate(Query query) {
        SelectClause sc = query.getSelectClause();
        GroupByClause gbc = query.getGroupByClause();

        // Select -> $project
        List<SelectParameter> selectParams = sc.getParameters();
        List<SelectParameter> selectParamsAggr = new ArrayList<>();
        List<SelectParameter> selectParamsNOAggr = new ArrayList<>();

        String projectDoc = "{ $project: {";
        for(SelectParameter sp : selectParams){
            String name = sp.getName();
            if(sp.getAggregateFunction() != null) {
                selectParamsAggr.add(sp);
                name = aggrParamNewName(sp);
            } else
                selectParamsNOAggr.add(sp);
            projectDoc += name + ": 1, ";
        }
        projectDoc += "_id: 0 } }";

        // $group contains the parameters from the GROUP BY CLAUSE in _id: {}
        // and contains the select params that are under the aggregate function

        String groupIdDoc = " _id: {";
        if(gbc != null){
            for(String param : gbc.getParameters()){
                String paramMongo = param + ": '$" + param + "', ";
                groupIdDoc += paramMongo;
            }
        }
        groupIdDoc += "}, ";

        String groupAggrDoc = "";
        for(SelectParameter spa : selectParamsAggr){
            String newName = aggrParamNewName(spa);
            groupAggrDoc += newName + ": { " + spa.getAggregateFunction() + ": '$" + spa.getName() + "' }, ";
        }

        if(!selectParamsAggr.isEmpty()){
            for(SelectParameter spna : selectParamsNOAggr){
                groupAggrDoc += spna.getName() + ": { $first: \"$" + spna.getName() + "\" }, ";
            }
        }

        String groupDoc = "";
        if(gbc != null || !groupAggrDoc.equals("")){
            groupDoc += "{ $group: {";
            groupDoc += groupIdDoc;
            groupDoc += groupAggrDoc;
            groupDoc += "} }";
        }

        System.out.println(projectDoc);
        System.out.println(groupDoc);
    }

    private String aggrParamNewName(SelectParameter sp){
        return sp.getAggregateFunction().toString().replace("$", "") + sp.getName();
    }
}
