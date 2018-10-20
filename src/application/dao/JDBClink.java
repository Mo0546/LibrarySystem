package application.dao;

import application.util.JDBCUtil;

import java.sql.Connection;

public class JDBClink {

    private final static String url = "jdbc:derby";
    private final static String user = "demo";
    private final static String password = "123456";

    Connection con = JDBCUtil.getConnection();


}
