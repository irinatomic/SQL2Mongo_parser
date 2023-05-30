package sql.implementation.helpers;

import lombok.Getter;

/*
$lookup: {
  from: "table2",
  localField: "fieldTable1",
  foreignField: "fieldTable2",
  as: "aliasFT2"
} */

@Getter
public class JoinClause {

    private String table2;
    private String fieldTable1;
    private String fieldTable2;
    private String aliasFT2;

    public JoinClause(String table2, String fieldTable1, String fieldTable2) {
        this.table2 = table2;
        this.fieldTable1 = fieldTable1;
        this.fieldTable2 = fieldTable2;
        this.aliasFT2 = fieldTable2.split("_")[0];
    }

    @Override
    public String toString() {
        return "Right: " + table2 + " field1: " + fieldTable1 + " field2: " + fieldTable2;
    }
}
