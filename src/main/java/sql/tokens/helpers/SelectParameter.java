package sql.tokens.helpers;

import lombok.Getter;
import sql.operators.Aggregate;

@Getter
public class SelectParameter {

    private String originalText;
    private String table;
    private String name;
    private String alias;
    private Aggregate aggregateFunction;

    public SelectParameter(String text) {
        extractValues(text);
    }

    private void extractValues(String text){
        this.originalText = text;

        if(text.equals("*")){
            this.name = "*";
            this.table = null;
            this.alias = null;
            this.aggregateFunction = null;
            return;
        }

        String fullName;
        if(text.contains("(")){
            String af = text.substring(0, text.indexOf("(")).trim();
            this.aggregateFunction = Aggregate.getElement(af);
            fullName = text.substring(text.indexOf("(")+1, text.indexOf(")"));
        } else {
            this.aggregateFunction = null;
            text += " ";
            fullName = text.substring(0, text.indexOf(" "));
        }

        if(fullName.contains(".")){
            int dotIndex = fullName.indexOf(".");
            this.table = fullName.substring(0, dotIndex);
            this.name = fullName.substring(dotIndex+1);
        } else
            this.name = fullName;

        if(text.contains("as"))
            this.alias = text.substring(text.indexOf("as") + 2).trim();
        else if(text.contains("AS"))
            this.alias = text.substring(text.indexOf("AS") + 2).trim();
        else
            this.alias = null;

        //System.out.println("SELECT PAMARETER: " + this.name + "_" + this.alias + "_" + this.aggregateFunction);
    }

    @Override
    public String toString() {
        String s = originalText + " ";
        return s.split(" ")[0];
    }
}
