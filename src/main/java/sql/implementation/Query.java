package sql.implementation;

import lombok.Getter;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import sql.composite.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Query extends TokenComposite {

    public Query(Token parent) {
        super(parent);
    }

    @Override
    public void parseQueryToSQLObject(String query) {

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
                        this.addChild(sc);
                    }

                    // From clause -> custom
                    extractFromClause(query);

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

    private void extractFromClause(String query){

        query = query.toLowerCase();
        // Hack: am unable to create a regex to match the end of string
        // but it matches the where so we add to a copy of the query string
        query += " where";

        String patternS = "\\bfrom\\b(.*?)\\b(?:where\\b|$)";
        Pattern pattern = Pattern.compile(patternS, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);

        // Find the matching substring
        if (matcher.find()) {
            String extractedString = matcher.group(1).trim();
            FromClause fc = new FromClause(this);
            fc.parseQueryToSQLObject(extractedString.trim());
            this.addChild(fc);
        }

    }

}
