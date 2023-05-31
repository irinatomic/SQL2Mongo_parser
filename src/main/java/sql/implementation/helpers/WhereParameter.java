package sql.implementation.helpers;

import sql.operators.Logical;

public class WhereParameter {

    private WhereInequality left;
    private Logical logical;

    public WhereParameter(String left, String logical) {
        this.left = new WhereInequality(left);
        this.logical = Logical.getElement(logical);
    }

    @Override
    public String toString() {
        return "WHERE PARAM  [" + left.toString() + "] [" + logical + "]";
    }
}
