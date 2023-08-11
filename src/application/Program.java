package application;

import java.time.LocalDate;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department department = new Department(1, "Philosophy");
		Seller seller = new Seller(1, "Aristoteles", "aristoteles@gmail.com", LocalDate.now(), 800.00, department);
		
		System.out.println("Department Object: " + department);
		System.out.println();
		System.out.println("Seller Object: " + seller);
		
	}

}
