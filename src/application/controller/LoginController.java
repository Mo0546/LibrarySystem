package application.controller;

import application.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static application.model.Security.getMd5;

public class LoginController implements Initializable {

	//@FXML private BorderPane loginRoot;
	//Stage stage = new Stage();
	@FXML
	private TextField account;
	@FXML
	private PasswordField password;
	@FXML
	public Button btn_login;
	@FXML
	public Button btn_register;

	Stage closeStage;
	//Stage openStage;

	private String tempPassword;
	private String tempAccount;
	private String passwordMd5;
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
			Register register = new Register();
			register.showStage();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	public void setCloseStage() {
		this.closeStage = Main.getStage();
	}

	public Stage getCloseStage() {
		return closeStage;
	}

	/**
	 * 登录
	 *
	 * @throws Exception
	 */

	@FXML
	public void doLogin(ActionEvent event) throws Exception {
		/*
		 * 新开一个线程,012是顺序
		 * */
		Task<Void> task = new Task<Void>() {
			protected Void call() throws Exception {
				//System.out.println("1成功");
				//System.out.println("2 "+tempAccount+" "+tempPassword);
				Platform.runLater(new Runnable() {
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
							e.printStackTrace();
						}
						System.out.println("账号 " + tempAccount + " 密码 " + passwordMd5);
						//System.out.println("2成功");
					}
				});
				return null;
			}
		};
		Task<Void> task1 = new Task<Void>() {
			protected Void call() throws Exception {
				//System.out.println("1成功");
				//System.out.println("2 "+tempAccount+" "+tempPassword);
				Platform.runLater(new Runnable() {
					public void run() {
						setCloseStage();//一定要先设置要关闭的stage，不然会出现线程错误
						closeStage.hide();
						MainReader reader_main = new MainReader();
						reader_main.setAccount(tempAccount);
						//MainLibraryManager libraryManager_main = new MainLibraryManager();
						//MainSystemManager systemManager_main = new MainSystemManager();
						try {
							reader_main.showStage();
							//libraryManager_main.showStage();
							//systemManager_main.showStage();
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println("账号 " + tempAccount + " 密码 " + passwordMd5);
						//System.out.println("2成功");
					}
				});
				return null;
			}
		};
		Task<Void> task2 = new Task<Void>() {
			protected Void call() throws Exception {
				//System.out.println("1成功");
				Platform.runLater(new Runnable() {
					public void run() {
						setCloseStage();//一定要先设置要关闭的stage，不然会出现线程错误
						closeStage.hide();
						//MainReader reader_main = new MainReader();
						MainLibraryManager libraryManager_main = new MainLibraryManager();
						libraryManager_main.setAccount(tempAccount);
						//MainSystemManager systemManager_main = new MainSystemManager();
						try {
							//reader_main.showStage();
							libraryManager_main.showStage();
							//systemManager_main.showStage();
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println("账号 " + tempAccount + " 密码 " + passwordMd5);
						//System.out.println("2成功");
					}
				});
				return null;
			}
		};
		Task<Void> task3 = new Task<Void>() {
			protected Void call() throws Exception {
				//System.out.println("1成功");
				//System.out.println("2 "+tempAccount+" "+tempPassword);
				Platform.runLater(new Runnable() {
					public void run() {
						setCloseStage();//一定要先设置要关闭的stage，不然会出现线程错误
						closeStage.hide();
						//MainReader reader_main = new MainReader();
						//MainLibraryManager libraryManager_main = new MainLibraryManager();
						MainSystemManager systemManager_main = new MainSystemManager();
						systemManager_main.setAccount(tempAccount);
						try {
							//reader_main.showStage();
							//libraryManager_main.showStage();
							systemManager_main.showStage();
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println("账号 " + tempAccount + " 密码 " + passwordMd5);
						//System.out.println("2成功");
					}
				});
				return null;
			}
		};

		tempAccount = account.getText();
		tempPassword = password.getText();
		if (tempAccount.isEmpty() || tempPassword.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("警告");
			alert.setHeaderText("验证错误");
			alert.setContentText("请重新输入账号密码");
			alert.show();
		} else if (tempAccount.equals("0")) {
			new Thread(task).start();
		} else {
			passwordMd5 = getMd5(tempPassword);
			String role = application.dao.ReaderDao.Login(tempAccount, passwordMd5);
			System.out.println(role);
			if (role.equals("U"))
				new Thread(task1).start();
			else if (role.equals("L"))
				new Thread(task2).start();
			else if (role.equals("S"))
				new Thread(task3).start();
			else {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("警告");
				alert.setHeaderText("用户名或密码错误");
				alert.setContentText("请重新输入账号密码");
				alert.show();
			}
				    /*if(JDBCUtil.createDB()==0){
				    System.out.println("0成功 "+"数据库创建成功");
				    }*/
		}
	}

	//判断文本框是否为空
	private boolean isEmpty(String str) {
		// TODO 自动生成的方法存根
		if (str == null) {
			return false;
		}
		return true;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO 自动生成的方法存根

	}

}
