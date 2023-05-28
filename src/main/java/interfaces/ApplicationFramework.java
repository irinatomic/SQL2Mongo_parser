package interfaces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationFramework {

    protected Gui gui;
    //bice interfejs za MessageGenerator
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

    public void initialise(Gui gui){
        this.gui = gui;
        //bice i za MessageGenerator
    }
}
