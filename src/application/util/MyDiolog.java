package application.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MyDiolog extends Alert {
    private AlertType alertType;
    private String contentText;
    /**
     * 传入类型，有三种Alert.AlertType.INFORMATION，Warning,Error
     * */
    public MyDiolog(AlertType alertType) {
        super(alertType);
    }

    public MyDiolog(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
    }
    public  MyDiolog(AlertType alertType,String contentText){
        super(alertType,contentText);
    }
    public void mshow(){
        this.showAndWait();
    }
}
