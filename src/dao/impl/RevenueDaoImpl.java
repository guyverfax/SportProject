package dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.RevenueDao;
import model.PorderSummary;
import model.Revenue;
import util.DbConnection;

public class RevenueDaoImpl implements RevenueDao{

	public static void main(String[] args) {
		//readAll()
		//RevenueDao revenueDao=new RevenueDaoImpl();
		//System.out.println(revenueDao.readAll());
	}

	@Override
	public List<Revenue> readAll() {
		String sql="SELECT * FROM sportproject.revenue";
		List<Revenue> revenueList=new ArrayList<>();
		
		try {
			Connection conn=DbConnection.getDb();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				revenueList.add(mapToRevenue(rs));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return revenueList;
	}

	private Revenue mapToRevenue(ResultSet rs) throws SQLException {
        Revenue revenue = new Revenue();
        revenue.setYear(rs.getInt("year"));
        revenue.setMonth(rs.getInt("month"));
        revenue.setRevenue(rs.getInt("revenue"));
        return revenue;
    }
}
