package gui.controller;

import app.AppCore;
import gui.view.MainFrame;
import interfaces.ApplicationFramework;
import interfaces.Database;
import sql.SQLImplemet;
import sql.tokens.Query;

import java.awt.event.ActionEvent;

public class SubmitAction extends AbstractButtonAction{

    public SubmitAction(){
        putValue(SMALL_ICON, loadIcon("/images/submit.png"));
        putValue(NAME, "Submit Action");
        putValue(SHORT_DESCRIPTION, "Submit Action");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = MainFrame.getInstance().getInputText().getText();

        //Parse query
        ApplicationFramework.getInstance().getSql().parseQueryToSQLObject(text);

        //Validate
        //ApplicationFramework.getInstance().getValidator().validateQuery();

        //Adapt query for Mongo
        SQLImplemet sqlImplemet = (SQLImplemet) ApplicationFramework.getInstance().getSql();
        Query query = sqlImplemet.getCurrQuery();
        ApplicationFramework.getInstance().getAdapter().adaptQueryForMongo(query);

        //Query the mongo db
        Database db = ApplicationFramework.getInstance().getDb();
        AppCore.getInstance().getTableModel().setRows(db.preformQuery());
    }
}
