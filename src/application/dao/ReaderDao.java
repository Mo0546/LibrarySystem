package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.Reader;
import application.util.JDBCUtil;
import application.util.MyDiolog;
import javafx.scene.control.Alert;

import static application.model.Security.getMd5;

public class ReaderDao {

    /**
     * 登录验证功能，传入用户名和密码，在数据库中查找，如果找到了，返回用户类型，没找到则返回N.
     * @param act 用户名
     * @param psw 密码
     */
    public static String Login(String act, String psw){
        String result = "N";
        Connection con=JDBCUtil.getConnection();
        String sql = "select * from reader where account='"+act+"' and password='"+psw+"'";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            List<Reader> readerList=new ArrayList<>();
            if(rs.next()){
                String account = rs.getString(1);
                String password = rs.getString(2);
                String role = rs.getString(3);
                String name = rs.getString(4);
                String contact = rs.getString(5);
                Reader aReader=new Reader(account, password, role, name, contact);
                result = aReader.getRole();
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //stm.close();
            //rs.close();
            JDBCUtil.closeConnection(con);
        }
        return result;
    }


    /**
     *
     * 注册账号，将用户对象保存到数据库
     * 使用preparedStatement防止sql注入
     * @param reader 要保存到数据库的图书对象
     * @return 返回0代表数据库中已有该用户，取消保存。返回1代表保存成功，返回-1代表保存不成功。
     *
     */
    public static int Register1(Reader reader){
        Connection con=JDBCUtil.getConnection();
        String sql = "INSERT INTO reader VALUES(?, ?, ?, ?, ?)";
        if(con!=null){
            try {
                if(findById(reader.getUsersID())!=null){
                    MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"数据已经存在！");
                    diolog.mshow();
                    System.out.println("数据已存在，不能重复插入");
                    return 0;
                }
                PreparedStatement preparedStatement=con.prepareStatement(sql);
                preparedStatement.setString(1, reader.getUsersID());
                preparedStatement.setString(2, reader.getPassword());
                preparedStatement.setString(3, reader.getRole());
                preparedStatement.setString(4, reader.getName());
                preparedStatement.setString(5, reader.getContact());
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
     * 注册账号的函数，传入各String型参数
     */
    public static int Register2(String account, String password, String role, String name, String contact) {
        Connection con=JDBCUtil.getConnection();
        String sql = "insert into reader values(?,?,?,?,?)";
        PreparedStatement stm = null;
        int result = 0;
        if(findById(account)!=null){
            System.out.println("数据已存在，不能重复插入");
            MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"数据已存在，不能重复插入");
            diolog.mshow();
            return 0;
        }
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, account);
            stm.setString(2, password);
            stm.setString(3, role);
            stm.setString(4, name);
            stm.setString(5, contact);
            result = stm.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return -1;
    }

