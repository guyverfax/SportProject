package service.impl;

import dao.PorderDao;
import dao.impl.PorderDaoImpl;
import model.Porder;
import service.PorderService;
import java.util.List;

public class PorderServiceImpl implements PorderService {

    private final PorderDao porderDao;

    public PorderServiceImpl() {
        this.porderDao = new PorderDaoImpl();
    }

    public static void main(String[] args) {
        PorderService porderService = new PorderServiceImpl();

        // Test Create
        //Porder newPorder = new Porder("O004", "P003", "M001", "E001", 6);
        //boolean created = porderService.create(newPorder);
        //System.out.println("Create: " + created);

        // Test Read by ID
        //Orders orderById = ordersService.readById(1);
        //System.out.println("Read by ID: " + orderById);

        // Test Read by Orderno
        //Porder porderByOrderno = porderService.readByPorderno("O002");
        //System.out.println("Read by Orderno: " + porderByOrderno);

        // Test Read by Memberno
        //List<Orders> ordersByMemberno = ordersService.readByMemberno("M002");
        //System.out.println("Read by Memberno: " + ordersByMemberno);

        // Test Read by Employno
        //List<Orders> ordersByEmployno = ordersService.readByEmployno("E002");
        //System.out.println("Read by Employno: " + ordersByEmployno);

        // Test Read All
        //List<Porder> allPorder = porderService.readAll();
        //System.out.println("Read All: " + allPorder);

        // Test Update
        //Orders updatedOrder = new Orders("ORD001", "M001", "E001");
        //boolean updated = ordersService.update(updatedOrder);
        //System.out.println("Update: " + updated);

        // Test Delete
        //boolean deleted = ordersService.delete(4);
        //System.out.println("Delete: " + deleted);
    }

    @Override
    public boolean create(Porder porder) {
        return porderDao.create(porder);
    }

    @Override
    public Porder readById(int id) {
        return porderDao.readById(id);
    }

    @Override
    public Porder readByPorderno(String porderno) {
        return (Porder) porderDao.readByPorderno(porderno);
    }

    @Override
    public List<Porder> readByMemberno(String memberno) {
        return porderDao.readByMemberno(memberno);
    }

    @Override
    public List<Porder> readByEmployno(String employno) {
        return porderDao.readByEmployno(employno);
    }

    @Override
    public List<Porder> readAll() {
        return porderDao.readAll();
    }

    
    @Override
    public boolean update(Porder porder) {
        return porderDao.update(porder);
    }

    @Override
    public boolean delete(int id) {
        return porderDao.delete(id);
    }

}