package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SystemManagerController {

	@FXML Label lb_system_reader;
	@FXML Label lb_system_libraryManager;
	@FXML Label lb_system_information;
	
	public void lb_system_readerClick() {
		System.out.println("系统管理员读者管理点击成功");
	}
	public void lb_system_libraryManagerClick() {
		System.out.println("系统管理员图书管理员点击成功");
	}
	public void lb_system_informationClick() {
		System.out.println("系统管理员个人信息点击成功");
	}
}
