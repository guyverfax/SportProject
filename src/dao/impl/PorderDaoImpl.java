package dao.impl;

import dao.PorderDao;
import model.Member;
import model.Porder;
import model.PorderSummary;
import model.Product;
import util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PorderDaoImpl implements PorderDao {

    public static void main(String[] args) {
        PorderDao porderDao = new PorderDaoImpl();

        // Test Create
        //Porder newPorder = new Porder("O004", "P002", "M001", "E001", 5);
        //boolean created = porderDao.create(newPorder);
        //System.out.println("Create: " + created);

        /*
        Porder newOrder = new Porder("O2025021412000151", "P001", "M001", "E001", 1);
        boolean success = porderDao.createUpdateStock(newOrder);
        if (success) {
            System.out.println("訂單建立成功，庫存已更新！");
        } else {
            System.out.println("訂單建立失敗，可能是庫存不足或系統錯誤！");
        }
        */
        
        //List<Porder> orders = List.of(
        //        new Porder("O001", "P001", "M001", "E001", 1),  // ✅ 可購買
        //        new Porder("O001", "P002", "M001", "E001", 4),  // ❌ 失敗 (庫存不足)
        //        new Porder("O001", "P001", "M001", "E001", 5)   // ❌ 失敗 (庫存不足)
        //);

        /*整筆判斷
        boolean success = porderDao.createUpdateStock(orders);
        if (success) {
            System.out.println("訂單建立成功，庫存已更新！");
        } else {
            System.out.println("訂單建立失敗，可能是庫存不足或系統錯誤！");
        }
        */
        
        // Test Read by ID
        //Porder porderById = porderDao.readById(2);
        //System.out.println("Read by ID: " + porderById);

        // Test Read by Orderno
        //Porder orderByOrderno = porderDao.readAllByPorderno("O2025021315245701");
        //System.out.println("Read by Orderno: " + orderByOrderno);

        // Test Read by Memberno
        //List<Orders> ordersByMemberno = ordersDao.readByMemberno("M002");
        //System.out.println("Read by Memberno: " + ordersByMemberno);

        // Test Read by Employno
        //List<Orders> ordersByEmployno = ordersDao.readByEmployno("E002");
        //System.out.println("Read by Employno: " + ordersByEmployno);

        // Test Read by Employno
        //List<Porder> orders = porderDao.readByMemberno("M111");
        //System.out.println("Read by Memberno: " + orders);
        
        //Test Read All
        //List<Porder> allOrders = porderDao.readAll();
        //System.out.println("Read All: " + allOrders);

        // Test Update
        //Orders updatedOrder = new Orders("ORD001", "M001", "E001");
        //boolean updated = ordersDao.update(updatedOrder);
        //System.out.println("Update: " + updated);

        //Test Delete
        //boolean deleted = porderDao.delete(11);
        //System.out.println("Delete: " + deleted);

        //Test readAmount(productno
        System.out.println(porderDao.readAmount("P001"));
    }

    @Override
    public boolean create(Porder porder) {
        String sql = "insert into porder (porderno, productno, memberno, employno, amount) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, porder.getPorderno());
            pstmt.setString(2, porder.getProductno());
            pstmt.setString(3, porder.getMemberno());
            pstmt.setString(4, porder.getEmployno());
            pstmt.setInt(5, porder.getAmount());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public boolean createUpdateStock(Porder porder) {
        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement updateStockStmt = null;

        String orderSQL = "INSERT INTO porder (porderno, productno, memberno, employno, amount) VALUES (?, ?, ?, ?, ?)";
        String updateStockSQL = "UPDATE product SET quantity = quantity - ? WHERE productno = ? AND quantity >= ?";

        try {
            conn = DbConnection.getDb();
            conn.setAutoCommit(false); // 開啟交易模式

            // 1. 新增訂單
            orderStmt = conn.prepareStatement(orderSQL);
            orderStmt.setString(1, porder.getPorderno());
            orderStmt.setString(2, porder.getProductno());
            orderStmt.setString(3, porder.getMemberno());
            orderStmt.setString(4, porder.getEmployno());
            orderStmt.setInt(5, porder.getAmount());

            int orderResult = orderStmt.executeUpdate();
            if (orderResult == 0) {
                conn.rollback();
                return false;
            }

            // 2. 扣除庫存
            updateStockStmt = conn.prepareStatement(updateStockSQL);
            updateStockStmt.setInt(1, porder.getAmount());
            updateStockStmt.setString(2, porder.getProductno());
            updateStockStmt.setInt(3, porder.getAmount());

            int stockResult = updateStockStmt.executeUpdate();
            if (stockResult == 0) {
                conn.rollback();  // 如果庫存不足，回滾交易
                return false;
            }

            conn.commit(); // 確認交易
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); // 發生錯誤時回滾
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (orderStmt != null) orderStmt.close();
                if (updateStockStmt != null) updateStockStmt.close();
                if (conn != null) conn.setAutoCommit(true); // 恢復自動提交
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	@Override
	public boolean createUpdateStock(List<Porder> orders) {
	    Connection conn = null;
        PreparedStatement checkStockStmt = null;
        PreparedStatement orderStmt = null;
        PreparedStatement updateStockStmt = null;

        String checkStockSQL = "SELECT quantity FROM product WHERE productno = ?";
        String orderSQL = "INSERT INTO porder (porderno, productno, memberno, employno, amount) VALUES (?, ?, ?, ?, ?)";
        String updateStockSQL = "UPDATE product SET quantity = quantity - ? WHERE productno = ?";

        try {
            conn = DbConnection.getDb();
            conn.setAutoCommit(false); // **開啟交易模式**

            // **1. 先檢查所有產品的庫存是否足夠**
            for (Porder order : orders) {
                checkStockStmt = conn.prepareStatement(checkStockSQL);
                checkStockStmt.setString(1, order.getProductno());
                ResultSet rs = checkStockStmt.executeQuery();

                if (rs.next()) {
                    int currentStock = rs.getInt("quantity");
                    if (currentStock < order.getAmount()) {
                        conn.rollback(); // **有任何一項產品庫存不足，整筆訂單失敗**
                        return false;
                    }
                } else {
                    conn.rollback();
                    return false;
                }
            }

            // **2. 所有產品庫存足夠，才執行訂單建立 & 扣庫存**
            for (Porder order : orders) {
                // **建立訂單**
                orderStmt = conn.prepareStatement(orderSQL);
                orderStmt.setString(1, order.getPorderno());
                orderStmt.setString(2, order.getProductno());
                orderStmt.setString(3, order.getMemberno());
                orderStmt.setString(4, order.getEmployno());
                orderStmt.setInt(5, order.getAmount());
                orderStmt.executeUpdate();

                // **扣除庫存**
                updateStockStmt = conn.prepareStatement(updateStockSQL);
                updateStockStmt.setInt(1, order.getAmount());
                updateStockStmt.setString(2, order.getProductno());
                updateStockStmt.executeUpdate();
            }

            conn.commit(); // **確認交易**
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); // **發生錯誤時回滾**
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (checkStockStmt != null) checkStockStmt.close();
                if (orderStmt != null) orderStmt.close();
                if (updateStockStmt != null) updateStockStmt.close();
                if (conn != null) conn.setAutoCommit(true); // **恢復自動提交**
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}

    @Override
    public Porder readById(int id) {
        String sql = "select * from porder where id = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapToPorder(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Porder readByPorderno(String porderno) {
        String sql = "select * from porder where porderno = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, porderno);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapToPorder(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Porder> readByMemberno(String memberno) {
        String sql = "select * from porder where memberno = ?";
        List<Porder> porderList = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                porderList.add(mapToPorder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return porderList;
    }

    @Override
    public List<Porder> readByEmployno(String employno) {
        String sql = "select * from porder where employno = ?";
        List<Porder> porderList = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                porderList.add(mapToPorder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return porderList;
    }

	@Override
	public List<Porder> readByProductno(String productno) {
		String sql = "select * from porder where productno = ?";
        List<Porder> porderList = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                porderList.add(mapToPorder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return porderList;
	}

	@Override
	public List<Porder> readAllByPorderno(String porderno) {
		String sql = "select * from porder where porderno = ?";
        List<Porder> porderList = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, porderno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                porderList.add(mapToPorder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return porderList;
	}
	
    @Override
    public List<Porder> readAll() {
        String sql = "select * from porder";
        List<Porder> porderList = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                porderList.add(mapToPorder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return porderList;
    }
	
    @Override
	public List<Porder> readAll(String sql, List<Object> params) {
    	List<Porder> porderList  = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 設定參數
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	porderList.add(mapToPorder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return porderList;
	}
    
    @Override
	public boolean existsInPorder(String porderno, String productno) {
    	String sql = "SELECT COUNT(*) FROM porder WHERE porderno = ? AND productno = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, porderno);
            pstmt.setString(2, productno);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}
    
    @Override
	public int readAmount(String productno) {
    	 String sql = "SELECT SUM(amount) AS total_amount FROM porder WHERE productno = ?";
         int totalAmount = 0;

         try (Connection conn = DbConnection.getDb();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {

             pstmt.setString(1, productno);
             try (ResultSet rs = pstmt.executeQuery()) {
                 if (rs.next()) {
                     totalAmount = rs.getInt("total_amount");
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return totalAmount;
	}
    
    // update
    @Override
    public boolean update(Porder porder) {
        String sql = "update porder set porderno=?, productno=?, memberno=?, employno=? amount=? WHERE id=?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1, porder.getPorderno());
            pstmt.setString(2, porder.getProductno());
            pstmt.setString(3, porder.getMemberno());
            pstmt.setString(4, porder.getEmployno());
            pstmt.setInt(5, porder.getAmount());
            pstmt.setInt(6, porder.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public void updateAmount(String porderno, String productno, int amount) {
		String sql = "update porder set amount=? where porderno=? and productno=?";
		try (Connection conn = DbConnection.getDb();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, amount);
	            pstmt.setString(2, porderno);
	            pstmt.setString(3, productno);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
	
    @Override
    public boolean delete(int id) {
        String sql = "delete from porder where id=?";
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
	public void delete(String porderno, String memberno, String employno) {
		String sql = "delete from porder where porderno=? and memberno=? and employno=?";
        try (Connection conn = DbConnection.getDb();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, porderno);
            pstmt.setString(2, memberno);
            pstmt.setString(3, employno);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }		
	}
	
	@Override
	public void deletePorderItem(String porderno, String productno) {
		String sql = "delete from porder where porderno=? and productno=?";
        try (Connection conn = DbConnection.getDb();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, porderno);
            pstmt.setString(2, productno);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

    private Porder mapToPorder(ResultSet rs) throws SQLException {
        Porder porder = new Porder();
        porder.setId(rs.getInt("id"));
        porder.setPorderno(rs.getString("porderno"));
        porder.setProductno(rs.getString("productno"));
        porder.setMemberno(rs.getString("memberno"));
        porder.setEmployno(rs.getString("employno"));
        porder.setAmount(rs.getInt("amount"));
        return porder;
    }

	@Override
	public boolean deletePorder(String porderNo, String productNo, int amount) {
		Connection conn = null;
        PreparedStatement deleteStmt = null;
        PreparedStatement updateStockStmt = null;

        String deleteSQL = "DELETE FROM porder WHERE porderno = ? AND productno = ?";
        String updateStockSQL = "UPDATE product SET quantity = quantity + ? WHERE productno = ?";

        try {
            conn = DbConnection.getDb();
            conn.setAutoCommit(false); // 開啟交易

            // 1. 刪除訂單
            deleteStmt = conn.prepareStatement(deleteSQL);
            deleteStmt.setString(1, porderNo);
            deleteStmt.setString(2, productNo);
            int affectedRows = deleteStmt.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                return false;
            }

            // 2. 恢復庫存
            updateStockStmt = conn.prepareStatement(updateStockSQL);
            updateStockStmt.setInt(1, amount);
            updateStockStmt.setString(2, productNo);
            updateStockStmt.executeUpdate();

            conn.commit(); // 提交交易
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); // 回滾
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (deleteStmt != null) deleteStmt.close();
                if (updateStockStmt != null) updateStockStmt.close();
                if (conn != null) conn.setAutoCommit(true); // 恢復自動提交
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}

	@Override
	public boolean deleteOrderByPorderNo(String porderNo) {
		Connection conn = null;
	    PreparedStatement getOrdersStmt = null;
	    PreparedStatement deleteStmt = null;
	    PreparedStatement updateStockStmt = null;
	    ResultSet rs = null;

	    String getOrdersSQL = "SELECT productno, amount FROM porder WHERE porderno = ?";
	    String deleteSQL = "DELETE FROM porder WHERE porderno = ?";
	    String updateStockSQL = "UPDATE product SET quantity = quantity + ? WHERE productno = ?";

	    try {
	        conn = DbConnection.getDb();
	        conn.setAutoCommit(false); // **開始交易**

	        // **1. 取得該訂單的所有產品和數量**
	        getOrdersStmt = conn.prepareStatement(getOrdersSQL);
	        getOrdersStmt.setString(1, porderNo);
	        rs = getOrdersStmt.executeQuery();

	        List<String> productNos = new ArrayList<>();
	        List<Integer> amounts = new ArrayList<>();

	        while (rs.next()) {
	            productNos.add(rs.getString("productno"));
	            amounts.add(rs.getInt("amount"));
	        }

	        // **2. 刪除該 `porderno` 的所有訂單**
	        deleteStmt = conn.prepareStatement(deleteSQL);
	        deleteStmt.setString(1, porderNo);
	        int affectedRows = deleteStmt.executeUpdate();
	        if (affectedRows == 0) {
	            conn.rollback();
	            return false;
	        }

	        // **3. 還原庫存**
	        updateStockStmt = conn.prepareStatement(updateStockSQL);
	        for (int i = 0; i < productNos.size(); i++) {
	            updateStockStmt.setInt(1, amounts.get(i));
	            updateStockStmt.setString(2, productNos.get(i));
	            updateStockStmt.executeUpdate();
	        }

	        conn.commit(); // **提交交易**
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            if (conn != null) conn.rollback(); // **回滾**
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return false;
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (getOrdersStmt != null) getOrdersStmt.close();
	            if (deleteStmt != null) deleteStmt.close();
	            if (updateStockStmt != null) updateStockStmt.close();
	            if (conn != null) conn.setAutoCommit(true); // **恢復自動提交**
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}