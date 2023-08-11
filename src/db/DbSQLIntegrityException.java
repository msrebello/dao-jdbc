package db;

public class DbSQLIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DbSQLIntegrityException(String msg) {
		super(msg);
	}

}
