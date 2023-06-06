package app;

import adapter.AdapterImpl;
import database.DBController;
import errors.ErrorGeneratorImpl;
import gui.SwingGui;
import gui.table.TableModel;
import interfaces.*;
import lombok.Getter;
import lombok.Setter;
import sql.SQLImplemet;
import sql.tokens.Query;
import validator.ValidatorImpl;

@Getter
@Setter
public class AppCore {

    private static AppCore instance;
    private Gui gui;
    private Database db;
    private SQL sql;
    private Validator validator;
    private Adapter adapter;
    private ErrorGenerator errorGenerator;
    private TableModel tableModel;

    private AppCore(){
        this.gui = new SwingGui();
        this.db = new DBController();
        this.sql = new SQLImplemet();
        this.validator = new ValidatorImpl();
        this.tableModel = new TableModel();
        this.adapter = new AdapterImpl();
        this.errorGenerator = new ErrorGeneratorImpl();

        ApplicationFramework appFramework = ApplicationFramework.getInstance();
        appFramework.initialise(gui, db, sql, validator, adapter, errorGenerator);
        appFramework.run();
    }

    public static AppCore getInstance(){
        if (instance == null){
            instance = new AppCore();
        }
        return instance;
    }

    public void beginPipeline(String text){

        //Parse query
        ApplicationFramework.getInstance().getSql().parseQueryToSQLObject(text);

        //Validate
        ApplicationFramework.getInstance().getValidator().validateQuery();

        //Adapt query for Mongo
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        Query query = sqlImplemet.getCurrQuery();
        ApplicationFramework.getInstance().getAdapter().adaptQueryForMongo(query);

        //Query the mongo db
        Database db = ApplicationFramework.getInstance().getDb();
        AppCore.getInstance().getTableModel().setRows(db.preformQuery());
    }

}
