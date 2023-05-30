package operators;

public enum Logical {

    $or("or"),
    $and("and");

    private final String op;

    Logical(String op) {
        this.op = op;
    }

    public static Logical getElement(String input) {
        input = input.toLowerCase();
        for (Logical l : Logical.values()) {
            if (l.op.equals(input))
                return l;
        }
        return null;
    }
}
