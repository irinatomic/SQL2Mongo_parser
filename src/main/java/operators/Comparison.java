package operators;

public enum Comparison {

    $eq("="),       $ne("<>"),
    $gt(">"),       $gte(">="),
    $lt("<"),       $lte("<="),
    $in("in"),      $nin("not in"),
    $between("between"),                    //edge case sa mongo strane
    $regex("like");

    private final String op;

    Comparison(String op) {
        this.op = op;
    }

    public static Comparison getElement(String input) {
        input = input.toLowerCase();
        for (Comparison c : Comparison.values()) {
            if (c.op.equals(input))
                return c;
        }
        return null;
    }
}
