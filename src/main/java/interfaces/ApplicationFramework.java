package interfaces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationFramework {

    protected Gui gui;
    protected Database db;
    protected SQL sql;
    protected Validator validator;
    protected ErrorGenerator errorGenerator;
    private static ApplicationFramework instance;

    private ApplicationFramework(){ }

    public static ApplicationFramework getInstance(){
        if(instance == null){
            instance = new ApplicationFramework();
        }
        return instance;
    }

    public void run(){
        this.gui.start();
    }

    public void initialise(Gui gui, Database db, SQL sql, Validator validator, ErrorGenerator eg){
        this.gui = gui;
        this.db = db;
        this.sql = sql;
        this.validator = validator;
        this.errorGenerator = eg;
        //bice i za MessageGenerator
    }
}
