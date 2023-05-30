package operators;

import interfaces.Operators;

public enum Logical implements Operators{

    $or("or"),
    $and("and");

    private final String op;

    Logical(String op) {
        this.op = op;
    }

    public Logical fromString(String input) {
        input = input.toLowerCase();
        for (Logical l : Logical.values()) {
            if (l.op.equals(input))
                return l;
        }
        return null;
    }
}
