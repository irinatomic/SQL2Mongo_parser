package sql.tokens;

import lombok.Getter;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;

@Getter
public class Query {

    private boolean hasParent;
    private String text;
    private SelectClause selectClause;
    private FromClause fromClause;
    private WhereClause whereClause;
    private HavingClause havingClause;
    private GroupByClause groupByClause;
    private OrderByClause orderByClause;

    public Query(boolean hasParent){
        this.hasParent = hasParent;
    }

    public void parseQueryToSQLObject(String query) {
        this.text = query;

        /*  JSQL works nicely for our logic except for the FROM clause
        because it reterns only the top level value, the joins have to be
        searched for recursively (double work for our logic to later unpack
        it into our own Join objects) */

        try {
            query = query.toLowerCase();
            Statement statement = CCJSqlParserUtil.parse(query);

            if (statement instanceof Select) {
                Select selectStatement = (Select) statement;

                SelectBody selectBody = selectStatement.getSelectBody();

                // Extract and print the clauses
                if (selectBody instanceof PlainSelect) {
                    PlainSelect plainSelect = (PlainSelect) selectBody;

                    // Select clause
                    if(!plainSelect.getSelectItems().isEmpty()){
                        this.selectClause = new SelectClause();
                        this.selectClause.parseQueryToSQLObject(plainSelect.getSelectItems().toString());
                    }

                    // From clause -> whole logic is in FromClause
                    if(plainSelect.getFromItem() != null){
                        this.fromClause = new FromClause();
                        this.fromClause.parseQueryToSQLObject(query);
                    }

                    // Where clause
                    if (plainSelect.getWhere() != null) {
                        this.whereClause = new WhereClause();
                        this.whereClause.parseQueryToSQLObject(plainSelect.getWhere().toString());
                    }

                    // Group By clause
                    if (plainSelect.getGroupBy() != null) {
                        this.groupByClause = new GroupByClause();
                        this.groupByClause.parseQueryToSQLObject(plainSelect.getGroupBy().toString());
                    }

                    // Having clause
                    if (plainSelect.getHaving() != null) {
                        this.havingClause = new HavingClause();
                        this.havingClause.parseQueryToSQLObject(plainSelect.getHaving().toString());
                    }

                    // Order By clause
                    if (plainSelect.getOrderByElements() != null) {
                        this.orderByClause = new OrderByClause();
                        this.orderByClause.parseQueryToSQLObject(plainSelect.getOrderByElements().toString());
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return text;
    }
}
