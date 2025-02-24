package service.impl;

import dao.ProductDao;
import dao.impl.ProductDaoImpl;
import model.Product;
import service.ProductService;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl() {
        this.productDao = new ProductDaoImpl();
    }

    public static void main(String[] args) {
        ProductService productService = new ProductServiceImpl();

        // Test Create
        Product newProduct = new Product("P004", "產品D", 150, 10);
        boolean created = productService.create(newProduct);
        System.out.println("Create: " + created);

        // Test Read by ID
        Product productById = productService.readById(1);
        System.out.println("Read by ID: " + productById);

        // Test Read by Productno
        Product productByProductno = productService.readByProductno("P002");
        System.out.println("Read by Productno: " + productByProductno);

        // Test Read All
        List<Product> allProducts = productService.readAll();
        System.out.println("Read All: " + allProducts);

        // Test Update
        Product updatedProduct = new Product("P001", "產品A更新", 120, 20);
        boolean updated = productService.update(updatedProduct);
        System.out.println("Update: " + updated);

        // Test Delete
        boolean deleted = productService.delete(4);
        System.out.println("Delete: " + deleted);
    }

    @Override
    public boolean create(Product product) {
        return productDao.create(product);
    }

    @Override
    public Product readById(int id) {
        return productDao.readById(id);
    }

    @Override
    public Product readByProductno(String productno) {
        return productDao.readByProductno(productno);
    }

    @Override
    public List<Product> readAll() {
        return productDao.readAll();
    }

    @Override
    public boolean update(Product product) {
        return productDao.update(product);
    }

    @Override
    public boolean delete(int id) {
        return productDao.delete(id);
    }
}