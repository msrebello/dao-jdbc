package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	
	void insert(Seller seller);
	void uptade(Seller seller);
	void deleteById(Integer id);
	Seller findById(int id);
	List<Seller> finAll();
	List<Seller> findByDepartment(Department department);

}
