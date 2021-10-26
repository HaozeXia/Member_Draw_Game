/*
 * Student name: Haoze Xia
 * Student ID: 1131343
 * LMS username: haozex
 */

public class Member {
	
	/**
	 * This class is used to record member info
	 * */
	private String memberId;
	private String memberName;
	private String email;
	
	/**This a constructor of Class Member
	 * @param memberID is the member ID
	 * @param memberName is member's Name
	 * @param email is the member's email
	 * */
	public Member(String memberId, String memberName, String email) {
		this.email=email;
		this.memberId = memberId;
		this.memberName = memberName;
	}
	
	/**
	 * This is a null constructor of Class Member
	 * */
	public Member() {
		
	}
	
	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}


}
