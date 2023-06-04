package sql.tokens.helpers;

import interfaces.ApplicationFramework;
import lombok.Getter;
import sql.operators.Comparison;
import sql.operators.RegexConverter;

@Getter
public class WhereInequality {

    /* Inequality in Where
    *  right can be String ('nesto' or string pattern '^J') or a Query
    *  A > B
    * C like '%A'
    * D in (subquery)
    */

    private String table;
    private String left;
    private Comparison comparison;
    private Object right;                       //String or Query

    public WhereInequality(String s){
        s = s.toLowerCase();

        //if it has a subquery
        if(s.contains("(") && s.contains("select")){
            String subquery = s.substring(s.indexOf("(")+1, s.indexOf(")"));
            this.right = ApplicationFramework.getInstance().getSql().parseSubqueryToSQLObject(subquery);

            String[] params = s.split(" ");
            this.left = params[0];
            this.comparison = Comparison.getElement(params[1]);
            return;
        }

        // if it doesn't have a subquery

        //special case: salary in (a, b)
        if(s.contains("(")){
            this.right = s.substring(s.indexOf("("), s.indexOf(")")+1);
            this.right = this.right.toString().replace("(", "[");
            this.right = this.right.toString().replace(")", "]");
            this.left = s.split(" ")[0];
            this.comparison = Comparison.getElement(s.split(" ")[1]);
            return;
        }

        s += " ";
        String[] params = s.split(" ");
        if(params[0].contains(".")){
            String fullName = params[0];
            int dotIndex = fullName.indexOf(".");
            this.table = fullName.substring(0, dotIndex);
            this.left = fullName.substring(dotIndex+1);
        } else
            this.left = params[0];
        this.comparison = Comparison.getElement(params[1]);

        if(comparison.equals(Comparison.$regex))
            this.right = RegexConverter.convertSQLtoMongoRegex(params[2]);
        else
            this.right = params[2];

    }

    @Override
    public String toString() {
        return left + " " + comparison + " " + right;
    }
}
