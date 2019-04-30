package com.razu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnections {

    public static void main(String[] args) {
        // System.out.println("PostgreSqlConnection " + getPostgreSqlConnection());
        // System.out.println("MySqlConnection " + getMySqlConnection());
        // select(getPostgreSqlConnection(), "SELECT * FROM users");
         select(getMySqlConnection(), "SELECT * FROM users");
//        insert(getMySqlConnection(),
//                ("INSERT INTO users (first_name, last_name, email) VALUES ('fname','lname','email')"));
    }

    public static Connection getMySqlConnection() {
        Connection connection = null;
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ db_test", "root", "123456");
        } catch (SQLException | ClassNotFoundException e) {
        }
        return connection;
    }

    public static Connection getPostgreSqlConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test_db", "postgres", "123456");
        } catch (SQLException | ClassNotFoundException e) {
        }
        return connection;
    }

    public static void select(Connection con, String query) {
        try {
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getLong("id"));
                System.out.println(rs.getString("first_name"));
                System.out.println(rs.getString("last_name"));
                System.out.println(rs.getString("email"));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
        } finally {
        }
    }

    public static void insert(Connection con, String query) {
        try {
            PreparedStatement st = con.prepareStatement(query);
            int status = st.executeUpdate();
            System.out.println("status" + status);
            st.close();
        } catch (SQLException e) {
        } finally {
        }
    }
}
