package sql.implementation;

import lombok.Getter;
import operators.*;
import sql.composite.Token;
import sql.implementation.helpers.Inequality;

@Getter
public class HavingClause extends Token {

    // Example: "sum(A) > B and avg(C) > D"
    // first half is the Inequality: sum(A) > B
    // second half is another HavingClause where left is the inequality and right is NULL
    // recursion until the right is NULL

    private Inequality left;
    private Logical logicalCentre;
    private Object right;

    public HavingClause(Token parent) {
        super(parent);
    }

    @Override
    public void parseQueryToSQLObject(String query) {

    }
}
