package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final  class MainReader extends Application {

	Stage stage = new Stage();
	static AnchorPane root;
	//static ScrollPane changPane;
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO 自动生成的方法存根
		Parent parent = (AnchorPane)FXMLLoader.load(getClass().getResource("view/MainReaderPane.fxml"));
		root = (AnchorPane)parent;
		//查找id来更新ui
		//changPane = (ScrollPane) parent.lookup("#scl_pane");
		Scene scene = new Scene(root,1280,720);
		scene.getStylesheets().add(getClass().getResource("view/background.css").toExternalForm());
		primaryStage.setTitle("读者中心");
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
