package adapter.translator;

import adapter.AdapterImpl;
import interfaces.ApplicationFramework;
import org.bson.Document;
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

public class GroupTranslator extends Translator{

    @Override
    public void translate(Query query) {
        AdapterImpl adapter = (AdapterImpl) ApplicationFramework.getInstance().getAdapter();
        SelectClause sc = query.getSelectClause();
        GroupByClause gbc = query.getGroupByClause();

        // Select -> $project
        List<SelectParameter> selectParams = sc.getParameters();
        List<SelectParameter> selectParamsAggr = new ArrayList<>();
        List<SelectParameter> selectParamsNOAggr = new ArrayList<>();

        for(SelectParameter sp : selectParams){
            if(sp.getAggregateFunction() != null)
                selectParamsAggr.add(sp);
            else
                selectParamsNOAggr.add(sp);
        }

        // $group contains the parameters from the GROUP BY CLAUSE in _id: {}
        // and contains the select params that are under the aggregate function

        String groupIdDoc = " _id: {";
        if(gbc != null){
            for (Map.Entry<String, String> entry : gbc.getParameters().entrySet()) {
                String column = entry.getKey();
                String table = entry.getValue();

                if(table == null)
                    groupIdDoc += column + ": '$" + column + "', ";
                else
                    groupIdDoc += column + ": '$" + adapter.getTablesInLookups().get(table) + "." + column + "', ";
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
                if(gbc != null && gbc.containsColumn(spna.getName())) continue;
                groupAggrDoc += spna.getName() + ": { $first: \"$" + spna.getName() + "\" }, ";
            }
        }

        String $group = "";
        if(gbc != null || !groupAggrDoc.equals("")){
            $group += "{ $group: {";
            $group += groupIdDoc;
            $group += groupAggrDoc;
            $group += "} }";
        }

        if($group.equals(""))  return;
        Document doc = Document.parse($group);
        adapter.getDocs().add(doc);
    }

    private String aggrParamNewName(SelectParameter sp){
        return sp.getAggregateFunction().toString().replace("$", "") + sp.getName();
    }
}
