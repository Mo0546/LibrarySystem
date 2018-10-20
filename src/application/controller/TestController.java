package application.controller;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class TestController implements Initializable{

	@FXML Button btn_test;
	public void click() {
		System.out.println("测试点击成功");
	}
	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		// TODO 自动生成的方法存根
		
	}
}
