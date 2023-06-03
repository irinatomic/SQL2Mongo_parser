package adapter.translator;

import interfaces.ApplicationFramework;
import sql.SQLImplemet;
import sql.tokens.Query;
import sql.tokens.WhereClause;
import sql.tokens.helpers.WhereInequality;

/* Used when we have multiple inequalities in WHERE clause
 * select ime, prezime from tabela where A > B or C > D and E < L
$match: {
      $or: [
        { $gt: ["$A", "$B"] },
        { $and: [ { $gt: ["$C", "$D"] }, { $lt: ["$E", "$L"] } ] }
      ]
    }
 */

public class MatchTranslator extends Translator{

    @Override
    public void translate(Query query) {

        // We are supporting either inequailities or 1 subquery
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        if(sqlImplemet.getCurrSubquery() != null)
            return;

        WhereClause wc = query.getWhereClause();

        String match = "{ $match: {";





        match += "} }";
        System.out.println(match);
    }

    private String turnWhereInequalityToMongo(WhereInequality wi){
        String res = "{ " + wi.getComparison() + ": [\"$" + wi.getLeft() + "\", \"$" + wi.getRight() + "\"] }";
        return res;
    }
}



