package sql.implementation;

import lombok.Getter;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import sql.composite.*;

@Getter
public class Query extends TokenComposite {

    public Query(Token parent) {
        super(parent);
    }

    @Override
    public void parseQueryToSQLObject(String query) {

        try {
            Statement statement = CCJSqlParserUtil.parse(query);

            if (statement instanceof Select) {
                Select selectStatement = (Select) statement;

                SelectBody selectBody = selectStatement.getSelectBody();

                // Extract and print the clauses
                if (selectBody instanceof PlainSelect) {
                    PlainSelect plainSelect = (PlainSelect) selectBody;

                    // Select clause
                    System.out.println("SELECT clause: " + plainSelect.getSelectItems());

                    // From clause
                    System.out.println("FROM clause: " + plainSelect.getFromItem());

                    // Where clause
                    if (plainSelect.getWhere() != null) {
                        System.out.println("WHERE clause: " + plainSelect.getWhere());
                    }

                    // Group By clause
                    if (plainSelect.getGroupBy() != null) {
                        System.out.println("GROUP BY clause: " + plainSelect.getGroupBy());
                    }

                    // Having clause
                    if (plainSelect.getHaving() != null) {
                        System.out.println("HAVING clause: " + plainSelect.getHaving());
                    }

                    // Order By clause
                    if (plainSelect.getOrderByElements() != null) {
                        System.out.println("ORDER BY clause: " + plainSelect.getOrderByElements());
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
