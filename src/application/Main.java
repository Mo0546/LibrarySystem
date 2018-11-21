package application;
	
import java.io.IOException;

import application.controller.LoginController;
import application.controller.RegisterController;
import application.util.JDBCUtil;
import application.util.MyDiolog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


public final class Main  extends Application implements EventHandler<ActionEvent>{
	/*
	 * 使用Stage设置不同的fxml文件跳转
	 * 再监听不同的控制类来实现按钮事件
	 * 
	 */
	 
	private static Stage primaryStage;

	FXMLLoader loader;
	public static void main(String[] args) throws Exception {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO 自动生成的方法存根
		this.primaryStage = primaryStage;
		primaryStage.setTitle("图书管理系统");
		loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/MainPane.fxml"));
		BorderPane loginPane = (BorderPane)loader.load();
		Scene scene = new Scene(loginPane,1280,720);
		scene.getStylesheets().add(getClass().getResource("view/login.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		if (JDBCUtil.createDB()!=0){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				MyDiolog myDiolog =new MyDiolog(Alert.AlertType.CONFIRMATION,"第一次使用系统，初始化数据库");
				myDiolog.mshow();
			}
		});
		}
	}
	
	//get舞台                                        
	public static Stage getStage() {
		return primaryStage;
	}
	//加载登录界面
	public void hideLoginStage() throws Exception {
		// TODO 自动生成的方法存根
		if(primaryStage != null) {
			primaryStage.hide();
		}
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		// TODO 自动生成的方法存根
	try {
		//showRegister();
	} catch (Exception e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	}
	
}

