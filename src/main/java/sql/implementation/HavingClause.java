package sql.implementation;

import lombok.Getter;
import sql.composite.Token;
import sql.implementation.helpers.HavingParameter;
import java.util.*;

@Getter
public class HavingClause extends Token {

    /* A and B or C -> list of HavingParams
    * params[0]: A and  (A = "sum(nesto) as N" which is a SelectParameter
    * params[1]: B or
    * params[2]: C null
    *
    * Mongo: $and{A, $or{B, C}}
    */

    private String originalText;
    private List<HavingParameter> params;

    public HavingClause(Token parent) {
        super(parent);
        this.params = new ArrayList<>();
    }

    @Override
    public void parseQueryToSQLObject(String query) {
        this.originalText = query;



        System.out.println(query);
    }
}
