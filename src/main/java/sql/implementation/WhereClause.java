package sql.implementation;

import lombok.Getter;
import sql.implementation.helpers.WhereParameter;
import sql.composite.*;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WhereClause extends Token {

    // A > B and C >= D and E in (subquery)
    // Logical same to HavingClause: left is an Inequality and right can be another subpart of the Where clause
    // Inside the inequality, on the right, we can have a subquery

    private String originalString;
    private List<WhereParameter> params;

    public WhereClause(Token parent) {
        super(parent);
        this.params = new ArrayList<>();
    }

    @Override
    public void parseQueryToSQLObject(String query) {
        this.originalString = query;

        query += " ";
        String[] words = query.split(" ");

        int start = 0;
        for(int i = 0; i < words.length; i++){
            String curr = words[i];
            if(curr.equalsIgnoreCase("and") || curr.equalsIgnoreCase("or")){

                String logical = curr;
                String left = "";
                for(int j = start; j < i; j++)
                    left += words[j] + " ";

                start = i+1;

                WhereParameter wp = new WhereParameter(left, logical);
                this.params.add(wp);
            }
        }

        // for the last one
        String left = "";
        for(int j = start; j < words.length; j++)
            left += words[j] + " ";

        WhereParameter wp = new WhereParameter(left, "");
        this.params.add(wp);

        //TEST
        System.out.println("WHERE CLAUSE: ");
        for(WhereParameter wp2 : params)
            System.out.println(wp2);
    }
}
