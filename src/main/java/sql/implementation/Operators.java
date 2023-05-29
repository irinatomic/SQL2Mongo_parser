package sql.implementation;

//straight conversion from sql to mongo: > -> $gt

public enum Operators {

    $eq("="),       $ne("<>"),
    $gt(">"),       $gte(">="),
    $lt("<"),       $lte("<="),
    $in("in"),      $nin("not in"),
    $min("min"),    $max("max"),
    $sum("sum"),
    $avg("avg");

    private final String operator;

    Operators(String operator) {
        this.operator = operator;
    }

    public static Operators fromString(String input) {
        input = input.toLowerCase();
        for (Operators v : Operators.values()) {
            if (v.operator.equals(input))
                return v;
        }
        throw new IllegalArgumentException("No enum constant with input: " + input);
    }
}
