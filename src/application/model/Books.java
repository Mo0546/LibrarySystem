package application.model;


 /*String createBooksTableSqlStr = "CREATE TABLE books (bookID VARCHAR(20) NOT NULL, name VARCHAR(20), " +
         "type VARCHAR(5), writer VARCHAR(30), press VARCHER(20), price DOUBLE)";
         */
public class Books {
    private String bookID;
    private String name;
    private String type;
    private String writer;
    private String press;
    private Double price;
    private String isInLibrary;

    public Books(){ }

    public Books(String bookID, String name, String type, String writer, String press, Double price, String isInLibrary){
        this.bookID = bookID;
        this.name = name;
        this.type = type;
        this.writer = writer;
        this.press = press;
        this.price = price;
        this.isInLibrary = isInLibrary;
    }

    public String getBookID(){ return bookID; }

    public void setBookID(String bookID){ this.bookID = bookID; }

    public String getName(){ return name; }

    public  void  setName(String name){ this.name = name; }

    public String getType(){ return type; }

    public void setType(String type){ this.type = type; }

    public String getWriter(){ return writer; }

    public void setWriter(String writer){ this.writer = writer; }

    public String getPress(){ return press; }

    public void setPress(String press){ this.press = press; }

    public Double getPrice(){ return price; }

    public void setPrice(Double price){ this.price = price; }

    public String getIsInLibrary() { return isInLibrary; }

    public void setIsInLibrary(String isInLibrary) { this.isInLibrary = isInLibrary; }
 }
