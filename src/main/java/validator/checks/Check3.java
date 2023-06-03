package validator.checks;

import java.util.*;

import errors.Error;
import interfaces.ApplicationFramework;
import sql.SQLImplemet;
import sql.tokens.*;
import sql.tokens.helpers.SelectParameter;

public class Check3 extends Check {

    // Aggregate functions must go in HAVING, not in WHERE

    @Override
    public boolean checkRule() {
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        Query query = sqlImplemet.getCurrQuery();

        List<String> mustNotHave = getParametersWithAggregate(query.getSelectClause());
        String whereClauseText = query.getWhereClause().getOriginalText();

        whereClauseText = whereClauseText.toLowerCase();

        for(String s : mustNotHave){
            s = s.toLowerCase();
            if(whereClauseText.contains(s)) {
                ApplicationFramework.getInstance().getErrorGenerator().createErrorMessage(Error.AGGREGATE_FUNCTION_IN_WHERE);
                return false;
            }
        }
        
        return true;
    }

    private List<String> getParametersWithAggregate(SelectClause selectClause){
        List<String> mustHave = new ArrayList<>();
        for (SelectParameter s: selectClause.getParameters()){
            if (s.getAggregateFunction() != null) {
                String potential = s.getOriginalText() + " ";
                mustHave.add(potential.split(" ")[0]);          //in case: 'select nesto as nes'
            }
        }
        return mustHave;
    }
}