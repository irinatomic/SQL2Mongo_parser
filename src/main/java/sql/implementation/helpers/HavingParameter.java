package sql.implementation.helpers;

import lombok.Getter;
import sql.operators.Logical;

@Getter
public class HavingParameter {

    private Inequality left;
    private Logical logical;

    public HavingParameter(String left, String logical) {
        this.left = new Inequality(left);
        this.logical = Logical.getElement(logical);
    }


}