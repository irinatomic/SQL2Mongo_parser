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
/*  Lookup for subqueries
    SELECT * from orders where user_id ?? (select user_id from orders where refund_id = 123);
  {
    "$lookup": {
      "from": "collection",
      "localField": "user_id",
      "foreignField": "user_id",
      "as": "result"
    }
  }

    ?? = in
    $match: { user_id: { $in: db.orders.distinct("user_id", { refund_id: 123 }) } }

    ?? = '='
    $match: { "result.refund_id": 123 }
 */

public class MatchTranslator extends Translator{

    @Override
    public void translate(Query query) {

        // We are supporting either inequailities or 1 subquery
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        Query supQuery = sqlImplemet.getCurrSubquery();
        // if(sqlImplemet.getCurrSubquery() != null)
        //     return;

        WhereClause wc = query.getWhereClause();
        
        if(wc == null)
            return;

        String $match = "{ $match: ";
        if(sqlImplemet.getCurrSubquery() == null){
            $match += turnWhereParameterToMongo(wc, wc.getParams().get(0), 0);
        }
        else{
            WhereClause swc = supQuery.getWhereClause();
            String[] words = wc.getOriginalText().split(" ");
            String[] pomWords = swc.getOriginalText().split(" ");
            if(words[1].equalsIgnoreCase("in")){
                //ove sam slpitova da bih pre sub dobio where words[0] in/= i SubQuery
                $match += "{ " + words[0] + ": { $in: db.orders.distinct(\"" + words[0] + "\", { ";
                //then i split WC in sunQuery to get parametars
                $match += pomWords[0] + ": " + pomWords[2] + " })}}";
            }
            if(words[1].equals("=")){  
                $match += "{ \" result." + pomWords[0] + "\": " + pomWords[2] + " }";
            }
            
        }
        
        $match += " }";

        System.out.println($match);

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



