package app;

import database.DBController;
import errors.ErrorGeneratorImpl;
import gui.SwingGui;
import gui.table.TableModel;
import interfaces.*;
import lombok.Getter;
import lombok.Setter;
import sql.SQLImplemet;
import validator.ValidatorImpl;

@Getter
@Setter
public class AppCore {

    private static AppCore instance;
    private Gui gui;
    private Database db;
    private SQL sql;
    private Validator validator;
    private ErrorGenerator errorGenerator;
    private TableModel tableModel;

    private AppCore(){
        this.gui = new SwingGui();
        this.db = new DBController();
        this.sql = new SQLImplemet();
        this.validator = new ValidatorImpl();
        this.tableModel = new TableModel();
        this.errorGenerator = new ErrorGeneratorImpl();

        ApplicationFramework appFramework = ApplicationFramework.getInstance();
        appFramework.initialise(gui, db, sql, validator, errorGenerator);
        appFramework.run();
    }

    public static AppCore getInstance(){
        if (instance == null){
            instance = new AppCore();
        }
        return instance;
    }

}
