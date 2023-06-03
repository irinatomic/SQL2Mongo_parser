package adapter_mongo.translator;

import adapter_mongo.*;
import interfaces.ApplicationFramework;
import org.bson.Document;
import sql.SQLImplemet;
import sql.tokens.*;
import sql.tokens.helpers.*;

/* Used when we have multiple inequalities in WHERE clause
 * select ime, prezime from zaposleni where salary > 10 or nesto < 5 and name like '%ANA'
$match: {
      $or: [ { salary: { $gt: 10 } },
             { $and: [  { nesto: { $lt: 5 } }, { name: { $regex: /ANA$/i } } ] }
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
        if(wc == null)
            return;

        String $match = "{ $match: ";
        $match += turnWhereParameterToMongo(wc, wc.getParams().get(0), 0);
        $match += " }";

        Document doc = Document.parse($match);
        ((AdapterImpl)ApplicationFramework.getInstance().getAdapter()).getDocs().add(doc);
        //System.out.println(match);
    }

    private String turnWhereInequalityToMongo(WhereInequality wi){
        return "{ " + wi.getLeft() + ": {" + wi.getComparison() + ": " + wi.getRight() + "} }";
    }

    private String turnWhereParameterToMongo(WhereClause wc, WhereParameter wp, int i){

        if(wp.getLogical() == null)
            return turnWhereInequalityToMongo(wp.getLeft());

        String res = "{ " + wp.getLogical() + ": [ " + turnWhereInequalityToMongo(wp.getLeft()) + ",";
        res += turnWhereParameterToMongo(wc, wc.getParams().get(i+1), i+1);
        res += " ] }";
        return res;
    }
}



