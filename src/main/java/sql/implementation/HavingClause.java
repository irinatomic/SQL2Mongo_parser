package sql.implementation;

import lombok.Getter;
import sql.composite.Token;

@Getter
public class HavingClause extends Token {

    //left and right can be another OneComparison or a String when we're
    // at the smallest comparison like sum(A) > B of "sum(A) > B and avg(C) > D"
    Operators opLeft;
    Object left;
    Operators opCenter;
    Operators opRight;
    Object right;

    public HavingClause(Token parent) {
        super(parent);
    }
}
