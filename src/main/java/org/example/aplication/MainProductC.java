package org.example.aplication;

import org.example.Main;
import org.example.model.Category;
import org.example.model.ProductCategory;
import org.example.service.impl.ProductCServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class MainProductC {
    public static void main(String[] args) {
        try (Connection conn = Main.getInstance()){
            ProductCServiceImpl productService=new ProductCServiceImpl();
            productService.getlist();
            productService.getbyId(1L);
            productService.delete(18L);
            productService.delete(17L);
            productService.delete(16L);
            productService.getlist();
            productService.save(new ProductCategory("Keke",5000.0, LocalDate.of(2023,03,27),new Category(1,"food")));
            productService.getlist();
            productService.update(1L,new ProductCategory("Max Steel",50000.0, LocalDate.of(2023,03,27),new Category(2,"toy")));
            productService.getlist();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
