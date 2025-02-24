package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.PorderDetailDao;
import dao.PorderSummaryDao;
import model.PorderDetail;
import model.PorderSummary;
import util.DbConnection;

public class PorderDetailDaoImpl implements PorderDetailDao {

	public static void main(String[] args) {
		PorderDetailDao porderdetailDao = new PorderDetailDaoImpl();
		//System.out.println(porderdetailDao.readAll());
		//System.out.println(porderdetailDao.readAllByPorderno("O2025021412000101"));
		System.out.println(porderdetailDao.readAllByPorderno("O2025021412000101","E002"));
	}
	
	private PorderDetail mapToPorderDetail(ResultSet rs) throws SQLException {
        PorderDetail porderdetail=new PorderDetail();
        porderdetail.setPorderno(rs.getString("porderno"));
        porderdetail.setOrderdate(rs.getString("orderdate"));
        porderdetail.setMemberno(rs.getString("memberno"));
        porderdetail.setMembername(rs.getString("membername"));
        porderdetail.setEmployno(rs.getString("employno"));
        porderdetail.setEmployname(rs.getString("employname"));
        porderdetail.setProductno(rs.getString("productno"));
        porderdetail.setProductname(rs.getString("productname"));
        porderdetail.setPrice(rs.getInt("price"));
        porderdetail.setAmount(rs.getInt("amount"));
        porderdetail.setSubtotal(rs.getInt("subtotal"));
        porderdetail.setStockquantity(rs.getInt("stockquantity"));
  
        return porderdetail;
    }

	@Override
	public List<PorderDetail> readAll() {
		String sql = "SELECT * FROM sportproject.porderdetail";
        List<PorderDetail> porderdetailList = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             while (rs.next()) {
            	 porderdetailList.add(mapToPorderDetail(rs));
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return porderdetailList;
	}

	@Override
	public List<PorderDetail> readAllByPorderno(String porderno) {
		String sql = "select * from sportproject.porderdetail where porderno=?";
		List<PorderDetail> porderdetailList = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, porderno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	porderdetailList.add(mapToPorderDetail(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return porderdetailList;
	}

	@Override
	public List<PorderDetail> readAllByPorderno(String porderno, String employno) {
		String sql = "select * from sportproject.porderdetail where porderno=? and employno=?";
		List<PorderDetail> porderdetailList = new ArrayList<>();
        try (Connection conn = DbConnection.getDb();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, porderno);
            pstmt.setString(2, employno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	porderdetailList.add(mapToPorderDetail(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return porderdetailList;
	}
	
}
