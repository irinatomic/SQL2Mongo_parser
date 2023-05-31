package validator.checks;

import java.util.List;

import sql.SQLImplemet;
import sql.composite.Token;
import sql.implementation.*;
import sql.implementation.helpers.JoinClause;

public class Check4 extends Check {

    @Override
    public boolean checkRule() {
        Query query = SQLImplemet.getCurrQuery();
        List<Token> clauses = query.getChildren();
        FromClause fromClause;

        for(Token t : clauses){
            if(t instanceof FromClause){
                fromClause = (FromClause) t;
                List<JoinClause> joins = fromClause.getJoins();
                for(JoinClause j : joins){
                    if (j.getFieldTable1() == "" && j.getFieldTable2() == ""){
                        return false;
                    }
                }
            }
        }

        return true;
    }
}