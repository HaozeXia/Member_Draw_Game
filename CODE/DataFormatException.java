/*
 * Student name: XXX
 * Student ID: YYY
 * LMS username: ZZZ
 */

public class DataFormatException extends Exception {
	/**
	 * This class is use to print and
	 * exit the system
	 */
	private static final long serialVersionUID = 1L;

	public DataFormatException(String e) {
		super(e + ", Data Format has problems, Please restart and try again");
		System.exit(0);
	}

}
