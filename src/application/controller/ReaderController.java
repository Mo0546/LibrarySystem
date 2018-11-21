package application.controller;

import application.dao.BooksDao;
import application.dao.InfoDao;
import application.dao.ReaderDao;
import application.model.Books;
import application.model.Info;
import application.model.Reader;
import application.util.MyDiolog;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReaderController implements Initializable {

    /**
     * 图书馆布局
     */
    @FXML
    TableView reader_tb_library;
    //图书表的每一列
    @FXML
    TableColumn<Books, String> tc_bookID;
    @FXML
    TableColumn<Books, String> tc_name;
    @FXML
    TableColumn<Books, String> tc_type;
    @FXML
    TableColumn<Books, String> tc_writer;
    @FXML
    TableColumn<Books, String> tc_press;
    @FXML
    TableColumn<Books, Double> tc_price;
    @FXML
    TableColumn<Books, String> tc_isInLibrary;
    @FXML
    TableColumn<Books, Boolean> tc_chooseItem;
    @FXML
    Button btn_borrow;

    /**
     * 我的借阅布局
     */
    @FXML
    TableView reader_tb_myBorrow;
    //我的借阅表的每一列
    @FXML
    TableColumn<Info, String> tc_borrowBookName;
    @FXML
    TableColumn<Info, String> tc_borrow_bookId;
    @FXML
    TableColumn<Info, String> tc_borrow_time;
    @FXML
    TableColumn<Info, String> tc_return_time;
    @FXML
    TableColumn<Info, String>tc_shouldReturn_time;
    @FXML
    TableColumn<Info,String> tc_borrow_renewTimes;
    @FXML
    TableColumn<Info, String> tc_borrow_renew;
    @FXML
    TableColumn<Info,String> tc_returnBook;
    /**
     * 个人信息布局
     */
    @FXML
    TextField tf_readerName;
    @FXML
    TextField tf_readerId;
    @FXML
    TextField tf_readerPhone;
    @FXML Button btn_changPassword;
    @FXML Button btn_changInfo;
    @FXML Button btn_confirmChange;
    /**
     * 搜索框
     * */
    @FXML TextField tf_books_find;

    /**
     *Tab栏
     */
    @FXML Tab tab_myBorrow;

    private ObservableList<Books> dataDB;//图书表数据
    private ObservableList<Info> dataTest;
    private Reader mreader;
    BooksDao booksDao = new BooksDao();
    InfoDao infoDao;
    ReaderDao readerDao = new ReaderDao();
    int checkCount;//判断只能选择借阅一本书
    String account;//登录账户
    @FXML Button btn_find;

    /**
     * 修改信息
     */
    @FXML
    public void doclick() {
        tf_readerPhone.setEditable(true);
        MyDiolog myDiolog = new MyDiolog(Alert.AlertType.INFORMATION, "您现在可以编辑个人信息");
        myDiolog.mshow();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                btn_confirmChange.setOnAction(e->{
                    String string = tf_readerPhone.getText();
                    mreader.setContact(string);
                    System.out.println(string);
                    readerDao.update(mreader);
                    mreader = ReaderDao.findById(account);
                    System.out.println("$$$"+mreader.getContact());
                    MyDiolog diolog = new MyDiolog(Alert.AlertType.INFORMATION, "修改成功");
                    diolog.mshow();
                    tf_readerPhone.setEditable(false);
                    tf_readerPhone.setText(mreader.getContact());
                });
            }
        });
    }
    /**
     * 显示图书表
     */
    public void showBooksTable() {
        tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tc_bookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        tc_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        tc_writer.setCellValueFactory(new PropertyValueFactory<>("writer"));
        tc_press.setCellValueFactory(new PropertyValueFactory<>("press"));
        tc_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tc_isInLibrary.setCellValueFactory(new PropertyValueFactory<>("isInLibrary"));
        //原来是需要设置Boolean值！
        //tc_chooseItem.setCellFactory(CheckBoxTableCell.forTableColumn(tc_chooseItem));
        tc_chooseItem.setCellFactory(new Callback<TableColumn<Books, Boolean>, TableCell<Books, Boolean>>() {
            @Override
            public TableCell<Books, Boolean> call(TableColumn<Books, Boolean> param) {
                //单元格内容
                return new TableCell<Books, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        CheckBox checkBox = new CheckBox();
                        if (empty) {
                            checkCount = 0;
                            setText(null);
                            setGraphic(null);
                        } else {
                            setGraphic(checkBox);
                            //监听选择框
                            checkBox.selectedProperty().addListener((obVal, oldVal, newVal) -> {
                                Books chooseBook;//只能借一本书
                                if (newVal) {
                                    //添加选中时执行的代码
                                    checkCount++;
                                    chooseBook = this.getTableView().getItems().get(this.getIndex());
                                    //借书按钮
                                    Books finalChooseBook = chooseBook;
                                    btn_borrow.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //更新ui用于显示新的alert  比如进度条百分之百之后显示
                                                    Alert alert;
                                                    if (checkCount == 1) {
                                                        alert = new Alert(Alert.AlertType.INFORMATION);
                                                        alert.setTitle("借阅成功");
                                                        alert.setHeaderText("请确认借阅书籍");
                                                        alert.setContentText(finalChooseBook.getName());
                                                        alert.showAndWait();
                                                        doBorrowBook(finalChooseBook.getBookID());
                                                    } else {
                                                        MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"只能借阅一本书");
                                                        diolog.mshow();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                                if (oldVal){
                                    //取消选中时
                                    chooseBook = null;
                                    checkCount--;
                                }
                            });
                        }
                    }
                };
            }
        });
        /**
         * 查数据库books的数据
         */
        //JDBCUtil.createDB();
        dataDB = FXCollections.observableArrayList(booksDao.getListOFBooks());
        reader_tb_library.setItems(dataDB);
    }

    /**
     *借书事务
     */
    private void doBorrowBook(String id) {
        System.out.println("*****借书"+id);
        System.out.println("*****用户"+account);
        Books book = booksDao.findById(id);
        System.out.println("*****"+book.getName());
        book.setIsInLibrary("N");
        booksDao.update(book);
        infoDao.addBorrow(account,book);
        System.out.println("****");
        //infoDao.renewBook(account,book.getBookID());
        this.reflesh();
    }

    private void reflesh() {
        dataDB = FXCollections.observableArrayList(booksDao.getListOFBooks());
        reader_tb_library.setItems(dataDB);
        infoDao = new InfoDao();
        dataTest = FXCollections.observableArrayList(infoDao.getInfoList(account));
        reader_tb_myBorrow.setItems(dataTest);
    }

    /**
     * 显示我的借阅表
     */
    public void showBorrowBooksTable() {
        tc_borrowBookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tc_borrow_time.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        tc_return_time.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        tc_borrow_renewTimes.setCellValueFactory(new PropertyValueFactory<>("renewTimes"));
        tc_shouldReturn_time.setCellValueFactory(new PropertyValueFactory<>("shouldReturnDate"));
        tc_borrow_renew.setCellFactory(new Callback<TableColumn<Info, String>, TableCell<Info, String>>() {
            @Override
            public TableCell<Info, String> call(TableColumn<Info, String> param) {
                return new TableCell<Info,String>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Button button = new Button("续借");
                        if (empty){
                            setText(null);
                            setGraphic(null);
                        }else {
                            setGraphic(button);
                            button.setOnMouseClicked((me)->{
                                Info clickedInfo = this.getTableView().getItems().get(this.getIndex());
                                int i = clickedInfo.getRenewtimes().intValue();
                                if(i<=3) {
                                    System.out.println("****续借" + i);
                                    if(clickedInfo.getReturnDate()==null){
                                        MyDiolog myDiolog  = new MyDiolog(Alert.AlertType.CONFIRMATION,"您尚未还书！无法续借");
                                        myDiolog.show();
                                    }else {
                                        MyDiolog myDiolog = new MyDiolog(Alert.AlertType.CONFIRMATION, "是否续借？" + clickedInfo.getName() + "");
                                        myDiolog.show();
                                        int times = clickedInfo.getRenewtimes()+1;
                                        clickedInfo.setRenewtimes(times);
                                        doRenew(clickedInfo.getBookID(),times,clickedInfo);
                                    }
                                }else {
                                    MyDiolog myDiolog  = new MyDiolog(Alert.AlertType.CONFIRMATION,"您续借已经超过三次，无法续借");
                                    myDiolog.show();
                                    }
                            });
                        }
                    }
                };
            }
        });
        tc_returnBook.setCellFactory(new Callback<TableColumn<Info, String>, TableCell<Info, String>>() {
            @Override
            public TableCell<Info, String> call(TableColumn<Info, String> param) {
                return new TableCell<Info, String>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Button button = new Button("还书");
                        if (empty){
                            setText(null);
                            setGraphic(null);
                        }else {
                            setGraphic(button);
                            button.setOnMouseClicked((me)->{
                                Info clickedInfo = this.getTableView().getItems().get(this.getIndex());
                                System.out.println(clickedInfo.getReturnDate());
                                if (clickedInfo.getReturnDate()==null){
                                MyDiolog diolog = new MyDiolog(Alert.AlertType.CONFIRMATION,"是否还书？"+clickedInfo.getName()+"");
                                diolog.mshow();
                                doReturn(clickedInfo.getBookID());
                                }
                                else {
                                    MyDiolog diolog = new MyDiolog(Alert.AlertType.INFORMATION,"您已还书");
                                    diolog.show();
                                }
                            });
                        }
                    }
                };
            }
        });
        infoDao = new InfoDao();
        dataTest = FXCollections.observableArrayList(infoDao.getInfoList(account));
        reader_tb_myBorrow.setItems(dataTest);
    }

    private void doRenew(String bookID,int times,Info clickInfo) {
        //*****借阅次数的update
        System.out.println(account+"***续借***"+ bookID);
        clickInfo.setRenewtimes(times);
        infoDao.renewBook(account,bookID);
        reflesh();
        System.out.println("*****续借后   次******"+clickInfo.getRenewtimes());
    }

    private void doReturn(String bookID) {
        //System.out.println("****还书"+bookID);
        infoDao.returnBook(account,bookID);
        reflesh();
    }

    /**
     * 图书表页的搜索
     * */
    @FXML
    public void find(){
        //dataDB.removeAll();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String string= tf_books_find.getText();
                System.out.println(string);
                List<Books> booksList = booksDao.getBooksLikeList(string);
                System.out.println(booksList.size());
            }
        });
        //dataDB.add(booksDao.findById(string));
        //reader_tb_library.setItems(dataDB);
           }
    @FXML public void tabClick(){
        System.out.println("~~~click");
        reflesh();
    }

    public void init(String account) {
        this.account = account;
        Reader reader = ReaderDao.findById(account);
        this.setMReader(reader);
    }
    /**
     * 填充个人信息页
     */
    public void setMReader(Reader reader){
        //System.out.println("****001");
        System.out.println(reader.getName());
        this.mreader = reader;
        tf_readerName.setText(mreader.getName());
        tf_readerPhone.setText(mreader.getContact());
        tf_readerId.setText(mreader.getUsersID());
    }

    @FXML
    public void changePassword() {
        System.out.println("****密码");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ChangePasswordDialogController passwordDialogController = new ChangePasswordDialogController();
                passwordDialogController.setContentText("请输入您的新密码：");
                Optional result = passwordDialogController.showAndWait();
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
        this.showBooksTable();
        this.showBorrowBooksTable();
    }


}

