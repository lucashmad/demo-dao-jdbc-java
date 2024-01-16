package application;

import dao.DaoFactory;
import dao.DepartmentDao;
import entities.Department;


public class ProgramTwo {

    public static void main(String[] args) {



        DepartmentDao departmentDao = DaoFactory.creatDepartmentDao();

        System.out.println("==== TEST 1: INSERT ====");
        Department newDepartment = new Department(null, "Game");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! New id: " + newDepartment.getId());




    }



}
