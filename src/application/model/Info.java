package application.model;


//String createInfoTableSqlStr = "CREATE TABLE info (usersID VARCHAR(20) NOT NULL, bookID VARCHAR(20), " +
//        "classOfUsers VARCHAR(5), borrowDate DATE, shouldReturnDate DATE, returnDate DATE, renewTimes int)";

import java.util.Date;

public class Info {
    private String account;
    private String bookID;
    private String name;
    private Date borrowDate;
    private Date shouldReturnDate;
    private Date returnDate;
    private int renewTimes;

    public Info(){}

    public Info(String usersID, String bookID, String name, Date borrowDate, Date shouldReturnDate, Date returnDate, int renewtimes){
        this.account = usersID;
        this.bookID = bookID;
        this.name = name;
        this.borrowDate = borrowDate;
        this.shouldReturnDate = shouldReturnDate;
        this.returnDate = returnDate;
        this.renewTimes = renewtimes;
    }

    public String getUsersID() { return account; }

    public void setUsersID(String usersID) { this.account = usersID; }

    public String getBookID() { return bookID; }

    public void setBookID(String bookID) { this.bookID = bookID; }

    public String getClassOfUsers() { return name; }

    public void setClassOfUsers(String classOfUsers) { this.name = classOfUsers; }

    public Date getBorrowDate() { return borrowDate; }

    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }

    public Date getShouldReturnDate() { return shouldReturnDate; }

    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }

    public Date getReturnDate() { return returnDate; }

    public void setShouldReturnDate(Date shouldReturnDate) { this.shouldReturnDate = shouldReturnDate; }

    public int getRenewtimes() { return renewTimes; }

    public void setRenewtimes(int renewtimes) { this.renewTimes = renewtimes; }
}
