package application.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.Books;
import application.util.JDBCUtil;
import application.util.MyDiolog;
import javafx.scene.control.Alert;

public class BooksDao {

    /**
     *
     * 该方法用于将图书对象保存到数据库
     * 使用preparedStatement防止sql注入
     * @param books 要保存到数据库的图书对象
     * @return 返回0代表数据库中已有该图书，取消保存。返回1代表保存成功，返回-1代表保存不成功。
     *
     ************ 写完了************
     * 插入时应传递getIsInLibrary()默认为Y
     */
    public int addBooks(Books books){
        Connection con=JDBCUtil.getConnection();
        String insertBooksSqlStr = "INSERT INTO books VALUES(?, ?, ?, ?, ?, ?, ?)";
        if(con!=null){
            try {
                if(findById(books.getBookID())!=null){
                    System.out.println("数据已存在，不能重复插入");
                    MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"数据已存在，不能重复插入");
                    diolog.mshow();
                    return 0;
                }
                String tmp = "Y";
                PreparedStatement preparedStatement=con.prepareStatement(insertBooksSqlStr);
                preparedStatement.setString(1, books.getBookID());
                preparedStatement.setString(2, books.getName());
                preparedStatement.setString(3, books.getType());
                preparedStatement.setString(4, books.getWriter());
                preparedStatement.setString(5, books.getPress());
                preparedStatement.setDouble(6, books.getPrice());
                preparedStatement.setString(7, tmp);
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"保存失败");
        diolog.mshow();
        return -1;
    }

