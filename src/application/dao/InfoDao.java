package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

import application.model.Books;
import application.model.Info;
import application.model.Reader;
import application.util.JDBCUtil;
import application.util.MyDiolog;
import javafx.scene.control.Alert;

public class InfoDao {


    /**
     * 获取借阅记录的全部信息，传入的条件有account
     * @param account 用户账号
     * @return infoLikeList 结果列表
     */
    public ArrayList<Info> getInfoList(String account){
        ArrayList<Info> infoLikeList = new ArrayList<>();
        Connection con=JDBCUtil.getConnection();
        String sql = "select * from info where account='"+account+"'";
        if (con == null) throw new AssertionError();
        add_list(infoLikeList, con, sql);
        return infoLikeList;
    }

    /**
     * 获取借阅记录的全部信息，传入的条件有account，查找未归还的
     * @param account 用户账号
     * @return infoLikeList 结果列表
     */
    public ArrayList<Info> getInfoList5(String account){
        ArrayList<Info> infoLikeList = new ArrayList<>();
        Connection con=JDBCUtil.getConnection();
        String sql = "select * from info where returnDate IS NULL and account='"+account+"'";
        if (con == null) throw new AssertionError();
        add_list(infoLikeList, con, sql);
        return infoLikeList;
    }

    /**
     * 获取借阅记录的全部信息，传入的条件是borrowBookID
     * @param borrowBookID 图书编号
     * @return infoLikeList 结果列表
     */
    public ArrayList<Info> getInfoList2(String borrowBookID){
        ArrayList<Info> infoLikeList = new ArrayList<>();
        Connection con=JDBCUtil.getConnection();
        String sql = "SELECT * FROM info WHERE borrowBookID='"+borrowBookID+"'";
        assert con != null;
        add_list(infoLikeList, con, sql);
        return infoLikeList;
    }

    /**
     * 获取借阅记录的全部信息，传入的条件是account
     * @param account 用户账号
     * @return infoLikeList 结果列表
     */
    public ArrayList<Info> getInfoList3(String account){
        ArrayList<Info> infoLikeList = new ArrayList<>();
        Connection con=JDBCUtil.getConnection();
        String sql = "SELECT * FROM info WHERE account='"+account+"'";
        assert con != null;
        add_list(infoLikeList, con, sql);
        return infoLikeList;
    }

    /**
     * 获取借阅记录的全部信息，传入的条件是name
     * @param name 图书名称
     * @return infoLikeList 结果列表
     */
    public ArrayList<Info> getInfoList4(String name){
        ArrayList<Info> infoLikeList = new ArrayList<>();
        Connection con=JDBCUtil.getConnection();
        String sql = "SELECT * FROM info WHERE name='"+name+"'";
        add_list(infoLikeList, con, sql);
        return infoLikeList;
    }

