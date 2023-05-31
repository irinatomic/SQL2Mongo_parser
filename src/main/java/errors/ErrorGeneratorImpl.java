package errors;

import gui.view.MainFrame;
import interfaces.ErrorGenerator;

public class ErrorGeneratorImpl implements ErrorGenerator {

    @Override
    public void createErrorMessage(Error error) {

        String message;

        switch (error){
            case NO_MANDATORY_CLAUSES:
                message = "SELECT statement must have SELECT and FROM clauses"; break;
            case GROUP_BY_ERROR:
                message = "All parameters from the SELECT clause (that aren't aggr. functions) must be in the GROUP BY clause"; break;
            case AGGREGATE_FUNCTION_IN_WHERE:
                message = "Aggregate function must go to HAVING clause, not WHERE clause"; break;
            case NO_JOIN_CONDITION:
                message = "JOIN clauses must have a condition for joining the tables (USING or ON)"; break;
            default:
                message = "Syntax or logical error"; break;
        }

        // TO-DO: change to observer
        MainFrame.getInstance().showErrorMessage(message);
    }
}
