import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;




/*
 * Student name: Haoze Xia
 * Student ID: 1131343
 * LMS username: haozex
 */

public class LuckyNumbersCompetition extends Competition implements Serializable {
	/**
	 * This class is extends from Competition and
	 * implements Serializable
	 * this class is use to add LuckyNumberCompetiton entry 
	 * and draw winner
	 */
	private static final long serialVersionUID = 1L;  //serializable ID
	private int entryID;  //entryID
	private int inital;  //initial position
	private int id;  // competition ID
    private final int ONE_CHANCE = 50; //$50 for one entry
    private final int LOW_PRIZE = 2;
	private final int RANDOM_RANGE = 100;
	private final int ARRAY_LENGTH = 7;
	private final int FIRST_PRIZE = 50000;
    private final int SECOND_PRIZE = 5000;
    private final int THIRD_PRIZE = 1000;
    private final int FOURTH_PRIZE = 500;
    private final int FIFTH_PRIZE = 100;
    private final int SIXTH_PRIZE = 50;
    private final int[] prizes = {SIXTH_PRIZE,FIFTH_PRIZE,FOURTH_PRIZE,
    		THIRD_PRIZE,SECOND_PRIZE,FIRST_PRIZE};  //prize array


    /**
     * This is a constructor for LuckyNumbersCompetition
     * @param name is the competition name
     * @param entryID is the entry ID
     * */
	public LuckyNumbersCompetition(String name, int entryID) {
		super(name, entryID);
		this.entryID = entryID;
	}
	
    /**
     * This is a empty constructor for LuckyNumbersCompetition
     * */
	public LuckyNumbersCompetition() {

	}
	
    /**
     * This is a constructor for LuckyNumbersCompetition
     * @param name is the competition name
     * @param id is the competition ID
     * */
	public LuckyNumbersCompetition(int id, String Name) {
		super(id,Name);
		this.id = id;
	}


	/**
	 * This method is to use to add entries
	 * */
	@Override
	public void addEntries() {
		String ID;
		Bill bill;
		boolean tryagain = true;
		inital = entryID;  //initial position

		System.out.println("Bill ID: ");

		while(true) {
			//remove \n
			ID = SimpleCompetitions.keyboard.nextLine();
			if(ID.equalsIgnoreCase("")) {
				
			}
			else {
				break;
			}
		}

		bill = SimpleCompetitions.dp.searchBill(ID);  //search given bill ID
		
		if (ID.length() == 6) {  //check id's length
			
			try {  //convert 'ID' String to integer type
				Integer.parseInt(ID);
				
			}catch (NumberFormatException e) {  
				//catch if ID contains characters or others, let user re-enter again
				System.out.printf("Invalid %s id! It must be a 6-digit number. "
						+ "Please try again.\n", "bill");
				addEntries();  //re-enter
				return;
			}	
		}
		else {  //ID not 6-digit, user re-enter again
			System.out.printf("Invalid %s id! It must be a 6-digit number. "
					+ "Please try again.\n","bill");
			addEntries(); //re-enter
			return;
		}
		
		
		if (SimpleCompetitions.dp.searchBill(ID) == null) {
			//can not find bill by given bill ID
			System.out.println("This bill does not exist. Please try again.");
			addEntries();  //re-enter
		}
		else if (bill.getMemberID() == "") {
			// this bill ID do not have a member ID
			System.out.println("This bill has no member id. Please try again.");
			addEntries();
		}
		else if(bill.billStatus()== true) {
			//this bill has no more chance to add
			System.out.println("This bill has already been used for a competition."
					+ " Please try again.");
			
			addEntries();
		}
		
		else {  //valid input
			double billAmount =bill.getTotalAmount();  //bill amount
			int totalChance = (int) Math.floor(billAmount/ONE_CHANCE); //total Chance
			
			System.out.println("This bill "+ "($"+billAmount+") "+"is eligible for "
			+totalChance+" entries. How many manual entries did the customer fill up?: ");
			
			while(true) {
				//manual Input numbers
				int manual = SimpleCompetitions.keyboard.nextInt();
				
				if (manual < totalChance && manual >= 0) {
					
					int autoChance = totalChance - manual;  //auto generator chance
					String memberID = bill.getMemberID();  //member ID
					manualInput(manual,autoChance,ID,memberID);  //invoke manualInput
					autoInput(autoChance,ID,memberID);  //invoke autoInput
					display();  //print out what just added
					bill.setStatus(true);  //set this bill has been used
					break;
				}
				else {
					//invalid input
					System.out.printf("The number must "
							+ "be in the range from 0 to %d. Please try again.\n" ,totalChance);
				}
			}
			
			while(tryagain) {
				//addMore Input
				System.out.println("Add more entries (Y/N)?");
				String addMore = SimpleCompetitions.keyboard.next();
				if(addMore.equalsIgnoreCase("Y")) {
					//add more
					addEntries();
					tryagain = false;
					break;
				}
				else if(addMore.equalsIgnoreCase("N")) {
					//do not add more
					tryagain = false;
					break;
				}
				else {  //invalid input
					System.out.println("Unsupported option. Please try again!");
				}
			}			
		}
	}

