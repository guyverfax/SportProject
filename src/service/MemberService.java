package service;

import model.Member;
import java.util.List;

public interface MemberService {
    // Create
    boolean create(Member member);

    // Read
    Member readById(int id);
    Member readByMemberno(String memberno);
    String[] readByMemberno();
    List<Member> readAll();


    // Update
    boolean update(Member member);

    // Delete
    boolean delete(int id);
}