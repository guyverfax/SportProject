package service;

import java.util.List;

import model.Employ;

public interface EmployService {
    // Create
    boolean create(Employ employ);

    // Read
    Employ readById(int id);
    Employ readByEmployno(String employno);
    Employ readByUsername(String username);
    Employ readByName(String name);
    boolean isExitsByEmployno(String employno);
    boolean isExitsByUsername(String usename);
    List<Employ> readAll();

    // Update
    boolean update(Employ employ);

    // Delete
    boolean delete(int id);
}