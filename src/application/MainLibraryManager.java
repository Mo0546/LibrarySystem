package application;

import application.controller.LibraryManagerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class MainLibraryManager extends Application{
	Stage stage = new Stage();
	static AnchorPane root;
	String account;
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO 自动生成的方法存根
		FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainLibraryManagerPane.fxml"));
		Parent parent = (AnchorPane)loader.load();
		LibraryManagerController libraryManagerController = loader.getController();
		libraryManagerController.init(account);
		root =(AnchorPane)parent;
		Scene scene = new Scene(root,1280,720);
		scene.getStylesheets().add(getClass().getResource("view/background.css").toExternalForm());
		primaryStage.setTitle("图书管理员中心");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void showStage() throws Exception {
		start(stage);
	}
	public static Pane getRoot() {
		return (Pane)root;
	}
}
