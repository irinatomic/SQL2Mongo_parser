package validator.checks;

import java.util.List;

import sql.SQLImplemet;
import sql.composite.Token;
import sql.implementation.*;

public class Check3 extends Check {

    @Override
    public boolean checkRule() {
        Query query = SQLImplemet.getCurrQuery();
        List<Token> clauses = query.getChildren();

        
        return false;
    }
}