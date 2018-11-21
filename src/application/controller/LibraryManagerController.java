package application.controller;

import application.MainLibraryManager;
import application.MainReader;
import application.dao.BooksDao;
import application.dao.InfoDao;
import application.dao.ReaderDao;
import application.model.Books;
import application.model.Info;
import application.model.Reader;
import application.util.JDBCUtil;
import application.util.MyDiolog;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.print.Book;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LibraryManagerController implements Initializable {

    /**
     * 图书管理布局
     *用来修改图书信息
     * */
    @FXML TableView tb_library_manager;
    @FXML TableColumn<Books,String> tc_manager_type;
    @FXML TableColumn<Books,String> tc_manager_bookID;
    @FXML TableColumn<Books,String> tc_manager_name;
    @FXML TableColumn<Books,String> tc_manager_press;
    @FXML TableColumn<Books,String> tc_manager_writer;
    @FXML TableColumn<Books,String> tc_manager_price;
    @FXML TableColumn<Books,String> tc_manager_isInLibrary;
    @FXML TableColumn<Books,String> tc_manager_borrow;
    @FXML TableColumn<Books,Boolean> tc_manager_choose;
    //按钮
    @FXML Button btn_addBooks;
    @FXML Button btn_updateBooks;
    @FXML Button btn_delectBooks;
    @FXML Button btn_findBooks;
    /**
     *读者信息管理布局
     * 主要是用来修改借阅者相关信息
     **/
    @FXML TableView tb_reader_manager;
    @FXML TableColumn<Info,String> tc_readerInfo_number;
    @FXML TableColumn<Info,String> tc_readerInfo_Id;
    @FXML TableColumn<Info,String> tc_readerInfo_borrowBookName;
    @FXML TableColumn<Info,String> tc_readerInfo_borrowTime;
    @FXML TableColumn<Info,String> tc_readerInfo_shouldReturnTime;
    @FXML TableColumn<Info,String> tc_readerInfo_returnTime;
    @FXML TableColumn<Info,String> tc_readerInfo_renewTimes;
    @FXML TableColumn<Info,String> tc_readerInfo_returnBook;
    @FXML TableColumn<Info,String> tc_readerInfo_renew;
    //按钮
    @FXML Button btn_findReader;
    /**
     * 个人消息界面
     * */
    @FXML TextField tf_library_name;
    @FXML TextField tf_library_ID;
    @FXML TextField tf_library_phone;
    @FXML Button btn_librarian_change;
    @FXML Button  btn_librarian_confirm;
    @FXML Button btn_changPassword;
    //图书管理数据
    private ObservableList<Books> dataBooksManager;
    //读者管理数据
    private ObservableList<Object> dataReaderManager;
    private String account;
    BooksDao booksDao =new BooksDao();
    InfoDao infoDao = new InfoDao();
    ReaderDao readerDao = new ReaderDao();
    private Reader mreader;
    @FXML
    private void btn_addBooksClick() {
        System.out.println("添加图书按钮被点击");
        //使用可填数据的对话框
        try {
            TextInputDialog dialog = new TextInputDialog("NoAdd");
            dialog.setTitle("添加图书");
            dialog.setHeaderText("是否要添加新的图书");
            //设置添加图书的对话框
            VBox pane = new VBox();
            TextField tf_bookID = new TextField();
            tf_bookID.setPromptText("图书编号");
            TextField tf_bookName = new TextField();
            tf_bookName.setPromptText("图书名称");
            TextField tf_bookType = new TextField();
            tf_bookType.setPromptText("图书类型");
            TextField tf_bookWriter = new TextField();
            tf_bookWriter.setPromptText("作者");
            TextField tf_bookPress = new TextField();
            tf_bookPress.setPromptText("出版社");
            TextField tf_price = new TextField();
            tf_price.setPromptText("价钱");
            TextField tf_isInLibrary = new TextField();
            tf_isInLibrary.setPromptText("是否在馆");
            pane.getChildren().addAll(tf_bookID, tf_bookName, tf_bookType, tf_bookWriter, tf_bookPress,tf_price,tf_isInLibrary);
            pane.setSpacing(20);
            dialog.getDialogPane().setContent(pane);

            String bookID = tf_bookID.getText();
            String bookName = tf_bookName.getText();
            String bookType = tf_bookType.getText();
            String bookWriter = tf_bookWriter.getText();
            String bookPress = tf_bookPress.getText();
            String isInlibrary = tf_isInLibrary.getText();
            Double price = Double.valueOf(tf_price.getText());
            System.out.println("id"+bookID+bookName+bookType+bookPress+bookWriter+isInlibrary);
            booksDao = new BooksDao();
            Books books = new Books(bookID,bookName,bookType,bookWriter,bookPress,price,isInlibrary);
            booksDao.addBooks(books);
            reflesh();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reflesh() {
        dataBooksManager = FXCollections.observableArrayList(booksDao.getListOFBooks());
        tb_library_manager.setItems(dataBooksManager);
        dataReaderManager = FXCollections.observableArrayList(infoDao.getAllInfoList());
        tb_reader_manager.setItems(dataReaderManager);
    }

    private void showBookManagerTable() {
        tc_manager_bookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        tc_manager_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tc_manager_press.setCellValueFactory(new PropertyValueFactory<>("press"));
        tc_manager_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tc_manager_writer.setCellValueFactory(new PropertyValueFactory<>("writer"));
        tc_manager_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        tc_manager_type.setSortType(TableColumn.SortType.ASCENDING);
        tc_manager_isInLibrary.setCellValueFactory(new PropertyValueFactory<>("isInLibrary"));
        tc_manager_choose.setCellFactory(new Callback<TableColumn<Books, Boolean>, TableCell<Books, Boolean>>() {
            @Override
            public TableCell<Books, Boolean> call(TableColumn<Books, Boolean> param) {
                return new TableCell<Books, Boolean>(){
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        CheckBox checkBox = new CheckBox();
                        if (empty){
                            setGraphic(null);
                            setText(null);
                        }else {
                            setGraphic(checkBox);
                            checkBox.selectedProperty().addListener((obVal,oldVal,newVal)->{
                                Books chooseBook;//只能一本书
                                if (newVal){
                                    //添加选中时执行的代码
                                    chooseBook = this.getTableView().getItems().get(this.getIndex());
                                    //借书按钮
                                    Books finalChooseBook = chooseBook;
                                    btn_delectBooks.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //更新ui用于显示新的alert  比如进度条百分之百之后显示
                                                    Alert alert;
                                                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                                                    alert.setTitle("删除");
                                                    alert.setHeaderText("请确认删除书籍");
                                                    alert.setContentText(finalChooseBook.getName());
                                                    alert.showAndWait();
                                                    doDelectBooks(finalChooseBook.getBookID());
                                                }
                                            });
                                        }
                                    });
                                    btn_updateBooks.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (finalChooseBook!=null) {
                                                        TextInputDialog dialog = new TextInputDialog("NoAdd");
                                                        dialog.setTitle("修改"+finalChooseBook.getName());
                                                        dialog.setHeaderText("请根据对应输入框输入信息，无需修改部分则不用填");
                                                        //设置添加图书的对话框
                                                        VBox pane = new VBox();
                                                        TextField tf_bookID = new TextField();
                                                        tf_bookID.setPromptText(finalChooseBook.getBookID());
                                                        TextField tf_bookName = new TextField();
                                                        tf_bookName.setPromptText(finalChooseBook.getName());
                                                        TextField tf_bookType = new TextField();
                                                        tf_bookType.setPromptText(finalChooseBook.getType());
                                                        TextField tf_bookWriter = new TextField();
                                                         tf_bookWriter.setPromptText(finalChooseBook.getWriter());
                                                        TextField tf_bookPress = new TextField();
                                                        tf_bookPress.setPromptText(finalChooseBook.getPress());
                                                        TextField tf_price = new TextField();
                                                        tf_price.setPromptText(finalChooseBook.getPrice().toString());
                                                        TextField tf_isInLibrary = new TextField();
                                                        tf_isInLibrary.setPromptText(finalChooseBook.getIsInLibrary());
                                                        pane.getChildren().addAll(tf_bookID, tf_bookName, tf_bookType, tf_bookWriter, tf_bookPress, tf_price, tf_isInLibrary);
                                                        pane.setSpacing(20);
                                                        dialog.getDialogPane().setContent(pane);
                                                        dialog.showAndWait();

                                                        String bookID = tf_bookID.getText();
                                                        String bookName = tf_bookName.getText();
                                                        String bookType = tf_bookType.getText();
                                                        String bookWriter = tf_bookWriter.getText();
                                                        String bookPress = tf_bookPress.getText();
                                                        String isInlibrary = tf_isInLibrary.getText();
                                                        String price = tf_price.getText();
                                                        System.out.println("id" + bookID + bookName + bookType + bookPress + bookWriter + isInlibrary);

                                                        if (bookID.isEmpty()) {
                                                            bookID = tf_bookID.getPromptText();
                                                        }
                                                        if (bookName.isEmpty()) {
                                                            bookName = tf_bookName.getPromptText();
                                                        }
                                                        if (bookType.isEmpty()) {
                                                            bookType = tf_bookType.getPromptText();
                                                        }
                                                        if (bookWriter.isEmpty()) {
                                                            bookWriter = tf_bookWriter.getPromptText();
                                                        }
                                                        if (bookPress.isEmpty()) {
                                                            bookPress = tf_bookPress.getPromptText();
                                                        }
                                                        if (isInlibrary.isEmpty()) {
                                                            isInlibrary = tf_isInLibrary.getPromptText();
                                                        }
                                                        if (price.isEmpty()) {
                                                            price = tf_price.getPromptText();
                                                        }
                                                        booksDao = new BooksDao();
                                                        Books books = new Books(bookID, bookName, bookType, bookWriter, bookPress, Double.valueOf(price), isInlibrary);
                                                        booksDao.update(books);
                                                        reflesh();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                                if (oldVal){
                                    chooseBook = null;
                                    btn_updateBooks.setOnAction(null);
                                }
                                });
                        }
                    }
                };
            }
        });
        tc_manager_borrow.setCellFactory(new Callback<TableColumn<Books, String>, TableCell<Books, String>>() {
            @Override
            public TableCell<Books, String> call(TableColumn<Books, String> param) {
                return new TableCell<Books, String>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Button button = new Button("借阅");
                        if (empty){
                            setText(null);
                            setGraphic(null);
                        }else {
                            setGraphic(button);
                            button.setOnMouseClicked((me)->{
                                Books clickedBooks = this.getTableView().getItems().get(this.getIndex());
                                if (clickedBooks.getIsInLibrary().equals("Y")){
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            TextInputDialog diolog = new TextInputDialog();
                                            diolog.setContentText("输入借阅者账号：");
                                            Optional result = diolog.showAndWait();
                                            if(result.isPresent()){
                                            System.out.println("**/*/*输入账号："+result.get());
                                            doBorrow(clickedBooks,result.get().toString());
                                            }
                                        }
                                    });

                                }else {
                                    MyDiolog myDiolog = new MyDiolog(Alert.AlertType.INFORMATION,"图书不在馆");
                                    myDiolog.mshow();
                                }
                            });
                        }
                    }
                };
            }
        });
        tb_library_manager.setItems(dataBooksManager);
    }

    private void doBorrow(Books books,String account) {
        books.setIsInLibrary("N");
        booksDao.update(books);
        infoDao.addBorrow(account,books);
        reflesh();
    }

    private void doDelectBooks(String bookID) {
        booksDao.deleteBooksById(bookID);
        reflesh();
    }

    public void showReaderManagerTable(){
        tc_readerInfo_number.setCellFactory((col)->{
            TableCell<Info,String> cell_info = new TableCell<Info,String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty){
                        int rowIndex = this.getIndex()+1;
                        this.setText(String.valueOf(rowIndex));
                    }
                }
            };
            return cell_info;
        });
        tc_readerInfo_Id.setCellValueFactory(new PropertyValueFactory<>("usersID"));
        tc_readerInfo_borrowBookName.setCellValueFactory(new PropertyValueFactory<>("name"));//书名
        tc_readerInfo_borrowTime.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        tc_readerInfo_returnTime.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        tc_readerInfo_shouldReturnTime.setCellValueFactory(new PropertyValueFactory<>("shouldReturnDate"));
        tc_readerInfo_renewTimes.setCellValueFactory(new PropertyValueFactory<>("renewTimes"));
        tc_readerInfo_returnBook.setCellFactory((col)->{
            TableCell<Info,String> cell = new TableCell<Info, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty){
                        Button button = new Button("还书");
                        this.setGraphic(button);
                        button.setOnAction((me)->{
                            this.setGraphic(button);
                            Info clickedInfo = this.getTableView().getItems().get(this.getIndex());
                            System.out.println(clickedInfo.getReturnDate());
                            if (clickedInfo.getReturnDate()==null){
                                MyDiolog diolog = new MyDiolog(Alert.AlertType.CONFIRMATION,"还书？"+clickedInfo.getName()+"");
                                diolog.mshow();
                                doReturn(clickedInfo.getUsersID(),clickedInfo.getBookID());
                            }
                            else {
                                MyDiolog diolog = new MyDiolog(Alert.AlertType.INFORMATION,"您已还书");
                                diolog.show();
                            }
                        });
                    }
                }
            };
            return cell;
        });
        //续借按钮
        tc_readerInfo_renew.setCellFactory((col)->{
            TableCell<Info,String> infoCell = new TableCell<Info,String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty){
                        Button button = new Button("续借");
                        this.setGraphic(button);
                        button.setOnAction((me)->{
                            this.setGraphic(button);
                            Info renewBoook = this.getTableView().getItems().get(this.getIndex());
                            doRenew(renewBoook.getUsersID(),renewBoook);
                            System.out.println(renewBoook.getName());
                        });
                    }
                }
            };
            return infoCell;
        });
        tb_reader_manager.setItems(dataReaderManager);
    }

    private void doReturn(String userId,String bookID) {
        infoDao.returnBook(userId,bookID);
        reflesh();
    }

    private void doRenew(String usersID, Info renewBoook) {
        if(infoDao.renewBook(usersID,renewBoook.getBookID()) == 1){
        //Books books = booksDao.findById(renewBoook.getBookID());
       // books.setIsInLibrary("N");
        reflesh();
        MyDiolog myDiolog  = new MyDiolog(Alert.AlertType.CONFIRMATION,"续借成功！");
        myDiolog.show();
        }
    }

    public void setMreader(Reader reader){
       this.mreader = reader;
        tf_library_ID.setText(reader.getUsersID());
        tf_library_name.setText(reader.getName());
        tf_library_phone.setText(reader.getContact());
    }
    public void init(String account) {
        System.out.println("图书管理员******000");
        this.account = account;
        Reader reader= ReaderDao.findById(account);
        this.setMreader(reader);
        JDBCUtil.createDB();
        dataBooksManager = FXCollections.observableArrayList(booksDao.getListOFBooks());
        dataReaderManager = FXCollections.observableArrayList(infoDao.getAllInfoList());
        this.showReaderManagerTable();
        this.showBookManagerTable();

    }
    /**
     *更改个人信息和更改个人密码
     * 看个人信息页那三个按钮
     */
    @FXML
    public void changePassword(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ChangePasswordDialogController passwordDialog = new ChangePasswordDialogController();
                passwordDialog.setContentText("请输入您的新密码：");
                Optional mresult = passwordDialog.showAndWait();
                if (mresult.isPresent()) {
                    System.out.println("Your 密码: " + mresult.get());
                    mreader.setPassword(mresult.get().toString());
                    readerDao.updatePassword(mreader);
                }
            }
        });
    }
    @FXML
    public  void doChangeInfo(){

        tf_library_phone.setEditable(true);
        MyDiolog myDiolog = new MyDiolog(Alert.AlertType.INFORMATION, "您现在可以编辑个人信息");
        myDiolog.mshow();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
               btn_librarian_confirm.setOnAction(e->{
                    String string = tf_library_phone.getText();
                    mreader.setContact(string);
                    System.out.println(string);
                    readerDao.update(mreader);
                    mreader = ReaderDao.findById(account);
                    System.out.println("$$$"+mreader.getContact());
                    MyDiolog diolog = new MyDiolog(Alert.AlertType.INFORMATION, "修改成功");
                    diolog.mshow();
                    tf_library_phone.setEditable(false);
                    tf_library_phone.setText(mreader.getContact());
                });
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private class TableRowControl extends TableRow {
        public TableRowControl(){
            super();
            this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton().equals(MouseButton.PRIMARY)&&event.getClickCount()==2&&TableRowControl.this.getIndex()<tb_library_manager.getItems().size()){
                        //doSomething
                        System.out.println("******编辑吗？？？？？");
                        tc_manager_bookID.setCellFactory(TextFieldTableCell.forTableColumn());
                        tc_manager_type.setCellFactory(TextFieldTableCell.forTableColumn());
                        tc_manager_name.setCellFactory(TextFieldTableCell.forTableColumn());
                        //tc_manager_price.setCellFactory(TextFieldTableCell.forTableColumn());
                        //tc_manager_writer.setCellFactory(TextFieldTableCell.forTableColumn());
                        }
                    }
            });
        }
    }
}
