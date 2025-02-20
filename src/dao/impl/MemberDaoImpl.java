package dao.impl;

import dao.MemberDao;
import model.Member;
import util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDaoImpl implements MemberDao {

	public static void main(String[] args) {
    
    	MemberDao memberDao = new MemberDaoImpl();
    	
        // Test Create
        //Member newMember = new Member("M004", "客戶劉", "1234567890", "台中市");
        //boolean created = memberDao.create(newMember);
        //System.out.println("Create: " + created);

        // Test Read by ID
        //Member memberById = memberDao.readById(1);
        //System.out.println("Read by ID: " + memberById);

        // Test Read by Memberno
        Member memberByMemberno = memberDao.readByMemberno("M002");
        System.out.println("Read by Memberno: " + memberByMemberno.getPhone());

        // Test Read All
        //List<Member> allMembers = memberDao.readAll();
        //System.out.println("Read All: " + allMembers);

        // Test Update
        //Member updatedMember = new Member("M001", "客戶林更新", "台北市", "0987654321");
        //boolean updated = memberDao.update(updatedMember);
        //System.out.println("Update: " + updated);

        // Test Delete
        //boolean deleted = memberDao.delete(4);
        //System.out.println("Delete: " + deleted);
        
		
        //List<Member> allMembers = memberDao.readByKeyword("巿");
        //System.out.println("Read All: " + allMembers);
		
    }

    @Override
    public boolean create(Member member) {
        String sql = "INSERT INTO member (memberno, name, phone, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getMemberno());
            pstmt.setString(2, member.getName());
            pstmt.setString(3, member.getPhone());
            pstmt.setString(4, member.getAddress());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Member readById(int id) {
        String sql = "SELECT * FROM member WHERE id = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapToMember(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Member readByMemberno(String memberno) {
        String sql = "SELECT * FROM member WHERE memberno = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberno);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapToMember(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Member> readAll() {
        String sql = "SELECT * FROM member";
        List<Member> members = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                members.add(mapToMember(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
	@Override
	public List<Member> readByKeyword(String keyword) {
		List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM member WHERE memberno LIKE ? OR name LIKE ? OR phone LIKE ? OR address LIKE ?";
        
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchKeyword = "%" + keyword + "%";
            pstmt.setString(1, searchKeyword);
            pstmt.setString(2, searchKeyword);
            pstmt.setString(3, searchKeyword);
            pstmt.setString(4, searchKeyword);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                	members.add(mapToMember(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
	}
	
    @Override
	public String[] readByMemberno() {
    	String[] memberno = readAll()
				 .stream()
                .map(Member::getMemberno)
                .toArray(String[]::new);
    	return memberno;
	}
    
	@Override
	public boolean isExitsByMemberno(String memberno) {
		if (readByMemberno(memberno)!=null)
		{
			return true;
		}
		return false;
	}
	
    @Override
    public boolean update(Member member) {
        String sql = "UPDATE member SET name = ?, phone = ?, address = ? WHERE memberno = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getPhone());
            pstmt.setString(3, member.getAddress());
            pstmt.setString(4, member.getMemberno());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM member WHERE id = ?";
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
	public boolean delete(String memberno) {
		String sql = "DELETE FROM member WHERE memberno = ?";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberno);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
    private Member mapToMember(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(rs.getInt("id"));
        member.setMemberno(rs.getString("memberno"));
        member.setName(rs.getString("name"));
        member.setPhone(rs.getString("phone"));
        member.setAddress(rs.getString("address"));
        return member;
    }
}