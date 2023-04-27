package org.example.ejemplo1clase;

import java.sql.*;

public class Main1 {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/test";
        String user= "root";
        String password="andreS0503";
        try(Connection conn = DriverManager.getConnection(url,user,password);
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT * FROM products");

        ) {
            while (resultSet.next()){
                System.out.print(resultSet.getInt("id"));
                System.out.print("|");
                System.out.print(resultSet.getString("product_name"));
                System.out.print("|");
                System.out.print(resultSet.getDouble("price"));
                System.out.print("|");
                System.out.print(resultSet.getDate("date_register"));
                System.out.println("\n");
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }

    }
}
