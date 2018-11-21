package application.controller;

import application.Main;
import application.Register;
import application.model.Reader;
import application.model.Security;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterController {

    @FXML
    Button btn_return;
    @FXML
    Button btn_register;
    @FXML
    RadioButton rtn_reader;
    @FXML
    RadioButton rtn_libraryManager;
    @FXML
    RadioButton rtn_systemManager;
    @FXML
    TextField tf_name;
    @FXML
    TextField tf_account;
    @FXML
    TextField tf_password;
    @FXML
    TextField tf_passwordAgain;
    @FXML
    TextField tf_phoneNumber;
    //设置数组用来保存textfield的内容
    List<String> list;
    Boolean checkNumber;
    //返回按钮事件

    Reader reader = new Reader();

    @FXML
    public void btn_returnClick() {
        //System.out.println("返回键点击成功");
        Main.getStage().show();
        Register.getStage().hide();
    }


    public void btn_registerClick() {
        //System.out.println("注册页注册成功");
        System.out.println("注册页textField的值 ");
        getRegisterData();
        if (check()) {
            Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("注册成功！");
            alert.setHeaderText(null);
            if (rtn_reader.isSelected()) {
                int i = application.dao.ReaderDao.Register2(list.get(1), Security.getMd5(list.get(2)),"U", list.get(0), list.get(4));
                alert.setContentText("你注册读者成功");
            }
            else if (rtn_libraryManager.isSelected()) {
                int i = application.dao.ReaderDao.Register2(list.get(1), Security.getMd5(list.get(2)),"L", list.get(0), list.get(4));
                alert.setContentText("你注册图书管理员成功");
            }
            else if (rtn_systemManager.isSelected()) {
                int i = application.dao.ReaderDao.Register2(list.get(1), Security.getMd5(list.get(2)),"S", list.get(0), list.get(4));
                alert.setContentText("你注册系统管理员成功");
            }
            else {
                alert.setTitle("注册失败");
                alert.setContentText("请选择一种注册类型");
            }

            alert.showAndWait();
        } else {
            allClear();//清空文本框
            Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
            if (!isChineseWord(list.get(0))) {
                alert.setTitle("失败！");
                alert.setHeaderText(null);
                alert.setContentText("请输入正确的中文名字");
                alert.showAndWait();
            } else if (!isDegital(list.get(1))) {
                alert.setTitle("失败！");
                alert.setHeaderText(null);
                alert.setContentText("请输入正确的数字型的学号或学工号");
                alert.showAndWait();

            } /*else if (!isPassword(list.get(2))) {
                alert.setTitle("失败！");
                alert.setHeaderText(null);
                alert.setContentText("请输入字母+数字型的密码");
                alert.showAndWait();

            } else if (!isPassword(list.get(3))) {
                alert.setTitle("失败！");
                alert.setHeaderText(null);
                alert.setContentText("请输入字母+数字型的确认密码");
                alert.showAndWait();

            } */
            else if (!isConsistent(list.get(2), list.get(3))) {
                alert.setTitle("失败！");
                alert.setHeaderText(null);
                alert.setContentText("确认密码跟输入的密码不一致");
                alert.showAndWait();

            } else if (!isMobile(list.get(4))) {
                alert.setTitle("失败！");
                alert.setHeaderText(null);
                alert.setContentText("请输入正确的11位手机号码");
                alert.showAndWait();

            } else {
                alert.setTitle("失败！");
                alert.setHeaderText(null);
                alert.setContentText("发生未知错误，请重新注册");
                alert.showAndWait();
            }
        }
    }

    public void getRegisterData() {
        list = Arrays.asList(null, null, null, null, null);//为五个textfield值设置空地址
        list.set(0, tf_name.getText().trim());
        list.set(1, tf_account.getText().trim());
        list.set(2, tf_password.getText().trim());
        list.set(3, tf_passwordAgain.getText().trim());
        list.set(4, tf_phoneNumber.getText().trim());
    }

    public boolean check() {
        System.out.println("几个值" + list.size());
        System.out.println("获取到名字:" + list.get(0));
        System.out.println("获取到学号/学工号:" + list.get(1));
        System.out.println("获取到密码:" + list.get(2));
        System.out.println("获取到再次确认:" + list.get(3));
        System.out.println("获取到电话号码:" + list.get(4));
        if ((list.get(0).isEmpty() || !isChineseWord(list.get(0))) || (list.get(1).isEmpty() || !isDegital(list.get(1)))
                || (list.get(2).isEmpty()) || (list.get(3).isEmpty())
                || list.get(4).isEmpty() || !isMobile(list.get(4)) || !isConsistent(list.get(2), list.get(3)))
        {
            checkNumber = false;
        }
        else {
            checkNumber = true;
        }
        return checkNumber;
    }

    public static boolean isChineseWord(String str) {
        String pattern = "[\u4e00-\u9fa5]+";
        boolean isMatch = Pattern.matches(pattern, str);
        return isMatch;
    }

    public static boolean isDegital(String str) {
        String pattern = "^[0-9_]+$";
        boolean isMatch = Pattern.matches(pattern, str);
        return isMatch;
    }

    /*public static boolean isPassword(String str) {
        String pattern = "^(\\d+[A-Za-z]+[A-Za-z0-9]*)|([A-Za-z]+\\d+[A-Za-z0-9]*)$";
        boolean isMatch = Pattern.matches(pattern, str);
        return isMatch;
    }*/

    public static boolean isMobile(String str) {
        String pattern = "^1[3|4|5|7|8][0-9]{9}$";
        boolean isMatch = Pattern.matches(pattern, str);
        return isMatch;
    }

    public static boolean isConsistent(String str1, String str2) {
        boolean isMatch = str1.equals(str2);
        return isMatch;
    }

    public void allClear() {
        tf_name.clear();
        tf_account.clear();
        tf_password.clear();
        tf_passwordAgain.clear();
        tf_phoneNumber.clear();
    }

}

