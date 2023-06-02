package sql.tokens;

import lombok.Getter;
import sql.tokens.helpers.HavingParameter;
import java.util.*;

@Getter
public class HavingClause {

    /* A and B or C -> list of HavingParams
    * params[0]: A and  (A = "sum(nesto) as N" which is a SelectParameter
    * params[1]: B or
    * params[2]: C null
    *
    * Mongo: $and{A, $or{B, C}}
    */

    private String originalText;
    private List<HavingParameter> params;

    public HavingClause() {
        this.params = new ArrayList<>();
    }

    public void parseQueryToSQLObject(String query) {
        this.originalText = query;

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

                HavingParameter hp = new HavingParameter(left, logical);
                this.params.add(hp);
            }
        }

        // for the last one
        String left = "";
        for(int j = start; j < words.length; j++)
            left += words[j] + " ";

        HavingParameter hp = new HavingParameter(left, "");
        this.params.add(hp);

        //TEST
//        System.out.println("HAVING CLAUSE: ");
//        for(HavingParameter hp2 : params)
//            System.out.println(hp2);
    }
}
