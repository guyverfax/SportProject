package dao;

import model.Member;
import java.util.List;

public interface MemberDao {
    // Create
    boolean create(Member member);

    // Read
    Member readById(int id);
    Member readByMemberno(String memberno);
    List<Member> readAll();
    List<Member> readByKeyword(String keyword);
    String[] readByMemberno();
    boolean isExitsByMemberno(String memberno);
    

    // Update
    boolean update(Member member);

    // Delete
    boolean delete(int id);
    boolean delete(String memberno);
}