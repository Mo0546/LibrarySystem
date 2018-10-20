package application.controller;

import java.io.IOException;

import application.MainReader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ReaderController {

	@FXML AnchorPane reader_library;
	@FXML AnchorPane reader_information;
	@FXML AnchorPane reader_borrowPane;
	@FXML Label reader_test;
	@FXML TextField reader_tf_lookup;
	//@FXML ListView<String> reader_lv;
	TreeView reader_tv = new TreeView<>();
	ObservableList<String> strList = FXCollections.observableArrayList("红色","黄色","绿色");
	ListView reader_lv = new ListView<>();
	TableView reader_tbv = new TableView<>();
	public void lb_reader_libraryClick() {
		//System.out.println("读者图书馆点击成功");
		/*if(reader_tf_lookup.getClip() != null) {
			String str = reader_tf_lookup.getText();
			System.out.printf("str: "+str);
		};*///还是老毛病
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
		//reader_lv.setCellFactory(TextFieldListCell<T>.forListView());
		reader_lv.setCellFactory(ComboBoxListCell.forListView());
		reader_lv.setFixedCellSize(50);
		reader_lv.setEditable(true);
		reader_lv.setPrefSize(1280, 690);
		reader_lv.autosize();
		reader_lv.setLayoutX(300);
		reader_lv.setLayoutY(125);
		MainReader.getRoot().getChildren().add(reader_lv);
		}
			});
		
	}
	public void lb_reader_borrowClick() throws Exception {
		//System.out.println("读者我的借阅点击成功");
		//更换页面右栏页面
		//线程错误
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				//AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("view/TestWindow.fxml"));
				//reader_borrowPane.getChildren().add(pane);
				//Label lb_test = new Label("列表视图添加");
				//ObservableList<String> strList = FXCollections.observableArrayList("红色","黄色","绿色");
				TableColumn bookNameCol = new TableColumn("借阅书籍");
				TableColumn publishCol = new TableColumn("出版商");
				TableColumn borrowCol = new TableColumn("应还日期");
				TableColumn backdateCol = new TableColumn("归还日期");
				   
				//测试
				///TableColumn test = new TableColumn("测试");
				reader_tbv.getColumns().addAll(bookNameCol, publishCol, borrowCol,backdateCol);
				reader_tbv.setPrefSize(1280, 690);
				reader_tbv.setLayoutX(300);
				reader_tbv.setLayoutY(125);
				reader_tbv.autosize();
				MainReader.getRoot().getChildren().add(reader_tbv);
				//reader_test.setText("更新成功");
			}
			
		});
	}
	public void lb_reader_informationClick() {
		//System.out.println("读者个人信息点击成功");
		reader_tv.setPrefSize(1280, 720);
		reader_tv.setLayoutX(300); 
		reader_tv.setLayoutY(20);
		reader_tv.autosize();
		MainReader.getRoot().getChildren().add(reader_tv);
		
	}
}
