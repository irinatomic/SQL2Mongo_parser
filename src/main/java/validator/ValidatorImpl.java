package validator;

import interfaces.Validator;
import validator.checks.*;

import java.util.*;

public class ValidatorImpl implements Validator {

    private List<Check> checks;

    public ValidatorImpl(){
        Check c1 = new Check1();
        Check c2 = new Check2();
        Check c3 = new Check3();
        Check c4 = new Check4();

        this.checks = new ArrayList<>();
        checks.addAll(Arrays.asList(c1, c2, c3, c4));
    }

    public void validateQuery(){
        //izvucemo Query: SqlImplement.getQuery
        //prodjemo kroz listu provera i pozovemo funkciju check1.checkRule()
        for (Check c: checks){
            if (!c.checkRule()){
                System.out.println("Izasao iz provere");
                break;
            }
        }
    }
}
