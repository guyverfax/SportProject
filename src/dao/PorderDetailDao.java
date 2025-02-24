package dao;

import java.util.List;

import model.PorderDetail;
import model.PorderSummary;

public interface PorderDetailDao {
	
	//c
	
	//r
	List<PorderDetail> readAll();
	List<PorderDetail> readAllByPorderno(String porderno);
	List<PorderDetail> readAllByPorderno(String porderno, String employno);
	
	//u
	
	//d

}
