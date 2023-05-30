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
                        this.addChild(sc);
                    }

                    if(plainSelect.getFromItem() != null){
                        FromClause fc = new FromClause(this);
                        fc.parseQueryToSQLObject(plainSelect.getFromItem().toString());
                        this.addChild(fc);
                    }

                    // Where clause
                    if (plainSelect.getWhere() != null) {
                        WhereClause wc = new WhereClause(this);
                        wc.parseQueryToSQLObject(plainSelect.getWhere().toString());
                        this.addChild(wc);
                    }

                    // Group By clause
                    if (plainSelect.getGroupBy() != null) {
                        GroupByClause gbc = new GroupByClause(this);
                        gbc.parseQueryToSQLObject(plainSelect.getGroupBy().toString());
                        this.addChild(gbc);
                    }

                    // Having clause
                    if (plainSelect.getHaving() != null) {
                        HavingClause hc = new HavingClause(this);
                        hc.parseQueryToSQLObject(plainSelect.getHaving().toString());
                        this.addChild(hc);
                    }

                    // Order By clause
                    if (plainSelect.getOrderByElements() != null) {
                        OrderByClause obc = new OrderByClause(this);
                        obc.parseQueryToSQLObject(plainSelect.getOrderByElements().toString());
                        this.addChild(obc);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
