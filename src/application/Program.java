package application;

import dao.DaoFactory;
import dao.SellerDao;
import entities.Department;
import entities.Seller;

import java.util.ArrayList;
import java.util.List;


public class Program {
    public static void main(String[] args) {

        // apenas teste
      //  Seller seller = new Seller(21,"Lucas", "lucas@gmail.com", new Date(), 3000.0, department);

       SellerDao sellerDao = DaoFactory.createSellerDao();

       // System.out.println(seller);

        System.out.println("=== Test 1: Seller findById ===");

        Seller seller = sellerDao.findById(3);

        System.out.println(seller);

        System.out.println("=== Test 2: Seller findByDepartment ===");
        Department department = new Department(2, null);
        List<Seller> sellerList = sellerDao.FindByIdDepartment(department);
        for (Seller seller1 : sellerList){
            System.out.println(seller1);
        }


    }
}
