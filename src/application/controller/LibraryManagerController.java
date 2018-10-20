package application.controller;

import application.MainLibraryManager;
import application.MainReader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;

public class LibraryManagerController {

	@FXML Label lb_library_book;
	@FXML Label lb_library_bookInformation;
	@FXML Label lb_library_readerInformation;
	@FXML Label lb_library_Information;
	public void lb_library_bookClick(){
		System.out.println("图书管理点击成功");
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				// TODO 自动生成的方法存根
				TreeView reader_tv = new TreeView<>();
				reader_tv.setPrefSize(980, 690);
				reader_tv.setLayoutX(300);
				reader_tv.setLayoutY(125);
				MainLibraryManager.getRoot().getChildren().add(reader_tv);
			}
			
		});
	}
	
	public void lb_library_bookInformationClick(){
		System.out.println("图书信息维护点击成功");
		TreeView reader_tv = new TreeView<>();
		reader_tv.setPrefSize(980, 690);
		reader_tv.setLayoutX(300);
		reader_tv.setLayoutY(125);
		MainLibraryManager.getRoot().getChildren().add(reader_tv);
	}
	public void lb_library_readerInformationClick(){
		System.out.println("读者维护点击成功");
		TreeView reader_tv = new TreeView<>();
		reader_tv.setPrefSize(980, 690);
		reader_tv.setLayoutX(300);
		reader_tv.setLayoutY(125);
		MainLibraryManager.getRoot().getChildren().add(reader_tv);
	}
	public void lb_library_InformationClick(){
		System.out.println("图书管理员个人信息点击成功");
		TreeView reader_tv = new TreeView<>();
		reader_tv.setPrefSize(980, 690);
		reader_tv.setLayoutX(300);
		reader_tv.setLayoutY(125);
		MainLibraryManager.getRoot().getChildren().add(reader_tv);
	}
}
