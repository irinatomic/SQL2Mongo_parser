package controller;

import gui.view.MainFrame;

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
        MainFrame.getInstance().getInputText().setText("");


    }
}
