package validator.checks;

import java.util.*;

import errors.Error;
import interfaces.ApplicationFramework;
import sql.SQLImplemet;
import sql.architecture.Token;
import sql.implementation.*;
import sql.implementation.helpers.SelectParameter;

public class Check2 extends Check {

    //All select parameters (not aggregate functions) must be in GROUP BY

    @Override
    public boolean checkRule() {
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        Query query = sqlImplemet.getCurrQuery();
        List<Token> clauses = query.getClauses();

        // Unknown error
        if(clauses.isEmpty())
            ApplicationFramework.getInstance().getErrorGenerator().createErrorMessage(Error.UNKNOWN);

        List<String> mustHave = new ArrayList<>();
        String groupByText = "";

        for(Token t : clauses){
            if (t instanceof SelectClause)
                mustHave = getParametersWithoutAggregate((SelectClause) t);
            if(t instanceof GroupByClause)
                groupByText = ((GroupByClause) t).getOriginalText();
        }

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

