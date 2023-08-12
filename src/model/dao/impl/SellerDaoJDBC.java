package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	private Connection conn;
		
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {

		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)" , Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			ps.setDouble(4, seller.getBaseSalary());
			ps.setInt(5, seller.getDepartment().getId());
			
			int rows_affected = ps.executeUpdate();
			
			if (rows_affected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
				}
				
				DB.closeResulSet(rs);
			}
			
			else {
				throw new DbException("## ERROR: No rows Affected!!");
			}
			
		} catch (SQLException e) {
			
			throw new DbException("## ERROR Caused by: " + e.getMessage());
		}
		
		finally {
			
			DB.closeStatement(ps);
		}
		
	}

	@Override
	public void uptade(Seller seller) {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?");
			
			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			ps.setDouble(4, seller.getBaseSalary());
			ps.setInt(5, seller.getDepartment().getId());
			ps.setInt(6, seller.getId());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			
			throw new DbException("## ERROR Caused by: " + e.getMessage());
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
					"DELETE FROM seller "
					+ "WHERE Id = ?");
			
			ps.setInt(1, id);
		
			int rows_affected = ps.executeUpdate();
			
			if (rows_affected == 0) {
				throw new DbException("FAILED OPERATION! ID NOT EXISTS");
			}
			
		} catch (SQLException e) {
			
			throw new DbException("## ERROR Caused by: " + e.getMessage());
		}
		
		finally {
			
			DB.closeStatement(ps);
		}	
		
	}

	@Override
	public Seller findById(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {	// QUERY
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
				
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if (rs.next()) { 
				
				// "CONVERT" DATA OF RESULTSET TO OBJECT
				
				Department departmentObj = instantiateDepartment(rs);
				
				Seller sellerObj = instantiateSeller(rs, departmentObj);
						
				return sellerObj;
			}
			
			return null;	
				
		} catch (SQLException e) {
			throw new DbException("## ERROR Caused by: " + e.getMessage());
		}
		
		finally {
			DB.closeResulSet(rs);
			DB.closeStatement(ps);
		}
	}
	
	private Seller instantiateSeller(ResultSet rs, Department departmentObj) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(departmentObj);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> finAll() {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {	// QUERY
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Id");
			
			rs = ps.executeQuery();
			
			List<Seller> sellers = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				
				// ID REUSE
				
				Department dep  = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller sellerObj = instantiateSeller(rs, dep);
				sellers.add(sellerObj);
			}
			
			return sellers;	
				
		} catch (SQLException e) {
			throw new DbException("## ERROR Caused by: " + e.getMessage());
		}
		
		finally {
			DB.closeResulSet(rs);
			DB.closeStatement(ps);
		}
	}
	

	@Override
	public List<Seller> findByDepartment(Department department) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {	// QUERY
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
				
			ps.setInt(1, department.getId());
			
			rs = ps.executeQuery();
			
			List<Seller> sellers = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				
				// ID REUSE
				
				Department dep  = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller sellerObj = instantiateSeller(rs, dep);
				sellers.add(sellerObj);
			}
			
			return sellers;	
				
		} catch (SQLException e) {
			throw new DbException("## ERROR Caused by: " + e.getMessage());
		}
		
		finally {
			DB.closeResulSet(rs);
			DB.closeStatement(ps);
		}
	}
	
	

}