	/**
	 * This method is use for manual adding numbers
	 * @param manualChance is the manual Input chance
	 * @param autoChance is the auto Input chance
	 * @param ID is the competition ID
	 * @param mID is the member ID
	 * */
	public void manualInput(int manualChance, int autoChance,String ID, String mID){
		
		while(manualChance > 0) {  //manual enter for entries
			
			boolean tryAgain = true;  //determine to run or stop for next for loop
			SimpleCompetitions.keyboard.nextLine();  //remove \n
			while(tryAgain) {  //check the input entry validity

				System.out.println("Please enter 7 different numbers "
						+ "(from the range 1 to 35) separated by whitespace.");
				ArrayList <Integer> arr = new ArrayList<>();  
				//create an array list to store input numbers 
		    	String str;
		    	
		    	str = SimpleCompetitions.keyboard.nextLine();
		    	String [] div = str.split(" ");  //separate the input with whitespace 
		    	try {
		    		for (int i = 0; i < div.length;i++) {  
		    			//change the input type from String to Integer
			    		arr.add(Integer.parseInt(div[i]));
			    	}
			    	Collections.sort(arr);  // sort the numbers in ascending order
			    	
			    	if (arr.size() < ARRAY_LENGTH) {  //less the 7 numbers
			    		System.out.println("Invalid input! Fewer "
			    				+ "than 7 numbers are provided. Please try again!");
			    	}
			    	
			    	else if (arr.size() > ARRAY_LENGTH) {  //more than 7 numbers
			    		System.out.println("Invalid input! More "
			    				+ "than 7 numbers are provided. Please try again!");
			    	}
			    	
			    	else if (arr.size() == ARRAY_LENGTH) {  //equal to 7 numbers
			    		
			    		
			    		int sameNumber=0;  //same number appearance frequency
			    		
			    		for (int i=0; i < (arr.size()-1);i++) {  // check the same numbers
			    			
				    		if( arr.get(i) ==  arr.get(i+1)) {  
				    	//if there is a number entered twice sameNumber will increase by 1
				    			
				    			sameNumber += 1;
				    			
				    			}
				    	}
			    		
			    		if (sameNumber>0) {  // a number entered twice
			    			
			    			System.out.println("Invalid input! All numbers must be different!");
			    			
			    		}
			    		else if(arr.get(0)<0 || arr.get(ARRAY_LENGTH-1)>35) {
			    			// input number not in the range (1 to 35)
			    			System.out.println("Invalid input! All numbers must be "
			    					+ "in the range from 1 to 35!");
			    		}
			    		
			    		else if (sameNumber == 0) {  //correct input entry
			    			
			    			entryID +=1;  //total entryId++
			    			setnumbers();  //create an array
			    			
			    			for(int i = 0; i < arr.size(); i++) {  
			    				//change from array list to array 
			    				numbers [i] = arr.get(i);
			    			}
			    			boolean type = true;
			    			NumbersEntry numEn = new NumbersEntry(entryID,ID,mID,numbers,type);
			    			// add to NumberEntry class
			    			entryArray.add(numEn);
					    	tryAgain = false;
			    			manualChance -= 1;  //manual chance --
			    		}
			    	}
		    	}
		    	catch(NumberFormatException e) {  // invalid input
		    		System.out.println("Invalid input! "
		    				+ "Numbers are expected. Please try again!");
		    	}
			}
		}
	}
	
	/**
	 * This method is use to auto generate numbers
	 * @param Auto is the auto chance
	 * @param ID is the competition ID
	 * @param mID is the memberID
	 * */
	public void autoInput(int Auto, String ID, String mID) {
		
		int seed;
		AutoNumbersEntry auto;
		
		while (Auto > 0) {
			//auto generate numbers
			entryID +=1;
			if (!(SimpleCompetitions.testMode)) {
				//normal mode
				seed = new Random().nextInt(RANDOM_RANGE);
			}
			else {
				//testing mode
			    seed = this.entryID-1;
			}
			auto = new AutoNumbersEntry(entryID,ID,mID,seed);
			// invoke AutoNumbersEntry for generate numbers
			entryArray.add(auto);
    		Auto -=1;
		}
	}

