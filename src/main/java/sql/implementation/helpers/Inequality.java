package sql.implementation.helpers;

import lombok.Getter;
import operators.Aggregate;
import operators.Comparison;

@Getter
public class Inequality {

    //values of Aggregate fields can be NULL
    // right is an Object because it can be a String (regular case) or a subquery (Where clause)

    private String left;
    private Aggregate aLeft;
    private Comparison comparison;
    private Object right;
    private Aggregate aRight;

    public Inequality(String left, String aLeft, String comparison, Object right, String aRight) {
        this.left = left;
        this.aLeft = Aggregate.valueOf(aLeft);
        this.comparison = Comparison.valueOf(comparison);
        this.right = right;
        this.aRight = Aggregate.valueOf(aRight);
    }
}
