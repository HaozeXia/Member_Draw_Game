/*
 * Student name: Haoze Xia
 * Student ID: 1131343
 * LMS username: haozex
 */

public class DataAccessException extends Exception {

	/**
	 * This class is use to print and
	 * exit the system
	 */
	private static final long serialVersionUID = 1L;
	public DataAccessException(String e){
		super(e + "Data Access is has problems, Please restart and try again");
		System.exit(0);
	}

}
