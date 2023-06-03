package sql.tokens.helpers;

import lombok.Getter;
import sql.operators.Logical;

@Getter
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