    /**
     *
     * 该方法用于将图书对象保存到数据库,传入各参数
     */
    public int addBooks2(String bookID, String name, String type, String writer, String press, Double price, String isINLibrary){
        Connection con=JDBCUtil.getConnection();
        String insertBooksSqlStr = "INSERT INTO books VALUES(?, ?, ?, ?, ?, ?, ?)";
        if(con!=null){
            try {
                if(findById(bookID)!=null){
                    System.out.println("数据已存在，不能重复插入");
                    return 0;
                }
                PreparedStatement preparedStatement=con.prepareStatement(insertBooksSqlStr);
                preparedStatement.setString(1,bookID);
                preparedStatement.setString(2,name);
                preparedStatement.setString(3,type);
                preparedStatement.setString(4,writer);
                preparedStatement.setString(5,press);
                preparedStatement.setDouble(6,price);
                preparedStatement.setString(7,isINLibrary);
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"保存失败");
        diolog.mshow();
        return -1;
    }

    /**
     * 该方法用于从数据库删除指定id的图书
     * @param id 要删除的图书的id号
     * @return 返回0代表该图书不存在，返回1代表删除成功，返回-1代表删除失败
     */
    public int deleteBooksById(String id){
        Connection con=JDBCUtil.getConnection();
        String deleteBooksSqlStr = "DELETE FROM books WHERE bookID=?";
        if(con!=null){
            try {
                if(findById(id)==null) return 0;
                PreparedStatement preparedStatement=con.prepareStatement(deleteBooksSqlStr);
                preparedStatement.setString(1, id);
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"删除失败");
        diolog.mshow();
        return -1;
    }
    /**
     * 该方法调用findById()用于更新数据库中指定图书的信息
     * @param books 要更新的图书对象
     * @return 返回0代表该图书不存在，返回1代表更新成功，返回-1代表更新失败
     *
     * 修改除id外的所有信息*
     */
    public int update(Books books){
        Connection con=JDBCUtil.getConnection();
        String updateCourseSqlStr = "UPDATE books SET name=?, type=?, writer=?, press=?, price=?, isInLibrary=? WHERE bookID=?";
        if(con!=null){
            try {
                if(findById(books.getBookID())==null) {
                    MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"图书不存在");
                    diolog.mshow();
                    return 0;}
                PreparedStatement preparedStatement=con.prepareStatement(updateCourseSqlStr);
                preparedStatement.setString(1, books.getName());
                preparedStatement.setString(2, books.getType());
                preparedStatement.setString(3, books.getWriter());
                preparedStatement.setString(4, books.getPress());
                preparedStatement.setDouble(5, books.getPrice());
                preparedStatement.setString(6, books.getIsInLibrary());
                preparedStatement.setString(7, books.getBookID());
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"更新失败");
        diolog.mshow();
        return -1;
    }

    /**
     * 用户查找图书，根据输入的图书名称，使用like进行模糊查询，然后返回一个ArrayList数组类型
     */
    public ArrayList<Books> getBooksLikeList(String name) {
        ArrayList<Books> bookLikeList = new ArrayList<Books>();
        Connection con=JDBCUtil.getConnection();
        String sql = "select * from books where name like '%"+name+"%'";

        /*if(con!=null){
            try {
                ResultSet rs=con.createStatement().executeQuery(sql);
                List<Books> bookLikeList=rsToBooks(rs);
                return bookLikeList;
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        return null;
        */

        try {
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Books tag = new Books();
                tag.setBookID(rs.getString("bookID"));
                tag.setName(rs.getString("name"));
                tag.setType(rs.getString("type"));
                tag.setWriter(rs.getString("writer"));
                tag.setPress(rs.getString("press"));
                tag.setPrice(rs.getDouble("price"));
                bookLikeList.add(tag);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JDBCUtil.closeConnection(con);
        }
        return bookLikeList;
    }

    /**
     * 该方法查询并返回数据库中的所有图书。
     * 该方法可能返回大量的图书对象，有可能造成网络拥堵或机器运行缓慢，请谨慎使用。
     * @return 返回数据库中的所有图书列表。返回null代表查询失败。
     */
    public List<Books> getListOFBooks(){
        Connection con=JDBCUtil.getConnection();
        String selectAllSqlStr="SELECT * FROM books";
        if(con!=null){
            try {
                ResultSet rs=con.createStatement().executeQuery(selectAllSqlStr);
                List<Books> booksList=rsToBooks(rs);
                return booksList;
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"查询失败");
        diolog.mshow();
        return null;
    }

    /**
     * 该方法根据图书id号从数据库中精确查找图书，返回一个对象
     * @param id 要查找的图书id号
     * @return 指定id的图书对象
     */
    public Books findById(String id){
        Connection con=JDBCUtil.getConnection();
        String selectAllSqlStr="SELECT * FROM books where bookID='"+id+"'";
        if(con!=null){
            try {
                ResultSet rs=con.createStatement().executeQuery(selectAllSqlStr);
                List<Books> bookList=rsToBooks(rs);
                if(bookList!=null && bookList.size()!=0)return bookList.get(0);
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"查询失败");
        diolog.mshow();
        return null;
    }

    /**
     * 该方法根据图书id号和Like模糊查询从数据库中模糊查找图书，返回一个对象
     * @param id 要查找的图书id号
     * @return 指定id的图书对象
     *//*
    public Books findByIdLike(String id){
        Connection con=JDBCUtil.getConnection();
        String selectAllSqlStr="SELECT * FROM courses where like '%"+id+"%'";
        if(con!=null){
            try {
                ResultSet rs=con.createStatement().executeQuery(selectAllSqlStr);
                List<Books> courseList=rsToBooks(rs);
                if(courseList!=null && courseList.size()!=0)return courseList.get(0);
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        return null;
    }*/

    /**
     * 该方法用于将图书查询的结果集转换成图书列表
     * @param rs 要转换的图书结果集
     * @return 转换好的图书列表
     */
    private List<Books> rsToBooks(ResultSet rs) {
        List<Books> booksList=new ArrayList<>();
        try {
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String type = rs.getString(3);
                String writer = rs.getString(4);
                String press = rs.getString(5);
                Double price = rs.getDouble(6);
                String isInLibrary = rs.getString(7);
                Books aBook=new Books(id, name, type, writer, press, price, isInLibrary);
                booksList.add(aBook);
            }
            return booksList;
        } catch (SQLException e) {
            processSqlError(e);
        }
        MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"查询失败");
        diolog.mshow();
        return null;
    }

    /**
     * 该方法用于处理SQL错误
     * @param e 处理SQL语句过程中产生的异常对象
     */
    private void processSqlError(Exception e) {
        e.printStackTrace();
    }
}
