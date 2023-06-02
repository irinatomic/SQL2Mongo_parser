package validator.checks;

import java.util.*;

import errors.Error;
import interfaces.ApplicationFramework;
import sql.SQLImplemet;
import sql.architecture.Token;
import sql.implementation.*;
import sql.implementation.helpers.SelectParameter;

public class Check3 extends Check {

    // Aggregate functions must go in HAVING, not in WHERE

    @Override
    public boolean checkRule() {
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        Query query = sqlImplemet.getCurrQuery();
        List<Token> clauses = query.getClauses();

        // Unknown error
        if(clauses.isEmpty())
            ApplicationFramework.getInstance().getErrorGenerator().createErrorMessage(Error.UNKNOWN);

        List<String> mustHave = new ArrayList<>();
        String whereClauseText = "";
        String havingClauseText = "";

        for(Token t : clauses){
            if (t instanceof SelectClause)
                mustHave = getParametersWithAggregate((SelectClause) t);
            if(t instanceof WhereClause)
                whereClauseText = ((WhereClause) t).getOriginalText();
            if(t instanceof HavingClause)
                havingClauseText = ((HavingClause) t).getOriginalText();
        }

        whereClauseText = whereClauseText.toLowerCase();
        havingClauseText = havingClauseText.toLowerCase();

        for(String s : mustHave){
            s = s.toLowerCase();
            if(whereClauseText.contains(s) || !havingClauseText.contains(s)) {
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