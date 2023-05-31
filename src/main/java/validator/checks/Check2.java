package validator.checks;

import java.util.*;

import errors.Error;
import interfaces.ApplicationFramework;
import sql.SQLImplemet;
import sql.composite.Token;
import sql.implementation.*;
import sql.implementation.helpers.SelectParameter;

public class Check2 extends Check {

    @Override
    public boolean checkRule() {
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        Query query = sqlImplemet.getCurrQuery();
        List<Token> clauses = query.getChildren();
        SelectClause selectClause;
        GroupByClause groupByClause;

        List<String> mustHave = new ArrayList<>();

        for (Token t: clauses){
            if (t instanceof SelectClause){
                selectClause = (SelectClause) t;
                for (SelectParameter s: selectClause.getParameters()){
                    if (s.getAggregateFunction() == null){
                        mustHave.add(s.getOriginalText());
                    }
                }
            }
            if (t instanceof GroupByClause){
                groupByClause = (GroupByClause) t;
                System.out.println(groupByClause.getParameters());
                for (String s: mustHave){
                    if (!groupByClause.getParameters().contains(s)){
                        ApplicationFramework.getInstance().getErrorGenerator().createErrorMessage(Error.GROUP_BY_ERROR);
                        return false;
                    }
                }
            }
        }

        
        
        return true;
    }
}
