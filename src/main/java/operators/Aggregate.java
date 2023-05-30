package operators;

public enum Aggregate  {

    $min("min"),
    $max("max"),
    $sum("sum"),
    $avg("avg");

    private final String op;

    Aggregate(String op) {
        this.op = op;
    }

    public static Aggregate getElement(String input) {
        for (Aggregate a : Aggregate.values()) {
            if (a.op.equalsIgnoreCase(input))
                return a;
        }
        return null;
    }
}
