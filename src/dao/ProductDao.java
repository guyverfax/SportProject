package dao;

import model.Member;
import model.Product;
import java.util.List;

public interface ProductDao {
    // Create
    boolean create(Product product);

    // Read
    Product readById(int id);
    Product readByProductno(String productno);
    List<Product> readAll();
    List<Product> readByKeyword(String keyword);
    String[] readByProductno();
    String[] readByProductnoAndProductname();
    boolean isExitsByProductno(String productno);

    // Update
    boolean update(Product product);
    boolean update(String productno, int amount);

    // Delete
    boolean delete(int id);
    boolean delete(String productno);
}