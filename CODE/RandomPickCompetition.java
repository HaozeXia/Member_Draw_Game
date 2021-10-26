import java.io.Serializable;
import java.util.Random;

/*
 * Student name: Haoze Xia
 * Student ID: 1131343
 * LMS username: haozex
 */

public class RandomPickCompetition extends Competition implements Serializable{
    /**
	 * This class is used for Random Pick Competition
	 * is extend from Competitions class
	 * and implements the Serializable interface
	 * two main method in this class
	 * drawWinners and add Entries
	 */
	private static final long serialVersionUID = 1L;  //serializable id
	private final int FIRST_PRIZE = 50000;
    private final int SECOND_PRIZE = 5000;
    private final int THIRD_PRIZE = 1000;
    private final int[] prizes = {FIRST_PRIZE, SECOND_PRIZE, THIRD_PRIZE};  //prize array
    private int entryID;
	private int inital;  //position for printing
    private final int MAX_WINNING_ENTRIES = 3;
    private final int oneChance = 50; //$50 for one entry
    
    /**This is a constructor of random pick competition
     * @param id is the competition ID
     * @param Name is the competition name
     * */
    public RandomPickCompetition(int id, String Name) {
    	super(id,Name);
    	
    }
    
    /**
     * This is a null constructor
     * */
    public RandomPickCompetition() {
    	
    }
    
    /**This is a constructor of random pick competition
     * @param entryID is the competition entry ID
     * @param Name is the competition name
     * */
    public RandomPickCompetition(String name, int entryID) {
    	super(name, entryID);
    	
    }
	
    /**
     * This method is used to draw Winner for
     * random pick competition
     * */
    @Override
    public void drawWinners() {
    	
        Random randomGenerator = null;  //initialized randomGenerator
        Member member;  //member object

        if (SimpleCompetitions.testMode) {
        	//testing mode
            randomGenerator = new Random(this.getId());
            
        } else {
        	//normal mode
            randomGenerator = new Random();
        }
        
        System.out.print(" Type: RandomPickCompetition\n");
        System.out.println("Winning entries:");
        
        int winningEntryCount = 0;
        
        while (winningEntryCount < MAX_WINNING_ENTRIES) {
        	//generate three winning entry
        	
            int winningEntryIndex = randomGenerator.nextInt(entryArray.size());
            //random generate an entry index
	
            Entry winningEntry = entryArray.get(winningEntryIndex);
            // let winning entry equal to the entry with the generated index 
            
            if (winningEntry.getPrize() == 0) {
            	//first selected entry is the first prize...until the third
                int currentPrize = prizes[winningEntryCount];
                winningEntry.setPrize(currentPrize);
                winningEntryCount++;               
            }
            
            String mID = winningEntry.getMemberId();
            int prize = winningEntry.getPrize();
            addWinning(mID,prize,winningEntry);  
            // invoke add winning method to update in the Hashmap
        }
        
		for(String k : winMap.keySet()) {
			// print out all winning entries
			String memberID =k;
			member = SimpleCompetitions.dp.searchMember(memberID);
	        String name = member.getMemberName();
	        int prize = winMap.get(memberID).getPrize();
	        int entryID = winMap.get(memberID).getEntryId();
	        
			System.out.print("Member ID: "+ memberID+","+" "
					+ "Member Name: "+name+","+" Entry ID: "+entryID+",");
			System.out.printf(" Prize: %-5d\n",prize);
			
		}
    }

    /**
     * This method is used to update info for winning entries
     * @param mID is the member ID
     * @param prize is the entry's prize
     * @param e is the Entry object
     * */
    public void addWinning(String mID, int prize, Entry e) {
    	
		if(!(winMap.containsKey(mID))|| prize > winMap.get(mID).getPrize()) {
			//if this winning entry not in HashMap or prize is higher than before
			winMap.put(mID, e);
		}
	}


	/**
	 * This method is to use to add entries
	 * */
	@Override
	public void addEntries() {
		String ID;
		inital = entryID;  //the initial position of the entry
		
		System.out.println("Bill ID: ");
		
		ID = SimpleCompetitions.keyboard.next();  //read bill ID
		
		Bill bill;
		
		bill = SimpleCompetitions.dp.searchBill(ID);
		// search is this ID is valid bill ID
		
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
			addEntries();  //re-enter
			return;
		}
		
		if (SimpleCompetitions.dp.searchBill(ID) == null) {
			// this bill id is not in the file
			System.out.println("This bill does not exist. Please try again.");
			addEntries();  //re-enter it
		}
		
		else if (bill.getMemberID() == "") {
			// this bill ID do not have a member ID
			System.out.println("This bill has no member id. Please try again.");
			addEntries();  //re-enter
		}
		
		else if(bill.billStatus()== true) {
			//this bill has no more chance to add
			System.out.println("This bill has already been used "
					+ "for a competition. Please try again.");
			
			addEntries(); //re-enter it
		}
		
		else {  // valid bill ID
			
			double billAmount =bill.getTotalAmount();  //bill amount
			int totalChance = (int) Math.floor(billAmount/oneChance);  //total Chance
			bill.setStatus(true);  //set this bill is used
			
			System.out.println("This bill "+ "($"+billAmount+") "+
			"is eligible for "+totalChance+" entries.");
			
			String billID = bill.getBillID();
			String memberID = bill.getMemberID();
			
			while(totalChance > 0) {
				//create new Entry object for each entry
				entryID++;
				Entry entry = new Entry(entryID,billID,memberID);
				entryArray.add(entry);
				totalChance--;
			}
			
			display();  //invoke display to print out what just added
			addMoreEntry();  //ask the user want to add more entries
		}
	
	}
	
	/**
	 * This method is use to ask user want to add more entries
	 * */
	public void addMoreEntry() {
		
		System.out.println("Add more entries (Y/N)?");
		String addMore = SimpleCompetitions.keyboard.next();
		
		if(addMore.equalsIgnoreCase("Y")) {
			//user want to add more
			addEntries();
		}
		else if(addMore.equalsIgnoreCase("N")) {

		}
		else {
			//invalid input re-enter
			System.out.println("Unsupported option. Please try again!");
			addMoreEntry();
		}
	
	}
	
	/**
	 * This method is use for print the just added entries
	 * */
	public void display() {
		
		System.out.println("The following entries have been automatically generated:");
		
		for (int i = inital; i < entryArray.size() ; i++) {
			
			System.out.printf("Entry ID: %-6d",entryArray.get(i).getEntryId());
			System.out.println();
		}
	}
}
