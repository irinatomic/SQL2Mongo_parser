package sql.tokens;

import lombok.Getter;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.*;
import sql.tokens.helpers.JoinClause;

import java.util.*;

/* We will remember the starting table (one we're doing the query against)
*  Every join translates to a $lookup in Mongo -> list of Joins
*  So every join contains:
*  - name of table 2
*  - argument from table 1
*  - argument from table 2
* */

@Getter
public class FromClause {

    private String fromTable;                               //table against which we're doing the query
    private List<JoinClause> joins;

    public FromClause() {
        this.joins = new ArrayList<>();
    }

    public void parseQueryToSQLObject(String query) {
        query = query.toLowerCase();

        try {
            Select select = (Select) CCJSqlParserUtil.parse(query);
            SelectBody selectBody = select.getSelectBody();

            /* Extract Join clauses to our JoinClause
            * Join.rightItem = desna tabela + ' ' + alijas
            * Join.onExpressions[] = sta je unutar on
            * Join.usingColumns[] = sta je unat usinga
            */
            if (selectBody instanceof PlainSelect) {

                PlainSelect plainSelect = (PlainSelect) selectBody;
                String fromTableName = plainSelect.getFromItem().toString();
                if(fromTableName.contains(" "))
                    this.fromTable = fromTableName.split(" ")[0];
                else
                    this.fromTable = fromTableName;
                List<Join> joins = plainSelect.getJoins();

                if(joins != null){
                    for(Join j : joins)
                        convertToJoinClause(j);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void convertToJoinClause(Join j){
        String leftTableAlias = fromTable;
        String leftField = "", rightField = "";

        if(fromTable.contains(" "))
            leftTableAlias = fromTable.substring(fromTable.indexOf(" ")+1);

        // check using or on
        if(!j.getUsingColumns().isEmpty()){
            String using = j.getUsingColumns().toString();
            using = using.replace("[()]", "");
            using = using.replace("[", "").replace("]", "");
            leftField = using;
            rightField = using;

        } else if(!j.getOnExpressions().isEmpty()){
            String on = j.getOnExpressions().toString();
            on = on.replace("[", "").replace("]", "");
            on = on.replaceAll("[()]", "");
            on += "=";
            String[] onParts = on.split("=");
            Arrays.setAll(onParts, i -> onParts[i].trim());

            String lAlias = onParts[0].substring(0, onParts[0].indexOf("."));
            if(leftTableAlias.equals(lAlias)) {
                 leftField = onParts[0].substring(onParts[0].indexOf(".")+1);
                 rightField = onParts[1].substring(onParts[1].indexOf(".")+1);
            } else {
                rightField = onParts[0].substring(onParts[0].indexOf(".")+1);
                leftField = onParts[1].substring(onParts[1].indexOf(".")+1);
            }
        }

        String rightTable = j.getRightItem().toString();
        if(rightTable.contains(" "))
            rightTable = rightTable.substring(0, rightTable.indexOf(" "));

        // create our JoinClause
        JoinClause jc = new JoinClause(rightTable, leftField, rightField);
        this.joins.add(jc);
    }
}
