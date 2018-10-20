package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainSystemManager extends Application{

	Stage stage = new Stage();
	static AnchorPane root;
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO 自动生成的方法存根
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("view/MainSystemManagerPane.fxml"));
		Scene scene = new Scene(root,1280,720);
		scene.getStylesheets().add(getClass().getResource("view/background.css").toExternalForm());
		primaryStage.setTitle("系统管理员中心");
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
