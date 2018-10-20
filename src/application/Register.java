package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public final class Register extends Application {
	Stage stage = new Stage();
	private static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO 自动生成的方法存根
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("view/RegisterPane.fxml"));
		Scene scene = new Scene(root,1280,720);
		scene.getStylesheets().add(getClass().getResource("view/register.css").toExternalForm());
		primaryStage.setTitle("注册");
		primaryStage.setScene(scene);
		this.primaryStage = primaryStage;
		primaryStage.show();
	}

	public void showStage() throws Exception {
		start(stage);
	}
	public static Stage getStage() {
		return primaryStage;
	}
}
