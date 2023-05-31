package validator.checks;

import errors.Error;
import interfaces.ApplicationFramework;
import sql.SQLImplemet;
import sql.composite.Token;
import sql.implementation.*;

import java.util.List;

public class Check1 extends Check {

    @Override
    public boolean checkRule(){
        Query query = SQLImplemet.getCurrQuery();
        List<Token> clauses = query.getChildren();

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
