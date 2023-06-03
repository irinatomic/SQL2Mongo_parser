package validator.checks;

import java.util.*;

import errors.Error;
import interfaces.ApplicationFramework;
import sql.SQLImplemet;
import sql.tokens.*;
import sql.tokens.helpers.SelectParameter;

public class Check2 extends Check {

    //If there is an aggregate function in select:
    //All select parameters (not aggregate functions) must be in GROUP BY

    @Override
    public boolean checkRule() {
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        Query query = sqlImplemet.getCurrQuery();

        // First : check if there is an aggregate function in select clause
        boolean  hasAggregate = false;
        for(SelectParameter sp : query.getSelectClause().getParameters()){
            if(sp.getAggregateFunction() != null){
                hasAggregate = true;
                break;
            }
        }

        if(!hasAggregate)
            return true;

        List<String> mustHave = getParametersWithoutAggregate(query.getSelectClause());
        String groupByText = query.getGroupByClause().getOriginalText();

        groupByText = groupByText.toLowerCase();
        for(String s : mustHave){
            if(!groupByText.contains(s.toLowerCase())){
                ApplicationFramework.getInstance().getErrorGenerator().createErrorMessage(Error.GROUP_BY_ERROR);
                return false;
            }
        }

        return true;
    }

    private List<String> getParametersWithoutAggregate(SelectClause selectClause){
        List<String> mustHave = new ArrayList<>();
        for (SelectParameter s: selectClause.getParameters()){
            if (s.getAggregateFunction() == null) {
                String potential = s.getOriginalText() + " ";
                mustHave.add(potential.split(" ")[0]);          //in case: 'select nesto as nes'
            }
        }
        return mustHave;
    }
}

