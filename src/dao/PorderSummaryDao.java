package dao;

import java.util.List;
import java.util.Map;

import model.PorderSummary;

public interface PorderSummaryDao {
	//create
	
	//read
	List<PorderSummary> readAll();
	List<PorderSummary> readAll(String sql, List<Object> params);
	List<PorderSummary> readAllByMemberno(String memberno);
	List<PorderSummary> readAllByEmployno(String employno);
	Map<String, Integer> readAmountByEmploy();
	Map<String, Integer> readAmountByMember();
	
	//update
	
	//delete

}
