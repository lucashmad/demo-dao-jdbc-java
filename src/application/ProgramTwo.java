package application;

import dao.DaoFactory;
import dao.DepartmentDao;
import entities.Department;

import java.util.Scanner;


public class ProgramTwo {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DepartmentDao departmentDao = DaoFactory.creatDepartmentDao();

        System.out.println("==== TEST 1: INSERT ====");
        Department newDepartment = new Department(null, "Game");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! New id: " + newDepartment.getId());

        System.out.println("==== TEST 2: DELETE ====");
        System.out.println("Enter id from delete: ");
        int id = scanner.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Deleted completed! ");

        System.out.println("=== Test 3: Seller findById ===");
        Department department = departmentDao.findById(10);
        System.out.println(department);

        scanner.close();
    }
}
