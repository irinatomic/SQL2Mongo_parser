package sql.implementation;

import lombok.Getter;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import sql.architecture.*;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Query extends Token {

    private String text;
    private List<Token> clauses;

    public Query(Token parent) {
        super(parent);
        clauses = new ArrayList<>();
    }

    @Override
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
                        SelectClause sc = new SelectClause(this);
                        sc.parseQueryToSQLObject(plainSelect.getSelectItems().toString());
                        clauses.add(sc);
                    }

                    // From clause -> whole logic is in FromClause
                    if(plainSelect.getFromItem() != null){
                        FromClause fc = new FromClause(this);
                        fc.parseQueryToSQLObject(query);
                        clauses.add(fc);
                    }

                    // Where clause
                    if (plainSelect.getWhere() != null) {
                        WhereClause wc = new WhereClause(this);
                        wc.parseQueryToSQLObject(plainSelect.getWhere().toString());
                        clauses.add(wc);
                    }

                    // Group By clause
                    if (plainSelect.getGroupBy() != null) {
                        GroupByClause gbc = new GroupByClause(this);
                        gbc.parseQueryToSQLObject(plainSelect.getGroupBy().toString());
                        clauses.add(gbc);
                    }

                    // Having clause
                    if (plainSelect.getHaving() != null) {
                        HavingClause hc = new HavingClause(this);
                        hc.parseQueryToSQLObject(plainSelect.getHaving().toString());
                        clauses.add(hc);
                    }

                    // Order By clause
                    if (plainSelect.getOrderByElements() != null) {
                        OrderByClause obc = new OrderByClause(this);
                        obc.parseQueryToSQLObject(plainSelect.getOrderByElements().toString());
                        clauses.add(obc);
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