    public ArrayList<Info> getAllInfoList(){
        ArrayList<Info> infoLikeList = new ArrayList<>();
        Connection con=JDBCUtil.getConnection();
        String sql = "select * from info";
        if (con == null) throw new AssertionError();
        add_list(infoLikeList, con, sql);
        return infoLikeList;
    }
    /**
     * 以下两个方法在查询借阅中调用
     * */
    private void add_list(ArrayList<Info> infoLikeList, Connection con, String sql) {
        try {
            PreparedStatement stm = null;
            stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                /*
                Info tag = new Info();
                tag.setUsersID(rs.getString("account"));
                tag.borrowBookID(rs.getString("borrowBookID"));
                tag.setName(rs.getString("name"));
                tag.setBorrowDate(rs.getString("borrowDate"));
                tag.setShouldReturnDate(rs.getString("shouldReturnDate"));
                tag.setReturnDate(rs.getString("returnDate"));
                tag.setRenewtimes(rs.getInt("renewTimes"));
                infoLikeList.add(tag);
                */
                System.out.println("rs");
                String account = rs.getString(1);
                String borrowBookID = rs.getString(2);
                String name = rs.getString(3);
                String borrowDate = rs.getString(4);
                String shouldReturnDate = rs.getString(5);
                String returnDate = rs.getString(6);
                int renewTimes = rs.getInt(7);
                Info anInfo=new Info(account, borrowBookID, name, borrowDate, shouldReturnDate, returnDate, renewTimes);
                infoLikeList.add(anInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JDBCUtil.closeConnection(con);
        }
    }


    /**
     * 增加图书借阅，
     * java.time.LocalDate获取日期
     * @param account 用户账号
     * @param aBook 图书对象
     * eturn 返回0代表数据库中已有该图书，取消保存。返回1代表保存成功，返回-1代表保存不成功。
     */
    public int addBorrow(String account, Books aBook) {

        //生成日期的功能
        /*
        Date now = new Date();
        // java.util.Date -> java.time.LocalDate
        LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // java.time.LocalDate -> java.sql.Date
        Date newDate=java.sql.Date.valueOf(localDate);
        //System.out.printf("%1$tF %1$tT\n", newDate);
        */

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DATE);
        System.out.println(month);
        //生成借阅开始日期
        String berrow_time = ""+year+"-"+month+"-"+day;
        month = month + 3;
        if(month > 12){
            month -= 12;
            year++;
        }
        //生成截止还书日期
        String should_return_time = ""+year+"-"+month+"-"+day;

        Connection con=JDBCUtil.getConnection();
        String sql = "INSERT INTO info(account,borrowBookID,name,borrowDate,shouldReturnDate,renewTimes) values(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = null;
        if(con!=null){
            try {
                if(getInfoList5(account) !=null && !getInfoList5(account).isEmpty()){
                    System.out.println("请先还书");
                    MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"数据已存在，不能重复插入");
                    diolog.mshow();
                    return 0;
                }
                /*if(getInfoList5(account).size()>2){
                    System.out.println("请先还书");
                    MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"数据已存在，不能重复插入");
                    diolog.mshow();
                    return 0;
                }*/
                preparedStatement=con.prepareStatement(sql);
                preparedStatement.setString(1, account);
                preparedStatement.setString(2, aBook.getBookID());
                preparedStatement.setString(3, aBook.getName());
                preparedStatement.setString(4, berrow_time);
                preparedStatement.setString(5, should_return_time);
                preparedStatement.setInt(6, 0);
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
     * 还书功能
     */
    public int returnBook(String account, String borrowBookID){

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DATE);
        String returnTime = ""+year+"-"+month+"-"+day;
        Connection con=JDBCUtil.getConnection();
        String sql = "UPDATE info SET returnDate =?";
        if(con!=null){
            try {
                if(findById(borrowBookID, account)==null) return 0;
                PreparedStatement preparedStatement=con.prepareStatement(sql);
                preparedStatement.setString(1, returnTime);
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"还书失败");
        diolog.mshow();
        return -1;

    }


    /**
     * 续借功能
     * @param
     */
    public int renewBook(String account, String borrowBookID){

        //重新开始计日期
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DATE);
        //生成借阅开始日期
        String berrow_time = ""+year+"-"+month+"-"+day;
        month = month + 1;
        if(month > 12){
            month -= 12;
            year++;
        }
        //生成截止还书日期
        String should_return_time = ""+year+"-"+month+"-"+day;


        Connection con=JDBCUtil.getConnection();
        String sql = "UPDATE info SET borrowDate=?, shouldReturnDate=?, renewTimes=? where borrowBookID=? and account=?";
        if(con!=null){
            try {
                if(findById(borrowBookID, account)==null) return 0;
                PreparedStatement preparedStatement=con.prepareStatement(sql);
                preparedStatement.setString(1, berrow_time);
                preparedStatement.setString(2, should_return_time);
                int i = findById(borrowBookID, account).getRenewtimes();
                if(i >= 2){
                    System.out.println("不能续借超过两次");
                    MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"不能续借超过两次");
                    diolog.mshow();
                    return 0;
                }
                preparedStatement.setInt(3, i+1);
                preparedStatement.setString(4, borrowBookID);
                preparedStatement.setString(5, account);
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"续借失败");
        diolog.mshow();
        return -1;

    }

    /**
     * 该方法用于从数据库删除借阅信息
     * @param borrowBookID 要删除的图书的id号
     * @return 返回0代表该借阅信息不存在，返回1代表删除成功，返回-1代表删除失败
     */
    public int deleteInfoById(String borrowBookID, String account){
        Connection con=JDBCUtil.getConnection();
        String deleteInfoSqlStr = "DELETE FROM info WHERE borrowBookID=?";
        if(con!=null){
            try {
                if(findById(borrowBookID, account) == null)
                    return 0;
                PreparedStatement preparedStatement=con.prepareStatement(deleteInfoSqlStr);
                preparedStatement.setString(1, borrowBookID);
                System.out.println("Delete Successfully");
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
     * 该方法根据图书id号和用户从数据库中精确查找借阅信息，返回一个对象
     * @param id 要查找的图书id号
     * @param account 要查找的账户
     * @return 指定id的图书对象
     *
     * 建议不在界面调用该方法
     */
    public Info findById(String id, String account){
        Connection con=JDBCUtil.getConnection();
        String selectAllSqlStr="SELECT * FROM info where borrowBookID='"+id+"' and account='"+account+"'";
        if(con!=null){
            try {
                ResultSet rs=con.createStatement().executeQuery(selectAllSqlStr);
                List<Info> infoList=rsToInfo(rs);
                if(infoList!=null && infoList.size()!=0)return infoList.get(0);
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        return null;
    }

    /**
     * 该方法用于将借阅查询的结果集转换成借阅列表
     * @param rs 要转换的借阅结果集
     * @return 转换好的借阅列表
     *
     * 建议不在界面调用该方法
     */
    private List<Info> rsToInfo(ResultSet rs) {
        List<Info> infoList=new ArrayList<>();
        try {
            while (rs.next()) {
                String account = rs.getString(1);
                String borrowBookID = rs.getString(2);
                String name = rs.getString(3);
                String borrowDate = rs.getString(4);
                String shouldReturnDate = rs.getString(5);
                String returnDate = rs.getString(6);
                int renewTimes = rs.getInt(7);
                Info anInfo=new Info(account, borrowBookID, name, borrowDate, shouldReturnDate, returnDate, renewTimes);
                infoList.add(anInfo);
            }
            return infoList;
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