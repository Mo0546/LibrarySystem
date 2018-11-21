package application.model;


import javafx.beans.property.*;

/*String createBooksTableSqlStr = "CREATE TABLE books (bookID VARCHAR(20) NOT NULL, name VARCHAR(20), " +
         "type VARCHAR(5), writer VARCHAR(30), press VARCHAR(20), price DOUBLE)";
         */
public class Books {
    private StringProperty bookID;
    private StringProperty name;
    private StringProperty type;
    private StringProperty writer;
    private StringProperty press;
    private DoubleProperty price;
    private StringProperty isInLibrary;

    public Books(){
        this(null,null,null,null,null,null,null);
    }

    public Books(String bookID, String name, String type, String writer, String press, Double price, String isInLibrary){
        this.bookID = new SimpleStringProperty(bookID);
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.writer = new SimpleStringProperty(writer);
        this.press = new SimpleStringProperty(press);
        this.price = new SimpleDoubleProperty(price);
        this.isInLibrary = new SimpleStringProperty(isInLibrary);


    }

    public String getBookID(){ return bookID.get(); }

    public void setBookID(String bookID){ this.bookID.set(bookID); }

    public String getName(){ return name.get(); }

    public void setName(String name){ this.name.set(name); }

    public String getType(){ return type.get();}

    public void setType(String type){ this.type.set(type); }

    public String getWriter(){ return writer.get(); }

    public void setWriter(String writer){ this.writer.set(writer); }

    public String getPress(){ return press.get(); }

    public void setPress(String press){ this.press.set(press); }

    public Double getPrice(){ return price.get(); }

    public void setPrice(Double price){ this.price.set(price); }

    public String getIsInLibrary() { return isInLibrary.get(); }

    public void setIsInLibrary(String isInLibrary) { this.isInLibrary.set(isInLibrary); }

 }
