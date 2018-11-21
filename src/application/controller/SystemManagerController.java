package application.controller;

import application.dao.ReaderDao;
import application.model.Reader;
import application.util.MyDiolog;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SystemManagerController implements Initializable {

    /**
     * 用户管理
     */
    @FXML
    TableView tb_system_libraryManager;
    @FXML
    TableColumn<Reader, String> tc_libraryManager_role;
    @FXML
    TableColumn<Reader, String> tc_libraryManager_name;
    @FXML
    TableColumn<Reader, String> tc_libraryManager_Id;
    @FXML
    TableColumn<Reader, String> tc_libraryManager_phone;
    @FXML
    TableColumn<Reader, Boolean> tc_libraryManager_choose;
    @FXML Button btn_update;

    /**
     * 个人消息页面
     */
    @FXML
    TextField tf_system_name;
    @FXML
    TextField tf_system_ID;
    @FXML
    TextField tf_system_phone;
    @FXML
    Button btn_changeInfo;
    @FXML Button btn_confirmChange;
    @FXML Button btn_changePassword;

    private ObservableList<Reader> data_LibraryManager;
    private  String account;
    ReaderDao readerDao = new ReaderDao();
    Reader mreader = new Reader();


    private void doChangeReader(String id) {
        Alert inputDialog = new Alert(Alert.AlertType.CONFIRMATION);
        Reader reader = ReaderDao.findById(id);
        inputDialog.setTitle("更改信息"+reader.getName());
        VBox pane = new VBox();
        TextField tf_name = new TextField();
        TextField tf_phone = new TextField();
        tf_name.setPromptText("姓名");
        tf_phone.setPromptText("联系方式");
        pane.setSpacing(20);
        pane.getChildren().addAll(tf_name,tf_phone);
        inputDialog.getDialogPane().setContent(pane);
        inputDialog.showAndWait();

        String name = tf_name.getText();
        String phone  = tf_phone.getText();

        reader.setContact(phone);
        reader.setName(name);
        readerDao.update(reader);
        reflesh();
    }

    private void reflesh() {
        data_LibraryManager = FXCollections.observableArrayList(readerDao.getListOFReaderWithoutSuperAdmin());
        tb_system_libraryManager.setItems(data_LibraryManager);
    }
    public void showLibraryManagerTable(){
        tc_libraryManager_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        tc_libraryManager_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tc_libraryManager_Id.setCellValueFactory(new PropertyValueFactory<>("usersID"));
        tc_libraryManager_phone.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tc_libraryManager_choose.setCellFactory(new Callback<TableColumn<Reader, Boolean>, TableCell<Reader, Boolean>>() {
            public TableCell<Reader, Boolean> call(TableColumn<Reader, Boolean> param) {
                //单元格内容
                return new TableCell<Reader, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        CheckBox checkBox = new CheckBox();
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setGraphic(checkBox);
                            Reader chooseReader = this.getTableView().getItems().get(this.getIndex());
                            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                @Override
                                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                    if (newValue) {
                                        btn_update.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                doChangeReader(chooseReader.getUsersID());
                                            }
                                        });
                                    }
                                    if (oldValue){
                                        MyDiolog myDiolog = new MyDiolog(Alert.AlertType.INFORMATION,"您尚未选择");
                                        myDiolog.mshow();
                                        btn_update.setOnAction(null);
                                    }
                                }
                            });
                        }
                    }
                };
            }
        });
        tb_system_libraryManager.setItems(data_LibraryManager);
    }
    @FXML
    public void doClick(){
        System.out.println("~~~~~click");
        reflesh();
    }
    public void init(String account) {
        this.account = account;
        mreader = ReaderDao.findById(account);
        tf_system_ID.setText(mreader.getUsersID());
        tf_system_name.setText(mreader.getName());
        tf_system_phone.setText(mreader.getContact());
        data_LibraryManager = FXCollections.observableArrayList(readerDao.getListOFReaderWithoutSuperAdmin());
        showLibraryManagerTable();
    }
    /**
     * 个人信息页的两个方法
     */
    @FXML
    public void doChangeInfo(){
        tf_system_phone.setEditable(true);
        MyDiolog myDiolog = new MyDiolog(Alert.AlertType.INFORMATION, "您现在可以编辑个人信息");
        myDiolog.mshow();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                btn_confirmChange.setOnAction(e->{
                    String string = tf_system_phone.getText();
                    mreader.setContact(string);
                    System.out.println(string);
                    readerDao.update(mreader);
                    mreader = ReaderDao.findById(account);
                    System.out.println("$$$"+mreader.getContact());
                    MyDiolog diolog = new MyDiolog(Alert.AlertType.INFORMATION, "修改成功");
                    diolog.mshow();
                    tf_system_phone.setEditable(false);
                    tf_system_phone.setText(mreader.getContact());
                });
            }
        });
    }
    @FXML
    public void doChangePassword(){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ChangePasswordDialogController passwordDialog = new ChangePasswordDialogController();
                passwordDialog.setContentText("请输入您的新密码：");
                Optional result = passwordDialog.showAndWait();
                if (result.isPresent()) {
                    System.out.println("Your 密码: " + result.get());
                    mreader.setPassword(result.get().toString());
                    readerDao.updatePassword(mreader);
                }
            }
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
