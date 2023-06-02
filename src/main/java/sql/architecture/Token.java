package sql.architecture;

import lombok.Getter;
import sql.SQLImplemet;

// Token - class for all parts of the SQL query object

@Getter
public abstract class Token extends SQLImplemet {

    private Token parent;

    public Token(Token parent){
        this.parent = parent;
    }

}
