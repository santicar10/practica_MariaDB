package org.example.aplication;

import org.example.Main;
import org.example.model.Product;
import org.example.service.impl.ProductServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class MainProduct {
    public static void main(String[] args) {
        try (Connection conn = Main.getInstance()){
            ProductServiceImpl productService=new ProductServiceImpl();
            productService.getlist();
            productService.getbyId(1L);
            productService.delete(2L);
            productService.getlist();
            productService.save(new Product("Keke",5000.0, LocalDate.of(2023,03,27)));
            productService.getlist();
            productService.update(1L,new Product("Empanada",500.0, LocalDate.of(2023,03,27)));
            productService.getlist();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
