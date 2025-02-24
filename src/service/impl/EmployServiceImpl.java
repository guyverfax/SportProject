package service.impl;

import dao.EmployDao;
import dao.impl.EmployDaoImpl;
import model.Employ;
import service.EmployService;
import java.util.List;

public class EmployServiceImpl implements EmployService {

    private final EmployDao employDao;

    public EmployServiceImpl() {
        this.employDao = new EmployDaoImpl();
    }

    public static void main(String[] args) {
        EmployService employService = new EmployServiceImpl();

        /*
        // Test Create
        Employ newEmploy = new Employ("E004", "員工王", "user3", "password", "User");
        boolean created = employService.create(newEmploy);
        System.out.println("Create: " + created);

        // Test Read by ID
        Employ employById = employService.readById(1);
        System.out.println("Read by ID: " + employById);

        // Test Read by Employno
        Employ employByEmployno = employService.readByEmployno("E002");
        System.out.println("Read by Employno: " + employByEmployno);

        // Test Read by Username
        Employ employByUsername = employService.readByUsername("user1");
        System.out.println("Read by Username: " + employByUsername);

        // Test Read All
        List<Employ> allEmployees = employService.readAll();
        System.out.println("Read All: " + allEmployees);

        // Test Update
        Employ updatedEmploy = new Employ("E001", "員工張更新", "admin", "UpdatedPassword", "Admin");
        boolean updated = employService.update(updatedEmploy);
        System.out.println("Update: " + updated);

        // Test Delete
        boolean deleted = employService.delete(4);
        System.out.println("Delete: " + deleted);
        */
    }

    @Override
    public boolean create(Employ employ) {
        return employDao.create(employ);
    }

    @Override
    public Employ readById(int id) {
        return employDao.readById(id);
    }

    @Override
    public Employ readByEmployno(String employno) {
        return employDao.readByEmployno(employno);
    }

    @Override
    public Employ readByUsername(String username) {
        return employDao.readByUsername(username);
    }
    
	@Override
	public Employ readByName(String name) {
		return employDao.readByName(name);
	}
	
	@Override
	public boolean isExitsByEmployno(String employno) {
		if (employDao.readByEmployno(employno)!=null)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isExitsByUsername(String usename) {
		if (employDao.readByUsername(usename)!=null)
		{
			return true;
		}
		return false;
	}
	
    @Override
    public List<Employ> readAll() {
        return employDao.readAll();
    }

    @Override
    public boolean update(Employ employ) {
        return employDao.update(employ);
    }

    @Override
    public boolean delete(int id) {
        return employDao.delete(id);
    }

}