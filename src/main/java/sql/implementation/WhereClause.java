package sql.implementation;

import lombok.Getter;
import operators.Logical;
import sql.composite.Token;
import sql.composite.TokenComposite;
import sql.implementation.helpers.Inequality;

@Getter
public class WhereClause extends TokenComposite {

    // A > B and C >= D and E in (subquery)
    // Logical same to HavingClause: left is an Inequality and right can be another subpart of the Where clause
    // Inside the inequality, on the right, we can have a subquery

    private Inequality left;
    private Logical logicalCentre;
    private Object right;

    public WhereClause(Token parent) {
        super(parent);
    }
}
