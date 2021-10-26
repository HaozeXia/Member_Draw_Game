/*
 * Student name: Haoze Xia
 * Student ID: 1131343
 * LMS username: haozex
 */

public class Bill {
	/**
	 * This class is used to record bill info
	 * */
	private String billID;
	private String memberID;
	private double totalAmount;
	private boolean billStatus;
	
	/**
	 * This is a null constructor of Class Bill
	 * */
	public Bill() {
		
	}

	/**This a constructor of Class Bill
	 * @param billID is the bill ID
	 * @param memberID is the member ID
	 * @param totoal Amount is the amount of bill
	 * @param billStatus is to record bill is used or not
	 * */
	public Bill(String billID, String memberID, 
			double totalAmount, boolean billStatus) {
		
		this.billID = billID;
		this.memberID = memberID;
		this.totalAmount = totalAmount;
		this.billStatus = billStatus;
	}

	/**
	 * @return the billID
	 */
	public String getBillID() {
		return billID;
	}

	/**
	 * @return the memberID
	 */
	public String getMemberID() {
		return memberID;
	}

	/**
	 * @return the totalAmount
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @return the status
	 */
	public boolean billStatus() {
		return billStatus;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean billStatus) {
		this.billStatus = billStatus;
	}
	
	

}
