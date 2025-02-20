package dao;

import model.Employ;
import java.util.List;

public interface EmployDao {
    // Create
    boolean create(Employ employ);

    // Read
    Employ readById(int id);
    Employ readByEmployno(String employno);
    Employ readByUsername(String username);
    Employ readByName(String name);
    List<Employ> readAll();
    boolean isExitsByEmployno(String employno);

    // Update
    boolean update(Employ employ);

    // Delete
    boolean delete(int id);
    boolean delete(String employno);
}