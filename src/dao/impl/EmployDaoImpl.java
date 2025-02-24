package dao.impl;

import dao.EmployDao;
import model.Employ;
import util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployDaoImpl implements EmployDao {

    public static void main(String[] args) {
        EmployDao employDao = new EmployDaoImpl();

        // Test Create
        Employ newEmploy = new Employ("E004", "員工王", "user3", "password", "User");
        boolean created = employDao.create(newEmploy);
        System.out.println("Create: " + created);

        // Test Read by ID
        Employ employById = employDao.readById(1);
        System.out.println("Read by ID: " + employById);

        // Test Read by Employno
        Employ employByEmployno = employDao.readByEmployno("E002");
        System.out.println("Read by Employno: " + employByEmployno);

        // Test Read by Username
        Employ employByUsername = employDao.readByUsername("user1");
        System.out.println("Read by Username: " + employByUsername);

        // Test Read All
        List<Employ> allEmployees = employDao.readAll();
        System.out.println("Read All: " + allEmployees);

        // Test Update
        Employ updatedEmploy = new Employ("E001", "員工張更新", "admin", "UpdatedPassword", "Admin");
        boolean updated = employDao.update(updatedEmploy);
        System.out.println("Update: " + updated);

        // Test Delete
        boolean deleted = employDao.delete(4);
        System.out.println("Delete: " + deleted);
    }

    @Override
    public boolean create(Employ employ) {
        String sql = "INSERT INTO employ (employno, name, username, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employ.getEmployno());
            pstmt.setString(2, employ.getName());
            pstmt.setString(3, employ.getUsername());
            pstmt.setString(4, employ.getPassword());
            pstmt.setString(5, employ.getRole());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Employ readById(int id) {
        String sql = "SELECT * FROM employ WHERE id = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapToEmploy(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employ readByEmployno(String employno) {
        String sql = "SELECT * FROM employ WHERE employno = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employno);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapToEmploy(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employ readByUsername(String username) {
        String sql = "SELECT * FROM employ WHERE username = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapToEmploy(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
	
    @Override
	public Employ readByName(String name) {
    	String sql = "SELECT * FROM employ WHERE name = ?";
        try (Connection conn = DbConnection.getDb();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapToEmploy(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}
	
    @Override
    public List<Employ> readAll() {
        String sql = "SELECT * FROM employ";
        List<Employ> employs = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                employs.add(mapToEmploy(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employs;
    }

    @Override
	public boolean isExitsByEmployno(String employno) {
    	if (readByEmployno(employno)!=null)
		{
			return true;
		}
		return false;
	}
    
    @Override
    public boolean update(Employ employ) {
        String sql = "UPDATE employ SET name = ?, username = ?, password = ?, role = ? WHERE employno = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employ.getName());
            pstmt.setString(2, employ.getUsername());
            pstmt.setString(3, employ.getPassword());
            pstmt.setString(4, employ.getRole());
            pstmt.setString(5, employ.getEmployno());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM employ WHERE id = ?";
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
	public boolean delete(String employno) {
		String sql = "DELETE FROM employ WHERE employno = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employno);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}   
	
    private Employ mapToEmploy(ResultSet rs) throws SQLException {
        Employ employ = new Employ();
        employ.setId(rs.getInt("id"));
        employ.setEmployno(rs.getString("employno"));
        employ.setName(rs.getString("name"));
        employ.setUsername(rs.getString("username"));
        employ.setPassword(rs.getString("password"));
        employ.setRole(rs.getString("role"));
        return employ;
    } 
}