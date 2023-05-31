package validator.checks;

import java.util.ArrayList;
import java.util.List;

import errors.Error;
import interfaces.ApplicationFramework;
import sql.SQLImplemet;
import sql.composite.Token;
import sql.implementation.*;
import sql.implementation.helpers.SelectParameter;

public class Check2 extends Check {

    @Override
    public boolean checkRule() {
        Query query = SQLImplemet.getCurrQuery();
        List<Token> clauses = query.getChildren();
        SelectClause selectClause;
        GroupByClause groupByClause;

        List<String> mustHave = new ArrayList<>(null);

        for (Token t: clauses){
            if (t instanceof SelectClause){
                selectClause = (SelectClause) t;
                for (SelectParameter s: selectClause.getParameters()){
                    if (s.getAggregateFunction() == null){
                        mustHave.add(s.getName());
                        System.out.println("The answer is: " + s.getName());
                    }
                }
            }
            if (t instanceof GroupByClause){
                groupByClause = (GroupByClause) t;
                for (String s: groupByClause.getParameters()){
                    if (!mustHave.contains(s)){
                        ApplicationFramework.getInstance().getErrorGenerator().createErrorMessage(Error.GROUP_BY_ERROR);
                        return false;
                    }
                }
            }
        }

        
        
        return true;
    }
}
