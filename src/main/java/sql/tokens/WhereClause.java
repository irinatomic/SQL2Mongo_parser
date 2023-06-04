package sql.tokens;

import lombok.Getter;
import sql.tokens.helpers.WhereParameter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WhereClause {

    // A > B and C >= D and E in (subquery)
    // Logical same to HavingClause: left is an Inequality and right can be another subpart of the Where clause
    // Inside the inequality, on the right, we can have a subquery

    private String originalText;
    private List<WhereParameter> params;

    public WhereClause() {
        this.params = new ArrayList<>();
    }

    public void parseQueryToSQLObject(String query) {
        query = query.toLowerCase();
        this.originalText = query;

        query += " ";
        String[] words = query.split(" ");

        //special case - we have a subquery
        if(query.contains("(") && query.contains("select")){
            WhereParameter wp = new WhereParameter(query, "");
            this.params.add(wp);
            return;
        }

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
    }
}
