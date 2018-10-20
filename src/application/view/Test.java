package application.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Test extends Application{
	@Override
	public void start(Stage openStage) throws Exception {
		// TODO 自动生成的方法存根
		
		AnchorPane textPane;
		textPane = (AnchorPane)FXMLLoader.load(getClass().getResource("TextWindow.fxml"));
		Scene newScene = new Scene(textPane,1280,720);
		openStage.setScene(newScene);
		openStage.setTitle("测试窗口");
		openStage.show();
	}
	public static void main(String[] args) throws Exception {
		
		launch(args);
	}
}
