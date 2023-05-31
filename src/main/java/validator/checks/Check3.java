package validator.checks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import errors.Error;
import interfaces.ApplicationFramework;
import sql.SQLImplemet;
import sql.composite.Token;
import sql.implementation.*;

public class Check3 extends Check {

    @Override
    public boolean checkRule() {
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        Query query = sqlImplemet.getCurrQuery();
        List<Token> clauses = query.getChildren();
        WhereClause whereClause;
        //System.out.println("111111");
        List<String> aggregates = new ArrayList<>();
        aggregates.addAll(Arrays.asList("min", "max", "sum", "avg", "upper", "lower"));


        for (Token t: clauses){
            if (t instanceof WhereClause){
                whereClause = (WhereClause) t;
                String whereOriginalText = whereClause.getOriginalString();
                System.out.println(whereOriginalText);

                String input = "max(red)";

                String[] partsA = input.split("[()]");
                System.out.println(partsA[0]);

                String[] parts = whereOriginalText.split(" ");
                for (String s: parts){
                    String[] secondParts = s.split("[()]");
                    if (secondParts.length > 1 && aggregates.contains(secondParts[0])){
                        ApplicationFramework.getInstance().getErrorGenerator().createErrorMessage(Error.AGGREGATE_FUNCTION_IN_WHERE);
                        return false;
                    }
                }


                for (String part : partsA) {
                    System.out.println(part);
                }
            }
        }

        
        
        return true;
    }
}