package operators;

import interfaces.Operators;

public enum Aggregate implements Operators {

    $min("min"),
    $max("max"),
    $sum("sum"),
    $avg("avg");

    private final String op;

    Aggregate(String op) {
        this.op = op;
    }

    public Aggregate fromString(String input) {
        input = input.toLowerCase();
        for (Aggregate a : Aggregate.values()) {
            if (a.op.equals(input))
                return a;
        }
        return null;
    }
}
