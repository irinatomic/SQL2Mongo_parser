package gui.controller;

import gui.view.MainFrame;
import interfaces.ApplicationFramework;
import java.awt.event.ActionEvent;

public class SubmitAction extends AbstractButtonAction{

    public SubmitAction(){
        putValue(SMALL_ICON, loadIcon("/images/submit.png"));
        putValue(NAME, "Submit Action");
        putValue(SHORT_DESCRIPTION, "Submit Action");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String query = MainFrame.getInstance().getInputText().getText();
        ApplicationFramework.getInstance().getSql().parseQueryToSQLObject(query);
    }
}
