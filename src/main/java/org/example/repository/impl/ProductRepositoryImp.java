package org.example.repository.impl;


import org.example.Main;
import org.example.model.Product;
import org.example.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductRepositoryImp implements Repository {
    private Connection getConnection() throws SQLException {
        return Main.getInstance();
    }

    private Product createProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setProduct_name(resultSet.getString("product_name"));
        product.setPrice(resultSet.getDouble("price"));
        product.setDate_register(resultSet.getDate("date_register").toLocalDate());
        return product;
    }
    @Override
    public List<Product> list() {
        List<Product> products = new ArrayList<>();
        try (Statement statement=getConnection().createStatement();
             ResultSet resultSet=statement.executeQuery("SELECT * FROM products")
        ){
            while (resultSet.next()) {
                Product product = createProduct(resultSet);
                products.add(product);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product byId(Long id) {
        Product product = null;
        try (PreparedStatement preparedStatement=getConnection().prepareStatement("SELECT * FROM products WHERE id =?")){
            preparedStatement.setLong(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) {
                product = createProduct(resultSet);
            }
            resultSet.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void save(Object o) {
        Product product = (Product) o;
        try (PreparedStatement preparedStatement=getConnection().prepareStatement("INSERT INTO products(product_name,price,date_register) VALUES (?,?,?)")){
            preparedStatement.setString(1,product.getProduct_name());
            preparedStatement.setLong(2,product.getPrice().longValue());
            preparedStatement.setDate(3,Date.valueOf(product.getDate_register()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        Product product = null;
        try (PreparedStatement preparedStatement=getConnection().prepareStatement("DELETE FROM products WHERE id =?")){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Long id,Object o) {
        Product product = (Product) o;
        try (PreparedStatement preparedStatement=getConnection().prepareStatement("UPDATE products SET product_name=? ,price=?,date_register=? where id=?")){
            preparedStatement.setString(1,product.getProduct_name());
            preparedStatement.setLong(2,product.getPrice().longValue());
            preparedStatement.setDate(3,Date.valueOf(product.getDate_register()));
            preparedStatement.setLong(4,id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

