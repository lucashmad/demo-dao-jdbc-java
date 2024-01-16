package application;

import dao.DaoFactory;
import dao.DepartmentDao;
import entities.Department;
import java.util.List;
import java.util.Scanner;


public class ProgramTwo {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DepartmentDao departmentDao = DaoFactory.creatDepartmentDao();

        System.out.println("==== TEST 1: INSERT ====");
        Department newDepartment = new Department(null, "Game");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! New id: " + newDepartment.getId());

        System.out.println("=== Test 2: Seller findById ===");
        Department department = departmentDao.findById(10);
        System.out.println(department);

        System.out.println("=== Test 3: Seller findAll ===");
        List<Department> departmentList = departmentDao.findAll();
        for (Department departmentOne : departmentList) {
            System.out.println(departmentOne);
        }
        System.out.println("=== Test 4: Seller Update ===");
        department = departmentDao.findById(1);
        department.setName("Tech Recruter");
        departmentDao.update(department);
        System.out.println("Update completed!");


        System.out.println("==== TEST 5: DELETE ====");
        System.out.println("Enter id from delete: ");
        int id = scanner.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Deleted completed! ");

        scanner.close();
    }
}
