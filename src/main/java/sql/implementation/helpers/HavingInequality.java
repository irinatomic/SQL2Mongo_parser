package sql.implementation.helpers;

import lombok.Getter;
import sql.operators.Comparison;

@Getter
public class HavingInequality {

    /* Inequality in having
    * A > B
    * sum(A) > B
    * min(A) < max(B)
     */

    private SelectParameter left;
    private Comparison comparison;
    private SelectParameter right;

    public HavingInequality(String s){
        s += " ";
        String[] params = s.split(" ");

        // Making a new select parameter (instead of searching for the matching one) so the code
        // doesn't break before the validation if the user's hasn't put in the SELECT clause
        this.left = new SelectParameter(params[0]);
        this.comparison = Comparison.getElement(params[1]);
        this.right = new SelectParameter(params[2]);
    }

    @Override
    public String toString() {
        return left.toString() + " " + comparison + " " + right.toString();
    }
}
