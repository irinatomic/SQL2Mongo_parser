package adapter.translator;

import adapter.AdapterImpl;
import interfaces.ApplicationFramework;
import org.bson.Document;
import sql.tokens.*;
import sql.tokens.helpers.SelectParameter;

import java.util.List;

public class ProjectTranslator extends Translator{

    @Override
    public void translate(Query query) {
        SelectClause sc = query.getSelectClause();
        GroupByClause gbc = query.getGroupByClause();
        List<SelectParameter> selectParams = sc.getParameters();
        AdapterImpl adapter = (AdapterImpl)ApplicationFramework.getInstance().getAdapter();
        String ourCollection = adapter.getCollectionName();

        //edge case - select *
        if(selectParams.get(0).getName().equals("*"))
            return;

        String $project = "{ $project: {";
        for(SelectParameter sp : selectParams){
            String name = sp.getName();
            if(sp.getAggregateFunction() != null)
                name = aggrParamNewName(sp);

            if(gbc != null && gbc.containsColumn(name))
                $project += name + ": \"$_id." + name + "\", ";

            else {
                if(sp.getTable() == null || sp.getTable().equals(ourCollection))
                    $project += name + ": 1, ";
                else
                    $project += name + ": \"$" + adapter.getTablesInLookups().get(sp.getTable()) + "." + name + "\", ";
            }
        }
        $project += "_id: 0 } }";

        Document doc = Document.parse($project);
        ((AdapterImpl) ApplicationFramework.getInstance().getAdapter()).getDocs().add(doc);
        System.out.println($project);
    }
    private String aggrParamNewName(SelectParameter sp){
        return sp.getAggregateFunction().toString().replace("$", "") + sp.getName();
    }
}
