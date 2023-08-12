package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		DepartmentDao departmentDao = DaoFactory.createDepartmentrDao();

		// TEST 1:

		System.out.println(" ====== [DEPARTMENT FIND BY ID] ======\n");

		Department department = departmentDao.findById(3);
		System.out.println(department);

		// TEST 2:

		System.out.println("\n ====== [DEPARTMENT FIND ALL] ======\n");

		List<Department> departments = departmentDao.findAll();

		for (Department obj : departments) {
			System.out.println(obj);
		}

		// TEST 3:

		System.out.println("\n ====== [DEPARTMENT INSERT] ======\n");
		Department newDepartment = new Department(null, "Philosophy");
		departmentDao.insert(newDepartment);

		System.out.println("New Department (ID): " + newDepartment.getId());

		// TEST 4:

		System.out.println("\n ====== [DEPARTMENT UPDATE] ======\n");
		department = departmentDao.findById(5);
		department.setName("Psychology");
		departmentDao.uptade(department);

		System.out.println("Execute update!");

		// TEST 5:

		System.out.println("\n ====== [DEPARTMENT DELETE] ======\n");

		System.out.print("Enter an Id to delete: ");
		int id = sc.nextInt();
		departmentDao.deleteById(id);

		System.out.println("Deleted Id!");

		sc.close();
	}
}