    /**
     * 该方法调用findById()用于更新数据库中指定用户的信息
     * @param reader 要更新的用户对象
     * @return 返回0代表该用户不存在，返回1代表更新成功，返回-1代表更新失败
     *
     * 修改除id和Password外的所有信息
     */
    public int update(Reader reader){
        Connection con=JDBCUtil.getConnection();
        String updateCourseSqlStr = "UPDATE reader SET role=?, name=?, contact=? WHERE account=?";
        if(con!=null){
            try {
                if(findById(reader.getUsersID())==null) {
                    MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"图书不存在");
                    diolog.mshow();
                    return 0;}
                PreparedStatement preparedStatement=con.prepareStatement(updateCourseSqlStr);
                preparedStatement.setString(1, reader.getRole());
                preparedStatement.setString(2, reader.getName());
                preparedStatement.setString(3, reader.getContact());
                preparedStatement.setString(4, reader.getUsersID());
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
     * 该方法调用findById()用于更新数据库中指定用户的信息
     * @param reader 要更新的用户对象
     * @return 返回0代表该用户不存在，返回1代表更新成功，返回-1代表更新失败
     *
     * 修改Password
     */
    public int updatePassword(Reader reader){
        Connection con=JDBCUtil.getConnection();
        String updateCourseSqlStr = "UPDATE reader SET password=? WHERE account=?";
        if(con!=null){
            try {
                if(findById(reader.getUsersID())==null) {
                    MyDiolog diolog = new MyDiolog(Alert.AlertType.ERROR,"图书不存在");
                    diolog.mshow();
                    return 0;}
                PreparedStatement preparedStatement=con.prepareStatement(updateCourseSqlStr);
                String str1 = getMd5(reader.getPassword());
                preparedStatement.setString(1, str1);
                preparedStatement.setString(2, reader.getUsersID());
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
     * 该方法用于从数据库删除指定account的用户
     */
    public int deleteAccountById(String account){
        Connection con=JDBCUtil.getConnection();
        String deleteReaderSqlStr = "DELETE FROM reader WHERE account=?";
        if(con!=null){
            try {
                if(findById(account)==null) return 0;
                PreparedStatement preparedStatement=con.prepareStatement(deleteReaderSqlStr);
                preparedStatement.setString(1, account);
                System.out.println("Successfully Delete");
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
     * 该方法根据账户账号从数据库中精确查找账户，返回一个对象
     * @param account 要查找的账户账号
     * @return 指定账户对象
     */
    public static Reader findById(String account){
        Connection con=JDBCUtil.getConnection();
        String selectAllSqlStr="SELECT * FROM reader where account='"+account+"'";
        if(con!=null){
            try {
                ResultSet rs=con.createStatement().executeQuery(selectAllSqlStr);
                List<Reader> readerList=rsToReader(rs);
                if(readerList!=null && readerList.size()!=0)return readerList.get(0);
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        return null;
    }

    /**
     * 该方法查询并返回数据库中的所有用户。
     * 该方法可能返回大量的用户对象，有可能造成网络拥堵或机器运行缓慢，请谨慎使用。
     * @return 返回数据库中的所有用户列表。返回null代表查询失败。
     */
    public List<Reader> getListOFReader(){
        Connection con=JDBCUtil.getConnection();
        String selectAllSqlStr="SELECT * FROM reader";
        if(con!=null){
            try {
                ResultSet rs=con.createStatement().executeQuery(selectAllSqlStr);
                List<Reader> readerList=rsToReader(rs);
                return readerList;
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        return null;
    }

    /**
     * 该方法查询并返回数据库中的所有用户
     *                                （不包括管理员）。
     * 该方法可能返回大量的用户对象，有可能造成网络拥堵或机器运行缓慢，请谨慎使用。
     * @return 返回数据库中的所有用户列表。返回null代表查询失败。
     */
    public List<Reader> getListOFReaderWithoutSuperAdmin(){
        Connection con=JDBCUtil.getConnection();
        String selectAllSqlStr="SELECT * FROM reader where role <> 'S'";
        if(con!=null){
            try {
                ResultSet rs=con.createStatement().executeQuery(selectAllSqlStr);
                List<Reader> readerList;
                readerList = rsToReader(rs);
                return readerList;
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        return null;
    }

    /**
     * 该方法查询并返回数据库中的所有用户
     *                                （只有Users）。
     * 该方法可能返回大量的用户对象，有可能造成网络拥堵或机器运行缓慢，请谨慎使用。
     * @return 返回数据库中的所有用户列表。返回null代表查询失败。
     */
    public List<Reader> getListOFUsers(){
        Connection con=JDBCUtil.getConnection();
        String selectAllSqlStr="SELECT * FROM reader where role ='U'";
        if(con!=null){
            try {
                ResultSet rs;
                rs = con.createStatement().executeQuery(selectAllSqlStr);
                List<Reader> readerList;
                readerList = rsToReader(rs);
                return readerList;
            } catch (SQLException e) {
                processSqlError(e);
            }finally{
                JDBCUtil.closeConnection(con);
            }
        }
        return null;
    }


    /**
     * 该方法用于将账户的结果集转换成账户列表
     * @param rs 要转换的账户结果集
     * @return 转换好的账户列表
     */
    private static List<Reader> rsToReader(ResultSet rs) {
        List<Reader> booksList=new ArrayList<>();
        try {
            while (rs.next()) {
                String account = rs.getString(1);
                String password = rs.getString(2);
                String role = rs.getString(3);
                String name = rs.getString(4);
                String contact = rs.getString(5);
                Reader aReader=new Reader(account, password, role, name, contact);
                booksList.add(aReader);
            }
            return booksList;
        } catch (SQLException e) {
            processSqlError(e);
        }
        return null;
    }

    /**
     * 该方法用于处理SQL错误
     * @param e 处理SQL语句过程中产生的异常对象
     */
    private static void processSqlError(Exception e) {
        e.printStackTrace();
    }

}
