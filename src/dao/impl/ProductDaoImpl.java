package dao.impl;

import dao.ProductDao;
import model.Member;
import model.Product;
import util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    public static void main(String[] args) {
        ProductDao productDao = new ProductDaoImpl();
        /*
        // Test Create
        Product newProduct = new Product("P004", "產品D", 150.0, 10);
        boolean created = productDao.create(newProduct);
        System.out.println("Create: " + created);

        // Test Read by ID
        Product productById = productDao.readById(1);
        System.out.println("Read by ID: " + productById);

        // Test Read by Productno
        Product productByProductno = productDao.readByProductno("P002");
        System.out.println("Read by Productno: " + productByProductno);

        // Test Read All
        List<Product> allProducts = productDao.readAll();
        System.out.println("Read All: " + allProducts);
		
        // Test Update
        Product updatedProduct = new Product("P001", "產品A更新", 120, 20);
        boolean updated = productDao.update(updatedProduct);
        System.out.println("Update: " + updated);

        // Test Delete
        boolean deleted = productDao.delete(4);
        System.out.println("Delete: " + deleted);
		*/
        
        //System.out.println(readByProductno());
        //List<Product> allProducts = productDao.readByKeyword("球");
        //System.out.println("Read All: " + allProducts);
        
        // Test update(productno, amount)
        //boolean updated = productDao.update("P001",10);
        //System.out.println("Update: " + updated);
    }

    @Override
    public boolean create(Product product) {
        String sql = "INSERT INTO product (productno, productname, price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getProductno());
            pstmt.setString(2, product.getProductname());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Product readById(int id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapToProduct(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product readByProductno(String productno) {
        String sql = "SELECT * FROM product WHERE productno = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productno);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapToProduct(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> readAll() {
        String sql = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(mapToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
	public List<Product> readByKeyword(String keyword) {
		List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE productno LIKE ? OR productname LIKE ?";
        
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchKeyword = "%" + keyword + "%";
            pstmt.setString(1, searchKeyword);
            pstmt.setString(2, searchKeyword);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                	products.add(mapToProduct(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
	}
    
	@Override
	public String[] readByProductno() {
		String[] productno = readAll()
			   .stream()
               .map(Product::getProductno)
               .toArray(String[]::new);
		return productno;
	}
	
	@Override
	public boolean isExitsByProductno(String productno) {
		if (readByProductno(productno)!=null)
		{
			return true;
		}
		return false;
	}
	
    public boolean update(Product product) {
        String sql = "UPDATE product SET productname = ?, price = ?, quantity = ? WHERE productno = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getProductname());
            pstmt.setInt(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setString(4, product.getProductno());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public boolean update(String productno, int amount) {
		String sql = "update product set quantity=? where productno=?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setString(2, productno);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM product WHERE id = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
	public boolean delete(String productno) {
    	 String sql = "DELETE FROM product WHERE productno = ?";
         try (Connection conn = DbConnection.getDb();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setString(1, productno);
             int affectedRows = pstmt.executeUpdate();
             return affectedRows > 0;
         } catch (SQLException e) {
             e.printStackTrace();
             return false;
         }
	}
    
    private Product mapToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setProductno(rs.getString("productno"));
        product.setProductname(rs.getString("productname"));
        product.setPrice(rs.getInt("price"));
        product.setQuantity(rs.getInt("quantity"));
        return product;
    }
    
}