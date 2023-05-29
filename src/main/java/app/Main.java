package app;

import gui.view.MainFrame;
import interfaces.*;

public class Main {

    public static void main(String[] args) {
        AppCore appCore = AppCore.getInstance();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setAppCore(appCore);

        //TESTING
        Database db = ApplicationFramework.getInstance().getDb();
        appCore.getTableModel().setRows(db.preformQuery(""));
    }
}
