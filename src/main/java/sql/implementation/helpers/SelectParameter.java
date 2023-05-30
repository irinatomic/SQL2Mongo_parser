package sql.implementation.helpers;

import lombok.Getter;
import operators.Aggregate;

@Getter
public class SelectParameter {

    private String name;
    private String alias;
    private Aggregate aggregateFunction;

    public SelectParameter(String text) {
        extractValues(text);
    }

    private void extractValues(String text){

        if(text.equals("*")){
            this.name = "*";
            this.alias = null;
            this.aggregateFunction = null;
            return;
        }

        if(text.contains("(")){
            String af = text.substring(0, text.indexOf("(")).trim();
            this.aggregateFunction = Aggregate.getElement(af);
            this.name = text.substring(text.indexOf("(")+1, text.indexOf(")"));
        } else {
            this.aggregateFunction = null;
            text += " ";
            this.name = text.substring(0, text.indexOf(" "));
        }

        if(text.contains("as"))
            this.alias = text.substring(text.indexOf("as") + 2).trim();
        else if(text.contains("AS"))
            this.alias = text.substring(text.indexOf("AS") + 2).trim();
        else
            this.alias = null;

        //System.out.println("SELECT PAMARETER: " + this.name + "_" + this.alias + "_" + this.aggregateFunction);
    }
}
