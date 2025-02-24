package service.impl;

import dao.MemberDao;
import dao.impl.MemberDaoImpl;
import model.Member;
import service.MemberService;
import java.util.List;

public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    public MemberServiceImpl() {
        this.memberDao = new MemberDaoImpl();
    }

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();

        // Test Create
        //Member newMember = new Member("M004", "客戶劉", "1234567890", "台中市");
        //boolean created = memberService.create(newMember);
        //System.out.println("Create: " + created);

        // Test Read by ID
        //Member memberById = memberService.readById(1);
        //System.out.println("Read by ID: " + memberById);

        // Test Read by Memberno
        Member memberByMemberno = memberService.readByMemberno("M002");
        System.out.println("Read by Memberno: " + memberByMemberno.getPhone());

        // Test Read All
        //List<Member> allMembers = memberService.readAll();
        //System.out.println("Read All: " + allMembers);

        // Test Update
        //Member updatedMember = new Member("M001", "客戶林更新", "台北市", "0987654321");
        //boolean updated = memberService.update(updatedMember);
        //System.out.println("Update: " + updated);

        // Test Delete
        //boolean deleted = memberService.delete(4);
        //System.out.println("Delete: " + deleted);
    }

    @Override
    public boolean create(Member member) {
        return memberDao.create(member);
    }

    @Override
    public Member readById(int id) {
        return memberDao.readById(id);
    }

    @Override
    public Member readByMemberno(String memberno) {
        return memberDao.readByMemberno(memberno);
    }
    
    @Override
	public String[] readByMemberno() {
        String[] memberno = memberDao.readAll()
        							 .stream()
                                     .map(Member::getMemberno)
                                     .toArray(String[]::new);
		return memberno;
	}
    
    @Override
    public List<Member> readAll() {
        return memberDao.readAll();
    }

    @Override
    public boolean update(Member member) {
        return memberDao.update(member);
    }

    @Override
    public boolean delete(int id) {
        return memberDao.delete(id);
    }

	
}