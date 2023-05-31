package validator.checks;

import java.util.List;

import errors.Error;
import interfaces.ApplicationFramework;
import sql.SQLImplemet;
import sql.composite.Token;
import sql.implementation.*;
import sql.implementation.helpers.JoinClause;

public class Check4 extends Check {

    // JOIN clauses must have either USING or ON

    @Override
    public boolean checkRule() {
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        Query query = sqlImplemet.getCurrQuery();
        List<Token> clauses = query.getChildren();

        // Unknown error
        if(clauses.isEmpty())
            ApplicationFramework.getInstance().getErrorGenerator().createErrorMessage(Error.UNKNOWN);

        // Will not be null because it passed the Check1
        FromClause fromClause = null;
        for(Token t : clauses){
            if(t instanceof FromClause){
                fromClause = (FromClause) t;
                break;
            }
        }

        List<JoinClause> joins = fromClause.getJoins();
        if(joins == null || joins.isEmpty())
            return true;

        for(JoinClause j : joins){
            if (j.getFieldTable1().equals(" ") && j.getFieldTable2().equals(" ")){
                ApplicationFramework.getInstance().getErrorGenerator().createErrorMessage(Error.NO_JOIN_CONDITION);
                return false;
            }
        }

        return true;
    }
}