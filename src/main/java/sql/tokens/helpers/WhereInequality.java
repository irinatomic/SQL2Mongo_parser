package sql.tokens.helpers;

import interfaces.ApplicationFramework;
import sql.operators.Comparison;
import sql.operators.RegexConverter;

public class WhereInequality {

    /* Inequality in Where
    *  right can be String ('nesto' or string pattern '^J') or a Query
    *  A > B
    * C like '%A'
    * D in (subquery)
    */

    private String left;
    private Comparison comparison;
    private Object right;                       //String or Query

    public WhereInequality(String s){

        //if it has a subquery
        if(s.contains("(")){
            String subquery = s.substring(s.indexOf("(")+1, s.indexOf(")"));
            System.out.println("SUBQUERY: " + subquery);
            this.right = ApplicationFramework.getInstance().getSql().parseSubqueryToSQLObject(subquery);

            String[] params = s.split(" ");
            this.left = params[0];
            this.comparison = Comparison.getElement(params[1]);

        }

        // if it doesn't have a subquery
        else {
            s += " ";
            String[] params = s.split(" ");
            this.left = params[0];
            this.comparison = Comparison.getElement(params[1]);

            if(comparison.equals(Comparison.$regex))
                this.right = RegexConverter.convertSQLtoMongoRegex(params[2]);
            else
                this.right = params[2];
        }

    }

    @Override
    public String toString() {
        return left + " " + comparison + " " + right;
    }
}
