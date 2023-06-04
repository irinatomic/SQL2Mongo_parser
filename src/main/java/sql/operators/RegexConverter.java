package sql.operators;

import java.util.HashMap;
import java.util.Map;

public class RegexConverter {

    // JSQL parser turns everything after LIKE to lower case letters
    // So we will just preform our Mongo queries case insensitive

    public static String convertSQLtoMongoRegex(String sql){
        sql = sql.replaceAll("\'", "");
        int sqlLen = sql.length();
        String mongo = "/";

        //If it doesn't have any special characters -> match the whole string
        if(!sql.contains("_") && !sql.contains("%")){
            mongo += "^" + sql + "$/i";
            //System.out.println("MONGO: " + mongo);
            return mongo;
        }

        // Find the chars sequence at the beginning
        String beginningChars = "";
        for(int i = 0; i < sqlLen; i++){
            Character curr = sql.charAt(i);
            if(Character.isLetter(curr))
                beginningChars += curr;
            else
                break;
        }

        //Only 'begins with' something
        if(beginningChars.length() == sqlLen-1 && sql.charAt(sqlLen-1) == '%'){
            mongo += '^' + beginningChars + ".*/i";
            //System.out.println("MONGO: " + mongo);
            return mongo;
        }

        //Find the chars sequence at the end
        String endingChars = "";
        for(int i = sqlLen-1; i >= 0; i--){
            Character curr = sql.charAt(i);
            if(Character.isLetter(curr))
                endingChars = curr + endingChars;
            else
                break;
        }

        // Only 'ends with' something
        if(endingChars.length() == sqlLen-1 && sql.charAt(0) == '%'){
            mongo += endingChars + "$/i";
            //System.out.println("MONGO: " + mongo);
            return mongo;
        }

        // Other -> not covering all cases
        Map<Character, String> defaults = new HashMap<>();
        defaults.put('_', ".");
        defaults.put('%', ".*");

        Character first = sql.charAt(0);
        if(Character.isLetter(first))
            mongo += "^";

        for(int i = 0; i < sqlLen; i++){
            Character curr = sql.charAt(i);
            if(Character.isLetter(curr))
                mongo += curr;
            else
                mongo += defaults.get(curr);
        }

        Character last = sql.charAt(sqlLen-1);
        if(Character.isLetter(last))
            mongo += "$";

        mongo += "/i";
        //System.out.println("MONGO: " + mongo);
        return mongo;
    }
}
