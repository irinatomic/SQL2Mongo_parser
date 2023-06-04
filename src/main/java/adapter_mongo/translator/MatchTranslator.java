package adapter_mongo.translator;

import adapter_mongo.*;
import interfaces.ApplicationFramework;
import org.bson.Document;
import sql.SQLImplemet;
import sql.tokens.*;
import sql.tokens.helpers.*;

/* Used ONLY FOR QUERY (not subquery) when we have multiple inequalities in WHERE clause
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
        // We wish to enter here in 2 cases
        // 1. no subqueries
        // 2. this is a where in a subquery

        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();

        // RETURN IF: it is a parent query and there is no subquery and no where clause
        if(!query.isHasParent() && sqlImplemet.getCurrSubquery() == null){
            if(query.getWhereClause() == null)
                return;
        }

        // RETURN IF: it is a parent query and there is a subquery
        if(!query.isHasParent() && sqlImplemet.getCurrSubquery() != null)
            return;

        WhereClause wc = query.getWhereClause();
        //System.out.println("Match translator - wc.originalText: " + wc.getOriginalText());

        String $match = "{ $match: ";
        $match += turnWhereParameterToMongo(query, wc, wc.getParams().get(0), 0);
        $match += " }";

        System.out.println($match);
        Document doc = Document.parse($match);
        ((AdapterImpl)ApplicationFramework.getInstance().getAdapter()).getDocs().add(doc);
    }

    private String turnWhereInequalityToMongo(Query q, WhereInequality wi){
        AdapterImpl adapter = (AdapterImpl)ApplicationFramework.getInstance().getAdapter();
        String fullName = wi.getLeft();

        if(wi.getTable() != null)
            fullName = "\"" + adapter.getTablesInLookups().get(wi.getTable()) + "." + wi.getLeft() + "\"";

        String res = "{ " + fullName + ": {" + wi.getComparison() + ": " + wi.getRight() + "} }";
        String resSubquery = "{ \"result." + wi.getLeft() + "\": {" + wi.getComparison() + ": " + wi.getRight() + "} }";

        if(q.isHasParent())
            return resSubquery;
        return res;
    }

    private String turnWhereParameterToMongo(Query q, WhereClause wc, WhereParameter wp, int i){

        if(wp.getLogical() == null)
            return turnWhereInequalityToMongo(q, wp.getLeft());

        String res = "{ " + wp.getLogical() + ": [ " + turnWhereInequalityToMongo(q, wp.getLeft()) + ",";
        res += turnWhereParameterToMongo(q, wc, wc.getParams().get(i+1), i+1);
        res += " ] }";
        return res;
    }
}



