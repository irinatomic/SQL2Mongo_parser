package adapter.translator;

import adapter.AdapterImpl;
import interfaces.ApplicationFramework;

import org.bson.Document;

import sql.SQLImplemet;
import sql.tokens.*;
import sql.tokens.helpers.JoinClause;

// We will either have joins or either have a subquery
// WHERE clause either has normal inequalities or 1 subquery

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
          if(!fc.getJoins().isEmpty())
            lookupForJoins(fc);
        } else
          lookupForSubQuery(wc);
    }

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
    private void lookupForJoins(FromClause fc){

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
            String $unwind = "{ $unwind: \"$" + result + "\" }, ";

            Document lookupDoc = Document.parse($lookup);
            Document unwindDoc = Document.parse($unwind);

            AdapterImpl adapter = (AdapterImpl)ApplicationFramework.getInstance().getAdapter();
            adapter.getTablesInLookups().putIfAbsent(j.getTable2(), result);
            adapter.getDocs().add(lookupDoc);
            adapter.getDocs().add(unwindDoc);
            System.out.println($lookup);
            System.out.println($unwind);
        }
    }

    /* Lookup for subquery
     * OPTION 1 no where in subquery : select * from employees where department_id in (select department_id from departments)
        db.employees.aggregate([
          { $lookup: {
              from: "departments",
              localField: "department_id",
              foreignField: "department_id",
              as: "result"
            } },
        ])

    * We add the $match for the where in subquery:
      select * from employees where department_id in
        (select department_id from departments where location_id = 1700)
      { $match: { "result.location_id": 1700 } }


     * Multiple inequalities in where in subquery
       select * from employees where department_id in
        (select department_id from departments where location_id = 1700 and manager_id = 200)
       { $match: {
       $and: [ {"result.location_id": {$eq : 1700}}, {"result.manager_id": {$eq: 200}} ]
       } }
     */
    private void lookupForSubQuery(WhereClause wc){
        //get subquery info
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        Query subquery = sqlImplemet.getCurrSubquery();

        String table = subquery.getFromClause().getFromTable();
        String field = subquery.getSelectClause().getParameters().get(0).getName();

        String $lookup = "{\n" + "" +
                            "$lookup: {\n" +
                                "from: \"" + table + "\", \n" +
                                "localField: \"" + field + "\", \n" +
                                "foreignField: \"" + field + "\", \n " +
                                "as: \"result\" \n" +
                                "}\n" +
                            "}";
        String $unwind = "{ $unwind: \"$result\" }, ";

        Document lookupDoc = Document.parse($lookup);
        Document unwindDoc = Document.parse($unwind);
        AdapterImpl adapter = (AdapterImpl)ApplicationFramework.getInstance().getAdapter();
        adapter.getDocs().add(lookupDoc);
        adapter.getDocs().add(unwindDoc);

        System.out.println($lookup);
        System.out.println($unwind);

        if(subquery.getWhereClause() != null){
            MatchTranslator mt = new MatchTranslator();
            mt.translate(subquery);
        }
    }
}
