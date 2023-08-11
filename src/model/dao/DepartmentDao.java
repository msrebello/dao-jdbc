package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	
	void insert(Department department);
	void uptade(Department department);
	void delete(Department department);
	Department findById(int id);
	List<Department> finAll();
}
