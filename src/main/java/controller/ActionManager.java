package controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionManager {

    private SubmitAction submitAction;

    public ActionManager(){ initialiseActions();}

    private void initialiseActions(){
        submitAction = new SubmitAction();
    }
}
