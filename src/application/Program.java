package application;

import dao.DaoFactory;
import dao.SellerDao;
import entities.Seller;


public class Program {
    public static void main(String[] args) {

        // apenas teste
      //  Seller seller = new Seller(21,"Lucas", "lucas@gmail.com", new Date(), 3000.0, department);

       SellerDao sellerDao = DaoFactory.createSellerDao();

       // System.out.println(seller);

        System.out.println("=== Test 1: Seller findById ===");

        Seller seller = sellerDao.findById(3);

        System.out.println(seller);


    }
}
