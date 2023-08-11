package application;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();

		// TEST 1:
		
		System.out.println(" ====== [SELLER FIND BY ID] ======\n");

		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		// TEST 2:
		
		System.out.println("\n ====== [SELLER FIND BY DEPARTMENT] ======\n");

		Department department = new Department(1, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		// TEST 3:
		
		System.out.println("\n ====== [SELLER FIND ALL] ======\n");

		list = sellerDao.finAll();
		
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		// TEST 4:
		
		System.out.println("\n ====== [SELLER INSERT] ======\n");
		
		Seller newSeller = new Seller(null, "Catarina", "catarian@gmail.com", new Date(), 2500.00, department); 
		sellerDao.insert(newSeller);
		
		System.out.println("New Seller (ID) = " + newSeller.getId());
	}

}
