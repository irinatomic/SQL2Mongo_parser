package gui.controller;

import lombok.Getter;

@Getter
public class ActionManager {

    private SubmitAction submitAction;

    public ActionManager(){ initialiseActions();}

    private void initialiseActions(){
        submitAction = new SubmitAction();
    }
}
