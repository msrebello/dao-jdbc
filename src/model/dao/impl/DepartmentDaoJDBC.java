package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn;
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department department) {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(
					"INSERT INTO department "
							+ "(Name) "
							+ "VALUES "
							+ "(?)" , Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, department.getName());
			
			int rows_affected =	ps.executeUpdate();
		
			if (rows_affected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
						
				if (rs.next()) {
					int id = rs.getInt(1);
					department.setId(id);
				}
				
				DB.closeResulSet(rs);
			}
			
			else {
				throw new DbException("NO ROWS AFFECTED!");
			}
			
			
		} catch (SQLException e) {
			throw new DbException("## ERROR: " + e.getMessage());
		}
		
		finally {
			
			DB.closeStatement(ps);
		}
	}

	@Override
	public void uptade(Department department) {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(
					"UPDATE department "
					+ "SET Name = ? "
					+ "WHERE Id = ?");
			
			ps.setString(1, department.getName());
			ps.setInt(2, department.getId());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException("## ERROR: " + e.getMessage());
		}

		finally {	
			
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(
							"DELETE FROM department "
							+ "WHERE Id = ?");
			
			ps.setInt(1, id);	
			
			int rows_affected = ps.executeUpdate();
			
			if (rows_affected == 0) {
				throw new DbException("FAILED OPERATION! ID NOT EXISTS");
			}
			
		} 
		catch (SQLException e) {
			throw new DbException("## ERROR: " + e.getMessage());
		}
		
		finally {	
			
			DB.closeStatement(ps);
		}
		
	}

	@Override
	public Department findById(int id) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"SELECT * FROM department "
					+"WHERE Id = ?");
			
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				
				Department department = new Department(rs.getInt("Id"), rs.getString("Name"));
				return department;
			}
			
			return null;
			
		} 
		catch (SQLException e) {
			throw new DbException("## ERROR: " + e.getMessage());
		}
		
		finally {
			DB.closeResulSet(rs);
			DB.closeStatement(ps);
		}
	}

	@Override
	public List<Department> findAll() {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
					"SELECT * FROM department "
							+ "ORDER BY Id");
			
			rs = ps.executeQuery();
			
			List<Department> listOfDepartments = new ArrayList<>();
			
			while (rs.next()) {
				Department department = new Department(rs.getInt("Id"), rs.getString("Name"));
				listOfDepartments.add(department);
			}
			
			return listOfDepartments;
			
		} catch (SQLException e) {
			throw new DbException("## ERROR: " + e.getMessage());
		}
		
		finally {
			DB.closeResulSet(rs);
			DB.closeStatement(ps);
		}

	}

}
