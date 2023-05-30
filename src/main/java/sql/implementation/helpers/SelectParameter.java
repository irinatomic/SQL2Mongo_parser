package sql.implementation.helpers;

import lombok.Getter;

@Getter
public class SelectParameter {

    private String name;
    private String alias;
    private String aggregateFunction;

    public SelectParameter(String name, String alias, String aggregateFunction) {
        this.name = name;
        this.alias = alias;
        this.aggregateFunction = aggregateFunction;
    }
}
