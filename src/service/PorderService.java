package service;

import model.Porder;

import java.util.List;

public interface PorderService {
    // Create
    boolean create(Porder porder);

    // Read
    Porder readById(int id);
    Porder readByPorderno(String porderno);
    List<Porder> readByMemberno(String memberno);
    List<Porder> readByEmployno(String employno);
    List<Porder> readAll();
    

    // Update
    boolean update(Porder porder);

    // Delete
    boolean delete(int id);
}