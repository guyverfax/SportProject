package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.PorderDao;
import dao.PorderSummaryDao;
import model.Porder;
import model.PorderSummary;
import util.DbConnection;

public class PorderSummaryDaoImpl implements PorderSummaryDao {

	public static void main(String[] args) {
		PorderSummaryDao pordersummaryDao = new PorderSummaryDaoImpl();
		
		//Test Read All
        //List<PorderSummary> allPordersummary = pordersummaryDao.readAll();
        //for (PorderSummary ps:allPordersummary)
        //{
        //	System.out.println("Read All: " + ps.getPorderno());
        //}
		
		//Test ReadAll by Memberno
		//List<PorderSummary> allPordersummary = pordersummaryDao.readAllByMemberno("M002");
        //for (PorderSummary ps:allPordersummary)
        //{
        	//System.out.println("Read All by Memberno: " + ps.getPorderno());
        //}
		System.out.println(pordersummaryDao.readAmountByEmploy());
	}

	@Override
	public List<PorderSummary> readAll() {
		String sql = "SELECT * FROM sportproject.pordersummary";
        List<PorderSummary> pordersummaryList = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             while (rs.next()) {
            	 pordersummaryList.add(mapToPorderSummary(rs));
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pordersummaryList;
	}

		
	@Override
	public List<PorderSummary> readAllByMemberno(String memberno) {
		String sql = "select * from sportproject.pordersummary where memberno=?";
		List<PorderSummary> pordersummaryList = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	pordersummaryList.add(mapToPorderSummary(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return pordersummaryList;
	}
	
	@Override
	public List<PorderSummary> readAllByEmployno(String employno) {
		String sql = "select * from sportproject.pordersummary where employno=?";
		List<PorderSummary> pordersummaryList = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	pordersummaryList.add(mapToPorderSummary(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return pordersummaryList;
	}
	
	@Override
	public Map<String, Integer> readAmountByEmploy() {
		Map<String, Integer> result = new HashMap<>();
        String sql = "SELECT employno, SUM(totalprice) AS totalprice FROM pordersummary GROUP BY employno";

        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                result.put(rs.getString("employno"), rs.getInt("totalprice"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
	}
	
	@Override
	public Map<String, Integer> readAmountByMember() {
		Map<String, Integer> result = new HashMap<>();
        String sql = "SELECT memberno, SUM(totalprice) AS totalprice FROM pordersummary GROUP BY memberno";

        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                result.put(rs.getString("memberno"), rs.getInt("totalprice"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
	}
	
	private PorderSummary mapToPorderSummary(ResultSet rs) throws SQLException {
        PorderSummary pordersummary = new PorderSummary();
        pordersummary.setPorderno(rs.getString("porderno"));
        pordersummary.setOrderdate(rs.getString("orderdate"));
        pordersummary.setMemberno(rs.getString("memberno"));
        pordersummary.setMembername(rs.getString("membername"));
        pordersummary.setEmployno(rs.getString("employno"));
        pordersummary.setEmployname(rs.getString("employname"));
        pordersummary.setProducts(rs.getString("products"));
        pordersummary.setTotalprice(rs.getInt("totalprice"));
        return pordersummary;
    }

	

}