	/**
	 * This method is use to print currently added entries
	 * */
	public void display() {
    	
    	System.out.println("The following entries have been added:");
    	
    	for (int i = inital; i < entryArray.size() ; i++) {
            System.out.printf("Entry ID: %-7d",entryArray.get(i).getEntryId());
            System.out.print("Numbers: ");
           
            Entry entry = entryArray.get(i);
            NumbersEntry numEn = (NumbersEntry) entry;
            int[] arr = numEn.getNumbers();
            if(entryArray.get(i).isManualType()) {  
            	
            	for(int j = 0; j < arr.length;j++) {  
            		//print the entry
            		
    				 System.out.printf("%2d",arr[j]);
    				 if (j != arr.length-1) {  
    				//to match the output format not printing whitespace for last digit
    					 System.out.print(" ");
    				 }
    			}
            	System.out.println();
            }
            
            else{
            	//print the auto entered entry 
            	for(int j = 0; j < arr.length;j++) {
        			System.out.printf("%2d" + " ",arr[j]);
             }
            	System.out.print("[Auto]");
    			System.out.println();	
           }
       }
	}
	
    /**
     * This method is used to draw Winner for
     * Lucky number competition
     * */
	@Override
	public void drawWinners() {
		Member member;
		int [] lucky = new int[ARRAY_LENGTH];  //Create an array to store the lucky entry
		int seed;
		if (!(SimpleCompetitions.testMode)) {
			//normal mode
			seed = new Random().nextInt();
		}
		else {
			//testing mode
		    seed = id;
		}
		
		AutoNumbersEntry auto = new AutoNumbersEntry();
		lucky = auto.createNumbers(seed);  //generate lucky entry
		
		System.out.print(" Type: LuckyNumbersCompetition\n");
		System.out.print("Lucky Numbers: ");
		
		for(int i = 0; i < lucky.length; i++) {
			System.out.printf("%2d" + " ",lucky[i]);
        }
		
       	System.out.print("[Auto]");
		System.out.println();
		
		for(Entry e : entryArray) {
			//calculate the prize and update the winning entries
			NumbersEntry entries = (NumbersEntry) e;
			int[] numbers = entries.getNumbers();
			
			e.setPrize(prize(compare(lucky,numbers)));//calculate and set prize
			
			if (e.getPrize()>0) {  //adding prize
				addWinning(e.getMemberId(),e.getPrize(),e);	
			}
		}
		
		System.out.println("Winning entries:");
		
		ArrayList<Entry> list = new ArrayList<>(winMap.values());
		Collections.sort(list, new Comparator<Entry>() {			
		public int compare(Entry entry1, Entry entry2) {
			//sorting the HashMap by convert and copy it to ArrayList
			 return entry1.getEntryId()-entry2.getEntryId();
			 }
		});
		
		 for(int i = 0; i<list.size();i++) {
			 //print out the winning entries
			 Entry entry = list.get(i);
			 NumbersEntry numEn = (NumbersEntry) entry;
			 
			 int entryID = list.get(i).getEntryId();
			 String memberID = list.get(i).getMemberId();
			 member = SimpleCompetitions.dp.searchMember(memberID);
			 String name = member.getMemberName();
			 int prize = list.get(i).getPrize();
			 
			 System.out.print("Member ID: "+ memberID+","+" Member Name: "+name+",");
		     System.out.printf(" Prize: %-5d\n",prize);
		     System.out.print("--> Entry ID: "+ entryID +"," +" Numbers: ");
		     int[] arr = numEn.getNumbers();
         	//print the auto entered entry 
         	 for(int j = 0; j < arr.length;j++) {
     			 System.out.printf("%2d" + " ",arr[j]);
          }
         	System.out.print("[Auto]");
 			System.out.println();	
		 }
	}
					

	/**
	 * This method is use for calculate 
	 * how many same numbers with the lucky numbers
	 * @param lucky is the lucky number
	 * @param numbers is the user numbers
	 * @return how many same numbers  
	 * */
	public int compare(int[] lucky, int[]numbers) {
		int same = 0;
		for (int j = 0; j < lucky.length; j++) {  //use brute force comparison
	    	   for (int k = 0; k < lucky.length; k++) {
	    		   if(numbers[k] == lucky[j]) {
	    			   same +=1; //if have same number 'same' + 1
	    			   } }
	    	   }
		return same;
	}
	

    /**
     * This method is use to calculate prize
     * @param same numbers
     * @return prize
     * */
	public int prize(int same) {
		
		int prize = 0;
		if(same >= LOW_PRIZE) {//same is greater than 1 have prize
			prize = prizes[same-LOW_PRIZE];
		}
		else {
			prize = 0;
		}
		return prize;
	
	}
	/**
	 * This method is use for adding the winning entry
	 * to WinMap
	 * @param mID is the memberID
	 * @param prize is the entry's prize
	 * @param e is Entry
	 */
	public void addWinning(String mID, int prize, Entry e) {
		if(!(winMap.containsKey(mID))||prize > winMap.get(mID).getPrize()) {
			//if winMap did not contain or prize is greater the previous
			winMap.put(mID, e);
		}
	}
	private int[] numbers;
	
	/**
	 * This method is use to 
	 * initialize the array numbers
	 * */
	public void setnumbers() {
		int[] numbers = new int[ARRAY_LENGTH];
		this.numbers = numbers;
	}

}
