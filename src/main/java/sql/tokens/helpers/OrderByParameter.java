package sql.tokens.helpers;

import lombok.Getter;

@Getter
public class OrderByParameter {

    private String table;
    private String name;
    private Integer order;              //int: 1 (ASC) or -1 (DESC)

    public OrderByParameter(String s){
        s += " ";
        String[] sParts = s.split(" ");

        String fullName = sParts[0];
        if(fullName.contains(".")){
            fullName += ".";
            this.table = fullName.split("\\.")[0];
            this.name = fullName.split("\\.")[1];
        } else
            this.name = fullName;

        this.order = 1;
        if(sParts.length == 2 && sParts[1].equalsIgnoreCase("DESC"))
            this.order = -1;
    }
}
