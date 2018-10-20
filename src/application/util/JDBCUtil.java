package application.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {



    public static Connection getConnection() {
        Connection con;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            con = DriverManager.getConnection("jdbc:derby:","DEMO","123456");

        } catch (Exception e) {
            return null;
        }
        return con;
    }

    /*public void demo() throws Exception {
        Connection con = getConnection();
        Statement stmt = con.createStatement();

    }*/

    public static int createDB() {
        Connection con = null;
        if (!dbExists()) {// 如果数据库不存在，则创建数据库并插入测试数据。
            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                con = DriverManager.getConnection("jdbc:derby:" +"DEMO" + ";create=true");

                String createBooksTableSqlStr = "CREATE TABLE books (bookID VARCHAR(20) NOT NULL, name VARCHAR(30), " +
                        "type VARCHAR(5), writer VARCHAR(30), press VARCHAR(20), price DOUBLE, isInLibrary VARCHAR(5))";

                String createReaderTableSqlStr = "CREATE TABLE reader (account VARCHAR(20) NOT NULL," +
                        " password VARCHAR(20), role VARCHAR(10), name VARCHAR(30), contact VARCHAR(20) )";

                String createInfoTableSqlStr = "CREATE TABLE info (account VARCHAR(20) NOT NULL, bookID VARCHAR(20), " +
                        "name VARCHAR(5), borrowDate DATE, shouldReturnDate DATE, returnDate DATE, renewTimes int)";

                String insertBooksSqlStr = "INSERT INTO books VALUES ('A001001','Hadoop权威指南','A','Tom White','清华大学出版社','99.00','Y')," +
                        "('A001002','Java编程思想','A','Bruce Eckel','机械工业出版社','108.00','Y'),('A002001','Python学习手册','A','Mark Lutz','机械工业出版社','119.00','Y')," +
                        "('A003001','深度学习','A','Lan Goodfellow','人民邮电出版社','168.00','Y'),('A003002','机器学习','A','周志华','清华大学出版社','88.00','Y')," +
                        "('A003003','统计学习方法','A','李航','清华大学出版社','38.00','Y'),('A004001','计算机网络','A','冯博琴','高等教育出版社','46.50','Y')," +
                        "('A004002','数据结构','A','叶核亚','电子工业出版社','42.00','N'),('A004003','操作系统','A','William Stallings','电子工业出版社','79.80','Y')," +
                        "('A004004','离散数学','A','Rosen','机械工业出版社','59.00','Y'),('B001001','概率论与数理统计','B','夏强 刘金山','人民邮电出版社','49.80','Y')," +
                        "('B002001','高等数学学习指导','B','倪科社 张昕','广东科技出版社','36.00','Y'),('B002002','高等数学辅导','B','张天德','沈阳出版社','32.80','Y')," +
                        "('B002003','高等数学','B','郭正光 方明亮','高等教育出版社','41.80','Y'),('B003001','毛中特','B','编写组','高等教育出版社','25.00','Y')," +
                        "('C001001','东周列国志','C','冯梦龙','清华大学出版社','99.00','Y'),('C002001','在细雨中呼喊','B','余华','作家出版社','25.00','Y')," +
                        "('C003001','一只特立独行的猪','C','王小波','北京工业大学出版社','25.00','Y'),('C003002','沉默的大多数','C','王小波','陕西师范大学出版社','32.00','Y')," +
                        "('D001001','近代中国的新陈代谢','D','陈旭麓','三联书店','69.00','Y'),('D002001','旧制度与大革命','D','托克维尔','商务印书馆','39.00','Y')," +
                        "('E001001','家常菜谱','E','于谦','三联书店','19.20','Y')";

                String insertReaderSqlStr = "INSERT INTO reader VALUES ('Admin','admin','S','default','default'),('Librarian','librarian','L','default','default'),('201725040120','123456','U','刘照赫','13888888888')";

                String insertInfoStudentSqlStr = "INSERT INTO info(account, bookID, name, borrowDate, shouldReturnDate) VALUES ('201725040120','A004002','数据结构', '2018-10-1', '2018-11-1'),";

                con.setAutoCommit(false);// 打开事务，以保证所有的数据库操作执行都成功或都失败。

                processUpdateStatement(createReaderTableSqlStr);
                processUpdateStatement(insertBooksSqlStr);
                processUpdateStatement(createBooksTableSqlStr);
                processUpdateStatement(createInfoTableSqlStr);
                processUpdateStatement(insertReaderSqlStr);
                processUpdateStatement(insertInfoStudentSqlStr);
                batchInsertStudents();

                con.commit();// 提交事务
                con.setAutoCommit(true);// 关闭事务
                return 1;

            } catch (SQLException bue) {
                try {
                    con.rollback();// 执行出错，回滚事务
                    printSQLException((SQLException) bue);
                } catch (SQLException se) {
                    printSQLException(se);
                }
                return -1;// 创建不成功，返回-1。
            } catch (ClassNotFoundException e) {
                System.err.println("JDBC Driver " + "org.apache.derby.jdbc.EmbeddedDriver" + " not found in CLASSPATH");
                return -1;// 创建不成功，返回-1。
            } finally {
                closeConnection(con);
            }
        }
        return 0;// 数据库已经存在，返回0，不做任何操作.
    }

    /**
     * 该方法以更新的方式执行传递过来的sql串。
     *
     * @param sql 要执行的sql串。
     * @return 受影响的记录数。返回-1代表执行sql语句失败。
     * @throws SQLException
     *             抛出的SQL异常
     */
    private static int processUpdateStatement(String sql) throws SQLException {
        Connection con = getConnection();
        if (con != null) {
            Statement stmt = con.createStatement();
            int affectedRows = stmt.executeUpdate(sql);
            closeConnection(con);
            return affectedRows;
        }
        return -1;
    }

    /**
     * 该方法以查询的方式执行传递过来的sql串。
     *
     * @param sql 要执行的sql串。
     * @return 返回查询结果记录集。返回null代表查询失败
     * @throws SQLException
     *             抛出的SQL异常
     */
    private static ResultSet processQueryStatement(String sql) throws SQLException {
        Connection con = getConnection();
        if (con != null) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            stmt.close();
            return rs;
        }
        return null;
    }

    /**
     * 执行批量插入数据到students表的操作。
     *
     * @throws SQLException
     *             抛出的SQL异常
     */
    private static void batchInsertStudents() throws SQLException {
        String insertStudentsSqlStr = "INSERT INTO students(id, name, birthday, height) " + "VALUES(?, ?, ?, ?)";

        String[] numbers = { "001", "002", "003", "004", "005", "006" };

        String[] names = { "张三", "李四", "王五", "赵六", "钱七", "孙八" };

        Date[] birthdays = { Date.valueOf("198                                          `8-12-20"), Date.valueOf("1989-1-10"), Date.valueOf("1978-11-22"),
                Date.valueOf("1981-5-12"), Date.valueOf("1998-6-27"), Date.valueOf("1973-11-12") };

        double[] heights = { 1.75, 1.65, 1.78, 1.71, 1.63, 1.85 };

        Connection con = getConnection();
        PreparedStatement stmt = con.prepareStatement(insertStudentsSqlStr);

        for (int i = 0; i < numbers.length; i++) {
            stmt.setString(1, numbers[i]);
            stmt.setString(2, names[i]);
            stmt.setDate(3, birthdays[i]);
            stmt.setDouble(4, heights[i]);
            stmt.addBatch();
        }

        stmt.executeBatch();
        closeConnection(con);
    }

    /**
     * 打印SQL异常信息
     *
     * @param e
     *            要打印的SQL异常
     */
    private static void printSQLException(SQLException e) {
        while (e != null) {
            System.out.print("SQLException: State:   " + e.getSQLState());
            System.out.println("Severity: " + e.getErrorCode());
            System.out.println(e.getMessage());
            e = e.getNextException();
        }
    }

    private static boolean dbExists() {
        boolean exists = false;
        Connection con = getConnection();
        if (con != null)// 如果con不为null,则连接成功，表明数据库已经存在，设置exists变量为true
            exists = true;
        closeConnection(con);
        return (exists);
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null && !con.isClosed())
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
