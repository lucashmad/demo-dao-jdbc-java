package application;

import entities.Department;
import entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {

        // apenas teste
        Department department = new Department(1, "Books");

        Seller seller = new Seller(21,"Lucas", "lucas@gmail.com", new Date(), 3000.0, department);

        System.out.println(seller);



    }
}
