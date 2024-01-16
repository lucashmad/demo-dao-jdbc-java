package application;

import dao.DaoFactory;
import dao.SellerDao;
import entities.Department;
import entities.Seller;

import java.util.ArrayList;
import java.util.Date;
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

        System.out.println("=== Test 3: Seller findAll ===");
        sellerList = sellerDao.findAll();
        for (Seller seller1 : sellerList) {
            System.out.println(seller1);

        }
        System.out.println("=== Test 4: Seller Insert ===");
        Seller newSeller = new Seller(null, "Lucas", "Lucas@gmail.com", new Date(), 3500.00, department);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());

        System.out.println("=== Test 5: Seller Update ===");
        seller = sellerDao.findById(1);
        seller.setName("Juliana Vitoria");
        sellerDao.update(seller);
        System.out.println("Update completed!");
    }
}
