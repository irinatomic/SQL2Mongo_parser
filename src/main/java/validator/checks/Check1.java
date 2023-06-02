package validator.checks;

import errors.Error;
import interfaces.ApplicationFramework;
import sql.SQLImplemet;
import sql.architecture.Token;
import sql.implementation.*;

import java.util.List;

public class Check1 extends Check {

    //Must have SELECT and FROM clauses

    @Override
    public boolean checkRule(){
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        Query query = sqlImplemet.getCurrQuery();
        List<Token> clauses = query.getClauses();

        boolean hasSelect = false;
        boolean hasFrom = false;

        for(Token t : clauses){
            if(t instanceof SelectClause)
                hasSelect = true;
            if(t instanceof FromClause)
                hasFrom = true;
        }

        // proveri da li je ispunjen uslov
        if(!hasSelect || !hasFrom)
            ApplicationFramework.getInstance().getErrorGenerator().createErrorMessage(Error.NO_MANDATORY_CLAUSES);

        return hasSelect && hasFrom;
    }
}
