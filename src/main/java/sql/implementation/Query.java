package sql.implementation;

import lombok.Getter;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import sql.composite.Token;
import sql.composite.TokenComposite;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Query extends TokenComposite {

    public Query(Token parent) {
        super(parent);
    }

    @Override
    public void parseQueryToSQLObject(String query) {

        try {
            // Parse the SQL query
            Statement statement = CCJSqlParserUtil.parse(query);

            // Check if the statement is a Select statement
            if (statement instanceof Select) {
                Select selectStatement = (Select) statement;

                // Get the select body
                PlainSelect plainSelect = (PlainSelect) selectStatement.getSelectBody();

                // Get the select items
                List<SelectItem> selectItems = plainSelect.getSelectItems();

                // Extract and print the select clause
                StringBuilder selectClause = new StringBuilder();
                for (SelectItem selectItem : selectItems) {
                    selectClause.append(selectItem.toString()).append(", ");
                }
                // Remove the trailing comma and space
                selectClause.setLength(selectClause.length() - 2);

                System.out.println("SELECT clause: " + selectClause);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Define regular expressions for each clause
        // Pretpostavka da su po redu navedene naredbe
        String selectRegex = "\\bSELECT\\b(.*?)\\bFROM\\b";
        String fromRegex = "\\bFROM\\b(.*?)\\bWHERE\\b";
        String whereRegex = "\\bWHERE\\b(.*?)\\bGROUP BY\\b";
        String groupByRegex = "\\bGROUP BY\\b(.*?)\\bHAVING\\b";
        String havingRegex = "\\bHAVING\\b(.*?)\\bORDER BY\\b";
        String orderByRegex = "\\bORDER BY\\b(.*)$";

        // Retrieve substrings for each clause
        String selectClause = extractClause(query, selectRegex);
        String fromClause = extractClause(query, fromRegex);
        String whereClause = extractClause(query, whereRegex);
        String groupByClause = extractClause(query, groupByRegex);
        String havingClause = extractClause(query, havingRegex);
        String orderByClause = extractClause(query, orderByRegex);

        //print
//        System.out.println("SELECT clause: " + selectClause.trim());
//        System.out.println("FROM clause: " + fromClause.trim());
//        System.out.println("WHERE clause: " + whereClause.trim());
//        System.out.println("GROUP BY clause: " + groupByClause.trim());
//        System.out.println("HAVING clause: " + havingClause.trim());
//        System.out.println("ORDER BY clause: " + orderByClause.trim());

    }

    private static String extractClause(String sqlQuery, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        String copy = new String(sqlQuery);
        Matcher matcher = pattern.matcher(copy);

        if (matcher.find())
            return matcher.group(1);
        return "";
    }
}
