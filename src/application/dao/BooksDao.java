package application.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.Books;
import application.util.JDBCUtil;

public class BooksDao {

    /**
     *
     * 该方法用于将图书对象保存到数据库
     * 使用preparedStatement防止sql注入
     * @param books 要保存到数据库的图书对象
     * @return 返回0代表数据库中已有该图书，取消保存。返回1代表保存成功，返回-1代表保存不成功。
     */
    public int save(Books books){
        Connection con=JDBCUtil.getConnection();
        String insertBooksSqlStr = "INSERT INTO books VALUES(?, ?, ?, ?, ?, ?, Y)";
        if(con!=null){
            try {
                if(findById(books.getBookID())!=null){
                    System.out.println("数据已存在，不能重复插入");
                    return 0;
                }
                PreparedStatement preparedStatement=con.prepareStatement(insertBooksSqlStr);
                preparedStatement.setString(1, books.getBookID());
                preparedStatement.setString(2, books.getName());
                preparedStatement.setString(3, books.getType());
                preparedStatement.setString(4, books.getWriter());
                preparedStatement.setString(5, books.getPress());
                preparedStatement.setDouble(6, books.getPrice());
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        return -1;
    }

    /**
     * 该方法用于从数据库删除指定id的图书
     * @param id 要删除的图书的id号
     * @return 返回0代表该图书不存在，返回1代表删除成功，返回-1代表删除失败
     */
    public int deleteById(String id){
        Connection con=JDBCUtil.getConnection();
        String deleteCourseSqlStr = "DELETE FROM books WHERE bookID=?";
        if(con!=null){
            try {
                if(findById(id)==null) return 0;
                PreparedStatement preparedStatement=con.prepareStatement(deleteCourseSqlStr);
                preparedStatement.setString(1, id);
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        return -1;
    }

    /**
     * 该方法用于更新数据库中指定图书的信息
     * @param books 要更新的图书对象
     * @return 返回0代表该图书不存在，返回1代表更新成功，返回-1代表更新失败
     *
     * 暂时只能set name，等等再写
     */
    public int update(Books books){
        Connection con=JDBCUtil.getConnection();
        String updateCourseSqlStr = "UPDATE books SET name=? WHERE bookID=?";
        if(con!=null){
            try {
                if(findById(books.getBookID())==null) return 0;
                PreparedStatement preparedStatement=con.prepareStatement(updateCourseSqlStr);
                preparedStatement.setString(1, books.getName());
                preparedStatement.setString(2, books.getBookID());
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        return -1;
    }

    /**
     * 该方法查询并返回数据库中的所有图书。
     * 该方法可能返回大量的图书对象，有可能造成网络拥堵或机器运行缓慢，请谨慎使用。
     * @return 返回数据库中的所有图书列表。返回null代表查询失败。
     */
    public List<Books> findAll(){
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
        return null;
    }

    /**
     * 该方法根据课程id号从数据库中查找图书
     * @param id 要查找的图书id号
     * @return 指定id的图书对象
     */
    public Books findById(String id){
        Connection con=JDBCUtil.getConnection();
        String selectAllSqlStr="SELECT * FROM courses where id='"+id+"'";
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
    }

    /**
     * 该方法用于将课程查询的结果集转换成图书列表
     * @param rs 要转换的图书结果集
     * @return 转换好的图书列表
     */
    private List<Books> rsToBooks(ResultSet rs) {
        List<Books> courseList=new ArrayList<>();
        try {
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String type = rs.getString(3);
                String writer = rs.getString(4);
                String press = rs.getString(5);
                Double price = rs.getDouble(6);
                String isInLibrary = rs.getString(7);
                Books course=new Books(id, name, type, writer, press, price, isInLibrary);
                courseList.add(course);
            }
            return courseList;
        } catch (SQLException e) {
            processSqlError(e);
        }
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
