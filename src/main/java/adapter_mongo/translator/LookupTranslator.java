package adapter_mongo.translator;

import adapter_mongo.AdapterImpl;
import interfaces.ApplicationFramework;
import sql.tokens.helpers.SelectParameter;

import java.util.List;

import org.bson.Document;

import sql.SQLImplemet;
import sql.tokens.*;
import sql.tokens.helpers.JoinClause;

// We will either have joins or either have a subquery
// WHERE clause either has normal inequalities or 1 subquery

/* Lookup for joins (max 2 joins)
 * {
 *  $lookup: {
 *    from: "rightTable",
 *    localField: "lefTableField",
 *    foreignField: "rightTableField",
 *    as: "result1"
 *   }
 * } ,
 * { $unwind: "result1" }
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

public class LookupTranslator extends Translator{

    @Override
    public void translate(Query query) {
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        Query subQuery = sqlImplemet.getCurrSubquery();

        // First we do the basic lookup for joins
        FromClause fc = query.getFromClause();
        WhereClause wc = query.getWhereClause();

        //set the main collection (table we're querying)
        String mainTable = fc.getFromTable();
        ((AdapterImpl)ApplicationFramework.getInstance().getAdapter()).setCollectionName(mainTable);

        // Either we do the joins
        if(subQuery == null){
          if(!fc.getJoins().isEmpty()){
            System.out.println("ulazim u lookup");
            lookupForJoin(fc);
            return;
          }
        }else{
          System.out.println("ima SUB al ulazim u lookup");
          lookupForSubQuery(fc, wc);
          return;
        }
        

        // Or either we do the subquery
        // ...
        return;
    }

    private void lookupForJoin(FromClause fc){

        int i = 1;
        for(JoinClause j : fc.getJoins()){
            String result = "result" + i++;
            String $lookup = "{\n" +
                                "$lookup: {\n" +
                                    "from: \"" + j.getTable2() + "\",\n" +
                                    "localField: \"" + j.getFieldTable1() + "\",\n" +
                                    "foreignField: \"" + j.getFieldTable2() + "\",\n" +
                                    "as: \"" + result + "\"\n"+
                               "}\n" +
                            "},";

            Document lookupDoc = Document.parse($lookup);
            Document unwindDoc = Document.parse("{ $unwind: \"$" + result + "\" }, ");
            AdapterImpl adapter = (AdapterImpl)ApplicationFramework.getInstance().getAdapter();
            adapter.getDocs().add(lookupDoc);
            adapter.getDocs().add(unwindDoc);
            adapter.getTablesInLookups().putIfAbsent(j.getTable2(), result);
            System.out.println("Odradio Lookup");
            System.out.println($lookup);
        }
    }

    private void lookupForSubQuery(FromClause fc, WhereClause wc){
      String[] words = wc.getOriginalText().split(" ");
      int i = 1;
      for(JoinClause j : fc.getJoins()){
          String result = "result" + i++;
          String $lookup = "{\n" +
                              "$lookup: {\n" +
                                  "from: \"" + j.getTable2() + "\",\n" +
                                  "localField: \"" + words[0] + "\",\n" +
                                  "foreignField: \"" + words[0] + "\",\n" +
                                  "as: \"" + result + "\"\n"+
                             "}\n" +
                          "},";

          Document lookupDoc = Document.parse($lookup);
          AdapterImpl adapter = (AdapterImpl)ApplicationFramework.getInstance().getAdapter();
          adapter.getDocs().add(lookupDoc);
          adapter.getTablesInLookups().putIfAbsent(j.getTable2(), result);
          System.out.println("Odradio Lookup");
          System.out.println($lookup);
      }
  }
}
