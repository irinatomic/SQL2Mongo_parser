package sql.implementation.helpers;

import lombok.Getter;
import sql.operators.Comparison;

@Getter
public class Inequality {

    // A > B
    // sum(A) > B
    // min(A) < max(B)

    private SelectParameter left;
    private Comparison comparison;
    private SelectParameter right;

    public Inequality(String s){
        s += " ";
        String[] params = s.split(" ");
        this.left = new SelectParameter(params[0]);
        this.comparison = Comparison.getElement(params[1]);
        this.right = new SelectParameter(params[2]);
    }

    public Inequality(String left, String comparison, String right) {
        this.left = new SelectParameter(left);
        this.comparison = Comparison.valueOf(comparison);
        this.right = new SelectParameter(right);
    }
}
