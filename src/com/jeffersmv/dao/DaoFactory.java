package com.jeffersmv.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DaoFactory {

    public DaoFactory() throws DaoException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DaoException(e);
        }
    }

    public static Connection getConnection() throws DaoException {
        Properties prop = new Properties();
        Connection connection;
        try {
//          prop.load(new FileInputStream("\\com\\jeffersmv\\resource\\connect.properties"));
            prop.load(DaoFactory.class.getClassLoader().getResourceAsStream("connect.properties"));
//            prop.load(inputStream);
//          InputStream inputStream =  this.getClass().getClassLoader().getResourceAsStream("connect.properties");
//          Scanner s = new Scanner(this.getClass().getClassLoader().getResourceAsStream("src//FileIsTest"));

            connection = DriverManager.getConnection(prop.getProperty("dburl"), prop.getProperty("user"), prop.getProperty("password"));
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        if (connection == null) return;
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
