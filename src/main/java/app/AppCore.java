package app;

import database.DBController;
import gui.SwingGui;
import gui.table.TableModel;
import interfaces.*;
import lombok.Getter;
import lombok.Setter;
import sql.SQLImplemet;

@Getter
@Setter
public class AppCore {

    private static AppCore instance;
    private Gui gui;
    private Database db;
    private SQL sql;
    private TableModel tableModel;

    private AppCore(){
        this.gui = new SwingGui();
        this.db = new DBController();
        this.sql = new SQLImplemet();
        this.tableModel = new TableModel();

        ApplicationFramework appFramework = ApplicationFramework.getInstance();
        appFramework.initialise(gui, db, sql);
        appFramework.run();
    }

    public static AppCore getInstance(){
        if (instance == null){
            instance = new AppCore();
        }
        return instance;
    }

}
