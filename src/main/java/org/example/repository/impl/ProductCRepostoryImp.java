package org.example.repository.impl;

import org.example.Main;
import org.example.model.Category;
import org.example.model.ProductCategory;
import org.example.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCRepostoryImp implements Repository {
    private Connection getConnection() throws SQLException {
        return Main.getInstance();
    }

    private ProductCategory createProduct(ResultSet resultSet) throws SQLException {
        ProductCategory product = new ProductCategory();
        product.setId(resultSet.getInt("id"));
        product.setProduct_name(resultSet.getString("product_name"));
        product.setPrice(resultSet.getDouble("price"));
        product.setDate_register(resultSet.getDate("date_register").toLocalDate());
        Category category = new Category();
        category.setId(resultSet.getInt("id_category"));
        category.setName(resultSet.getString("category_name"));
        product.setCategory(category);
        return product;
    }
    @Override
    public List<ProductCategory> list() {
        List<ProductCategory> products = new ArrayList<>();
        try (Statement statement=getConnection().createStatement();
             ResultSet resultSet=statement.executeQuery("SELECT p.id,p.product_name,p.price,p.date_register,p.id_category,c.category_name FROM products_category as p join categories as c on(p.id_category=c.id)")
        ){
            while (resultSet.next()) {
                ProductCategory product = createProduct(resultSet);
                products.add(product);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public ProductCategory byId(Long id) {
        ProductCategory product = null;
        try (PreparedStatement preparedStatement=getConnection().prepareStatement("SELECT p.id,p.product_name,p.price,p.date_register,p.id_category,c.category_name FROM products_category as p join categories as c on(p.id_category=c.id) where p.id=?")){
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
        ProductCategory product = (ProductCategory) o;
        try (PreparedStatement preparedStatement=getConnection().prepareStatement("INSERT INTO products_category(product_name,price,date_register,id_category) VALUES (?,?,?,?)")){
            preparedStatement.setString(1,product.getProduct_name());
            preparedStatement.setLong(2,product.getPrice().longValue());
            preparedStatement.setDate(3,Date.valueOf(product.getDate_register()));
            preparedStatement.setLong(4,product.getCategory().getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        ProductCategory product = null;
        try (PreparedStatement preparedStatement=getConnection().prepareStatement("DELETE FROM products_category WHERE id =?")){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Long id,Object o) {
        ProductCategory product = (ProductCategory) o;
        try (PreparedStatement preparedStatement=getConnection().prepareStatement("UPDATE products_category SET product_name=? ,price=?,date_register=?,id_category=? where id=?")){
            preparedStatement.setString(1,product.getProduct_name());
            preparedStatement.setLong(2,product.getPrice().longValue());
            preparedStatement.setDate(3,Date.valueOf(product.getDate_register()));
            preparedStatement.setLong(4,product.getCategory().getId());
            preparedStatement.setLong(5,id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
