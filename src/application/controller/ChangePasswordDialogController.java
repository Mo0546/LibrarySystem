package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordDialogController extends TextInputDialog implements Initializable{
    @FXML AnchorPane pane;

    public ChangePasswordDialogController(){ }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("****diolog***初始化");
        this.getDialogPane().setContent(pane);
        this.setTitle("输入更新的密码");
            //this.changPasswordPane = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/ChangePasswordPane.fxml"));
    }
}
