package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.util.ResourceBundle;

public class TestController implements Initializable{

	@FXML Button btn_test;
	@FXML
	public void click() {
		System.out.println("测试点击成功");
	}
	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		// TODO 自动生成的方法存根

	}
}
