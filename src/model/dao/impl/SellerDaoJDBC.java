package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uptade(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Seller seller) {
		// TODO Auto-generated method stub
		
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
				
				Department departmentObj = new Department();
							
						departmentObj.setId(rs.getInt("Id"));
						departmentObj.setName(rs.getString("Name"));
				
				Seller sellerObj = new Seller();
						
						sellerObj.setId(rs.getInt("Id"));
						sellerObj.setName(rs.getString("Name"));
						sellerObj.setName(rs.getString("Email"));
						sellerObj.setBirthDate(rs.getDate("BirthDate"));
						sellerObj.setBaseSalary(rs.getDouble("BaseSalary"));
						sellerObj.setDepartment(departmentObj);
						
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

	@Override
	public List<Seller> finAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
