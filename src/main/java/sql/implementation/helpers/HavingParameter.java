package sql.implementation.helpers;

import lombok.Getter;
import sql.operators.Logical;

@Getter
public class HavingParameter {

    private HavingInequality left;
    private Logical logical;

    public HavingParameter(String left, String logical) {
        this.left = new HavingInequality(left);
        this.logical = Logical.getElement(logical);
    }

    @Override
    public String toString() {
        return "HAVING PARAM [" + left.toString() + "] [" + logical + "]";
    }
}
