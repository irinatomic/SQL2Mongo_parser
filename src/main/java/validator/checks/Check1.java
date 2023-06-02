package validator.checks;

import errors.Error;
import interfaces.ApplicationFramework;
import sql.SQLImplemet;
import sql.tokens.*;

public class Check1 extends Check {

    //Must have SELECT and FROM clauses

    @Override
    public boolean checkRule(){
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        Query query = sqlImplemet.getCurrQuery();

        boolean hasSelect = false;
        boolean hasFrom = false;

        if(query.getSelectClause() != null)
            hasSelect = true;
        if(query.getFromClause() != null)
            hasFrom = true;

        // proveri da li je ispunjen uslov
        if(!hasSelect || !hasFrom)
            ApplicationFramework.getInstance().getErrorGenerator().createErrorMessage(Error.NO_MANDATORY_CLAUSES);

        return hasSelect && hasFrom;
    }
}
