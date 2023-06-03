package adapter.translator;

import adapter.AdapterImpl;
import interfaces.ApplicationFramework;
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

        // First we do the basic lookup for joins
        FromClause fc = query.getFromClause();

        //set the main collection (table we're querying)
        String mainTable = fc.getFromTable();
        ((AdapterImpl)ApplicationFramework.getInstance().getAdapter()).setMainCollecition(mainTable);

        // Either we do the joins
        if(!fc.getJoins().isEmpty()){
            lookupForJoin(fc);
            return;
        }

        // Or either we do the subquery
        // ...

    }

    private void lookupForJoin(FromClause fc){

        int i = 1;
        for(JoinClause j : fc.getJoins()){
            String result = "result" + i++;
            String lookup = "{\n" +
                                "$lookup: {\n" +
                                    "from: \"" + j.getTable2() + "\",\n" +
                                    "localField: \"" + j.getFieldTable1() + "\",\n" +
                                    "foreignField: \"" + j.getFieldTable2() + "\",\n" +
                                    "as: \"" + result + "\"\n"+
                               "}\n" +
                            "},\n" +
                            "{ $unwind: \"" + result + "\" }  ";

            System.out.println(lookup);
        }
    }
}
