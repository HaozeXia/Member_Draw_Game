import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Student name: Haoze Xia
 * Student ID: 1131343
 * LMS username: haozex
 */

public class DataProvider {
    /**
     * This class is the support group for this project
     * this class has method to read from file get info
     * and can also update to a file
     * and can also search memberID and bill ID
     */
	private String billFile; //bill file name
	private ArrayList<Member> memberArray; //ArrayList for member class
	private ArrayList<Bill> billArray;  //ArrayList for bill class
	private final int INDEX_TWO = 2;  //array index
	private final int INDEX_THREE = 3;  //array index
	
	
	/**
	 * This method is use for read the bill and member file
	 * and create them as objects
	 * and record in the ArrayList
	 * @param memberFile A path to the member file
     * @param billFile A path to the bill file
     * @throws DataAccessException If a file cannot be opened/read
     * @throws DataFormatException If the format of the the content is incorrect
	 * */
     public DataProvider(String memberFile, String billFile) 
                        throws DataAccessException, DataFormatException {
    	 String line;
         try { //try to create a buffered reader by given file name
        	 
        	 BufferedReader memberFiles = new BufferedReader(new FileReader(memberFile));
        	 BufferedReader billFiles = new BufferedReader(new FileReader(billFile));
        	 this.billFile = billFile;
        	 
             memberArray = new ArrayList<Member>(); //create ArrayList in Member class type
             billArray = new ArrayList<Bill>(); //create ArrayList in Bill class type
             
             while((line = memberFiles.readLine()) != null) {
            	 //read line by line for member File
            	 String parts[] = line.split(","); //split with ","
            	 Member read_member = new Member(parts[0],parts[1],parts[INDEX_TWO]);
            	 //create object for each member 
            	 memberArray.add(read_member);  // add them in member ArrayList
             }
             
             while((line = billFiles.readLine()) != null) {
            	//read line by line for bill File
            	 String parts[] = line.split(",");//split with ","
            	 double totalAmount = Double.parseDouble(parts[INDEX_TWO]);
            	 //convert String amount to double type
            	 boolean status = Boolean.parseBoolean(parts[INDEX_THREE]);
            	 //convert String status to boolean type
            	 Bill read_bill = new Bill(parts[0],parts[1],totalAmount,status);
            	 //create object for each bill
            	 billArray.add(read_bill); // add them in member ArrayList
             }
             memberFiles.close();  //stream close
             billFiles.close();  //stream close
         }
         catch(FileNotFoundException e) {
        	 //catch FileNotFoundException and throw DataAccessExpection
        	 throw new DataAccessException(memberFile+" or "+ billFile);
         } 
         catch(IOException e) {
        	 //catch IOexpection and throw DataFormatException
        	 throw new DataFormatException(memberFile+" or "+ billFile);
         } 
       
     }
  
     /**
      * This method is use for update the bill file
      * */
     public void writeBills() {
    	
    	 try {
    		 //try to create a write 
			BufferedWriter bill = new BufferedWriter(new FileWriter(billFile));
			String newBill = "";  //set the new bill to empty
			
			for(Bill b: billArray) {
				//for each bill recorded in bill ArrayList
				String line;
				if(b.billStatus()== true) {
//if this bill's status is true means this bill has been used, update true at the rear
					line = String.format("%s,%s,%f,true,\n",
							b.getBillID(),b.getMemberID(),b.getTotalAmount());
				}
				else {
//if this bill's status is false means this bill has not been used, update true at the rear
					line = String.format("%s,%s,%f,false,\n",
							b.getBillID(),b.getMemberID(),b.getTotalAmount());
				}
				newBill = newBill + line;  //new bill is equal to sum of the lines
			}
			
			bill.write(newBill);  //update the file
			bill.close();  // stream close
			
		} catch (IOException e) {
			//catch IO Exception
			e.printStackTrace();
		}
     }
     
     /**
      * This method is use to search the given bill ID
      * @param billID is the billID that want to search in the bill file
      * @return bill if the given bill ID has found
      * */
     public Bill searchBill(String billID) {
    	 Bill bill;
    	 for(int i = 0; i < billArray.size();i++) {
    		 // search the bill ID in the bill ArrayList
    		 String ID = billArray.get(i).getBillID();
    		 
    		 if(billID.equals(ID)) {
    			 // this given billID is in the bill ArrayList
    			 bill = billArray.get(i);
    			 return bill;
    		 }
    	 }
    	 //can not find the given billID
    	 return null;
     }
     
     /**
      * This method is use to search the given member ID
      * @param memberID is the memberID that want to search in the members file
      * @return member if the given member ID has found
      * */
     public Member searchMember(String memberID) {
    	 
    	 Member member;
    	 
    	 for(int i = 0; i<memberArray.size();i++) {
    		// search the member ID in the bill ArrayList
    		 String ID = memberArray.get(i).getMemberId();
    		 if(memberID.equals(ID)) {
    			 // this given memberID is in the bill ArrayList
    			 member = memberArray.get(i);
    			 return member;
    		 }
    	 }
    	 return null;  //can not find the given member ID
     }

	/**
	 * @return the billFlie
	 */
	public String getBillFlie() {
		return billFile;
	}
}
