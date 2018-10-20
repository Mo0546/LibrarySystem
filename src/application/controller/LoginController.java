package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import application.Main;
import application.MainLibraryManager;
import application.MainReader;
import application.MainSystemManager;
import application.Register;
import application.model.Reader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController implements Initializable{

	//@FXML private BorderPane loginRoot;
	//Stage stage = new Stage();
	@FXML private TextField account;
	@FXML private PasswordField password;
	@FXML public Button btn_login;
	@FXML public Button btn_register;
	
	Stage closeStage;
	//Stage openStage;
	
	private String tempPassword;
	private String tempAccount;
	/*
	 * 注册 设置跳转
	 * 
	 */
	
	public void btn_registerClick() throws Exception {
		try {
			
			System.out.println("注册点击成功");
			setCloseStage();//先设置
			//关闭primaryStage
			closeStage.hide();
			//隐藏上一个stage
			//closeStage= (Stage)btn_register.getScene().getWindow();
			//closeStage.hide();
			/*Test test = new Test();
			test.showStage();*/
			Register register = new Register();
			register.showStage();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	
	public void setCloseStage(){
		this.closeStage= Main.getStage();
	}
	public Stage getCloseStage() {
		// TODO 自动生成的方法存根
		return closeStage;
	}
	/**
	 * 登录
	 * @throws Exception 
	 */

	@FXML 
	public void doLogin(ActionEvent event) throws Exception {
				// TODO 自动生成的方法存根
		/*
		 * 新开一个线程,012是顺序
		 * */
		Task<Void> task = new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						// TODO 自动生成的方法存根
						System.out.println("1成功");
						//System.out.println("2 "+tempAccount+" "+tempPassword);
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								setCloseStage();//一定要先设置要关闭的stage，不然会出现线程错误
								closeStage.hide();
								MainReader reader_main = new MainReader();
								MainLibraryManager libraryManager_main = new MainLibraryManager();
								MainSystemManager systemManager_main = new MainSystemManager();
								try {
									reader_main.showStage();
									libraryManager_main.showStage();
									systemManager_main.showStage();
								} catch (Exception e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								}
								System.out.println("账号 " +tempAccount +" 密码 "+tempPassword);
								System.out.println("2成功");
							}
						});
						return null;	
					}
				};
				tempAccount = account.getText();
				tempPassword = password.getText();
				System.out.println("账号 " +tempAccount +" 密码 "+tempPassword);
				if(tempAccount.isEmpty()||tempPassword.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("警告");
					alert.setHeaderText("验证错误");
					alert.setContentText("请重新输入账号密码");
					alert.show();
				}else {
				new Thread(task).start();}
				System.out.println("0成功 ");
				}
				/*if(isEmpty(tempAccount)||isEmpty(tempPassword)) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("验证错误");
					alert.setContentText("请重新输入账号密码");
				}else {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("账号或者密码错误");
					alert.setContentText("请重新输入账号密码");
					account.clear();
					password.clear();
				}}*/
	//判断文本框是否为空
	private boolean isEmpty(String str) {
		// TODO 自动生成的方法存根
        if(str==null){
            return false;
        }
        
        return true;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO 自动生成的方法存根
	}

}
