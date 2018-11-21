package application.model;

import javafx.beans.property.*;

//String createInfoTableSqlStr = "CREATE TABLE info (usersID VARCHAR(20) NOT NULL, bookID VARCHAR(20), " +
//        "classOfUsers VARCHAR(5), borrowDate DATE, shouldReturnDate DATE, returnDate DATE, renewTimes int)";

import java.util.Date;

public class Info {
    private StringProperty account;
    private StringProperty borrowBookID;
    private StringProperty name;
    private StringProperty borrowDate;
    private StringProperty shouldReturnDate;
    private StringProperty returnDate;
    private IntegerProperty renewTimes;

    public Info(){
        this(null,null,null,null,null,null,null);}

    public Info(String usersID, String bookID, String name, String borrowDate, String shouldReturnDate, String returnDate, Integer renewTimes){
        this.account = new SimpleStringProperty(usersID);
        this.borrowBookID =  new SimpleStringProperty(bookID);
        this.name =  new SimpleStringProperty(name);
        this.borrowDate =  new SimpleStringProperty(borrowDate);
        this.shouldReturnDate =  new SimpleStringProperty(shouldReturnDate);
        this.returnDate =  new SimpleStringProperty(returnDate);
        this.renewTimes =  new SimpleIntegerProperty(renewTimes);
    }

    public String getUsersID() { return account.get(); }

    public void setUsersID(String usersID) { this.account.set(usersID); }

    public String getBookID() { return borrowBookID.get(); }

    public void setBookID(String bookID) { this.borrowBookID.set(bookID); }

    public String getName() { return name.get(); }

    public void setName(String name) { this.name.set(name); }

    public String getBorrowDate() { return borrowDate.get(); }

    public void setBorrowDate(String borrowDate) { this.borrowDate.set(borrowDate); }

    public String getShouldReturnDate() { return shouldReturnDate.get(); }

    public void setReturnDate(String returnDate) { this.returnDate.set(returnDate); }

    public String getReturnDate() { return returnDate.get(); }

    public void setShouldReturnDate(String shouldReturnDate) { this.shouldReturnDate.set(shouldReturnDate); }

    public Integer getRenewtimes() { return renewTimes.get(); }

    public IntegerProperty renewTimesProperty(){
        return renewTimes;
    }

    public void setRenewtimes(Integer renewtimes) { this.renewTimes.set(renewtimes); }
}
