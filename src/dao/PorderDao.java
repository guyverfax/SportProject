package dao;

import model.Porder;
import model.PorderSummary;

import java.util.List;

public interface PorderDao {
    // Create
    boolean create(Porder porder);
    boolean createUpdateStock(Porder porder);
    boolean createUpdateStock(List<Porder> orders);

    // Read
    Porder readById(int id);
    Porder readByPorderno(String porderno);
    List<Porder> readByMemberno(String memberno);
    List<Porder> readByEmployno(String employno);
    List<Porder> readByProductno(String productno);
    List<Porder> readAllByPorderno(String porderno);
    List<Porder> readAll();
    List<Porder> readAll(String sql, List<Object> params);
    boolean existsInPorder(String porderno, String productno);
    int readAmount(String productno);

    // Update
    boolean update(Porder porder);
    void updateAmount(String porderno, String productno, int amount);

    // Delete
    boolean delete(int id);
    void delete(String porderno, String memberno, String employno); //just delete & no update stock
    void deletePorderItem(String porderno, String productno);
    boolean deletePorder(String porderNo, String productNo, int amount);
    boolean deleteOrderByPorderNo(String porderNo); //delete & update stock
}