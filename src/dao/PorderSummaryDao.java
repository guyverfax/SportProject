package dao;

import java.util.List;

import model.PorderSummary;

public interface PorderSummaryDao {
	//create
	
	//read
	List<PorderSummary> readAll();
	List<PorderSummary> readAll(String sql, List<Object> params);
	List<PorderSummary> readAllByMemberno(String memberno);
	List<PorderSummary> readAllByEmployno(String employno);
	
	//update
	
	//delete

}
