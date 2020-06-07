package ru.gb.chat.server;

import java.sql.*;

public class SqlClient {

    private static Connection connection;
    private static Statement statement;

    synchronized static void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:chat-server/chat-db.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized static void disconnect(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    synchronized static String getNickname(String login, String password){
        try {
            ResultSet rs =  statement.executeQuery(String.format("select nickname from users where login = '%s' and password = '%s'",
                    login, password));
            if(rs.next()) {
                return rs.getString("nickname");
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        return null;
    }
}
