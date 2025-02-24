package service;

import java.util.List;

import model.Product;

public interface ProductService {
    // Create
    boolean create(Product product);

    // Read
    Product readById(int id);
    Product readByProductno(String productno);
    List<Product> readAll();

    // Update
    boolean update(Product product);

    // Delete
    boolean delete(int id);
}