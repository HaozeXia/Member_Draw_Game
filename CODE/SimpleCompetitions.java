/*
 * Student name: Haoze Xia
 * Student ID: 1131343
 * LMS username: haozex
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class SimpleCompetitions {
	/**
	 * 
	 * SimpleCompetitions is the main class of this package
	 * one Simple competition can have multiple-competition
	 * */
	
	public static Scanner keyboard;  //all class in the package can use this scanner
	public static DataProvider dp;  //create data provider object
	public static boolean testMode =true;  //record test mode or normal mode
	private boolean currentActive = false;   // record the current competition active or not
	private Competition activeComp;  //create an Competition object
	private int compID;  // competition ID
	private String compName;  // competition name
	private String mode;    //lucky mode or random mode
	private int activeNum;  //active competition number
	private ArrayList<Competition> compArray = new ArrayList<>();  
	//create an array to record all competition
	private boolean drawWinner = false;  //check draw Winner
	private String loadComp;  // load competitions
	
	/**
	 * This method create (Random/Lucky) Competitions and add 
	 * to Competition ArrayList 
	 * @param typeComp the Competition type
	 * @param compName the Competition name
	 * */

    public void addNewCompetition(String typeComp,String compName) {
		currentActive = true;
		
		if (typeComp.equalsIgnoreCase("R")) {  //input "r" for random pick type Competition
			
			compID = compArray.size();
			compID ++;
			
			String type = "RandomPickCompetition";
			System.out.println("A new competition has been created!");
			System.out.println("Competition ID: "+compID+","+" Competition Name: "+compName+","
			+" Type: "+type);
			
			activeComp = new 
					RandomPickCompetition(compID,compName);//create a new random pick competition
			
			compArray.add(activeComp);  //record in an array
			activeComp.status = true;  //set the active status to true
		}
		
		else if (typeComp.equalsIgnoreCase("L")) {  //input "l" for lucky number type Competition
			
			compID = compArray.size();
			String type = "LuckyNumbersCompetition";
			compID ++;
			
			System.out.println("A new competition has been created!");
			System.out.println("Competition ID: "+compID+","+
			" Competition Name: "+compName+","+" Type: "+type);
			
			activeComp = new 
					LuckyNumbersCompetition(compID,compName);
			//create a new random pick competition
			
			compArray.add(activeComp);  //record in an array
			activeComp.status = true;  //set the active status to true
		}
        
    }

    /**
     * This method is to print the report of all competitions
     * */
    public void report() {
    	
    	if(activeComp != null && activeComp.status == true) {	
    		// to determine is there has an active Competition
    		activeNum = 1;
    	}
    	else {
    		activeNum = 0;
    	}
    	
    	System.out.println("----SUMMARY REPORT----");
    	System.out.println("+Number of completed competitions: " + (compArray.size()-activeNum));
    	System.out.println("+Number of active competitions: " + activeNum);
    	System.out.println();
    	
    	String active;  //yes/no refer to the active status
    	
    	for(Competition comp : compArray) {  //for each loop to print out all competitions
    		
    		int ID = comp.getId();
    		String name = comp.getName();
    		int numEntry =comp.entryArray.size();
    		int numWin = comp.winMap.size();
    		int sumPrize = comp.getSumPrize();
    		
            if(comp != activeComp || 
            		(comp == activeComp && compArray.get(compArray.size()-1).status == false)) {
            	// if this competition not the current, and current status is false
    			active = "no";
    			
    			System.out.println("Competition ID: "+ID+","+" name: "
    			+name+","+" active: "+active);
        		System.out.println("Number of entries: "+numEntry);
        		System.out.println("Number of winning entries: "+numWin);
        		System.out.println("Total awarded prizes: "+sumPrize);
        		
        		if(comp != compArray.get(compArray.size()-1)) {
        			// if this competition is not the last one
        			System.out.println();
        		}
    		}
            
            if(comp == activeComp && compArray.get(compArray.size()-1).status == true) {
            	// if this competition is the current with status true
    			active = "yes";
    			
    			System.out.println("Competition ID: "+ID+","+" "
    					+ "name: "+name+","+" active: "+active);
        		System.out.println("Number of entries: "+numEntry);
        		
        		currentActive = true;
    		}
    	}
    }

    /**
    * Main program that uses the main SimpleCompetitions class
    * @param args main program arguments
    */
    public static void main(String[] args){
    	
    	//Create an object of the SimpleCompetitions class
    	keyboard = new Scanner(System.in); 
        SimpleCompetitions sc = new SimpleCompetitions();  
        sc.loadComp();
        if(sc.loadComp.equalsIgnoreCase("N")){
        	// if loading file the mode will be load in
        	sc.testMode();
        }
        sc.fileName();
        sc.menu();
    }
    
    /**
     * This method is used for loading competitions records from file
     * */
    private void loadComp() {

    	boolean tryagain = true;
    	
    	System.out.println("----WELCOME TO SIMPLE COMPETITIONS APP----");
    	
    	while (tryagain) {
        	System.out.println("Load competitions from file? (Y/N)?");
        	loadComp = keyboard.next();
        	
        	if (loadComp.equalsIgnoreCase("Y")) {
        		// if user input "y"
        		System.out.println("File name:");
        		
        		String inputFile;
        		inputFile = keyboard.next();
        		try {
					loadFile(inputFile);
				} catch (DataAccessException e) {
					e.printStackTrace();
					System.exit(0);
				}
            	tryagain = false;
        	}
        		
        	else if (loadComp.equalsIgnoreCase("N")) {
        		// if user input "n"
        		tryagain = false;
        	}
        	
        	else {
        		// Invalid input re-enter
        		System.out.println("Unsupported option. Please try again!");
        	}
    	}
    	
    }
    
    /**
     * This method is used to load binary file
     * @param fileName is the input file name
     * @throws DataAccessException 
     * */
    private void loadFile(String fileName) throws DataAccessException {
    	try {
    		// try to loading file
			ObjectInputStream InputStream = 
					new ObjectInputStream(new FileInputStream(fileName));
	    	Competition c;
	    	
			while((c = (Competition)InputStream.readObject())!=null) {
				
				compArray.add(c);  //add from file to ArrayList
				
				if(c.status == true) {
					//if current status is true
					activeComp = c;
					drawWinner = true;
				}
				
				if(c.status != true) {
					//if current status is false
					currentActive = false;
					drawWinner = false;
				}
			}
			InputStream.close();
		}
    	
		catch(IOException e) {
			// catch the IOException
			throw new DataAccessException(fileName);

			
		} catch (ClassNotFoundException e) {
			// catch ClassNotFoundException
			throw new DataAccessException(fileName);
		}

    }
    
    /**
     * This method is used to determine is test mode or normal mode
     * */
    private void testMode() {
    	
    	boolean tryagain = true;
    	
    	while (tryagain) {
 
    		System.out.println("Which mode would you like to run?"
    				+ " (Type T for Testing, and N for Normal mode):");
    		
        	mode = keyboard.next();
        	
    		if (mode.equalsIgnoreCase("T")) {
    			// if input is "t" the competition will run on testing mode
    			testMode = true;
    			tryagain = false;
    			break;
    		}
    		
    		else if (mode.equalsIgnoreCase("N")) {
    			// if input is "n" the competition will run on normal mode
    			testMode = false;
    			tryagain = false;
    			break;
    		}
    		
    		else {
    			System.out.println("Invalid mode! Please choose again.");
    		}
    	}
    	
    }
    
    /**
     * This method is main menu
     * to invoke the correct option
     * */
    private void menu() {
    	String menuNum;
    	
    	while(true) {
    		
    		menuSelect();  //call display menu
    		menuNum = keyboard.next();
    		
    		if (menuNum.equalsIgnoreCase("1")) {
    			// Option 1 create competition
    			if(currentActive) {
    				// there is an active competition
    				System.out.println("There is an active competition. "
        					+ "SimpleCompetitions does "
        					+ "not support concurrent competitions!");
    			}
    			
    			else {
    				
    				while(true) {
    					
    					System.out.println("Type of competition "
    							+ "(L: LuckyNumbers, R: RandomPick)?:");
    					
        				String typeComp = keyboard.next();
        				keyboard.nextLine();  //remove the /n
        				
        				if(!(typeComp.equalsIgnoreCase("R")|typeComp.equalsIgnoreCase("L"))) {
        					// Invalid input, re-enter it
        					System.out.println("Invalid competition type! "
        							+ "Please choose again.");		
        				}
        				
        				else {
        					
        					System.out.println("Competition name: ");
            				compName = keyboard.nextLine();
            				addNewCompetition(typeComp,compName);  // create an new competition
            				break;  //break the loop
        				}
    				}
    			}
    		}
    		
    		else if(menuNum.equalsIgnoreCase("2")) {
    			// Option 2 add entries
    			if (currentActive) {
    				// there is an active competition
    				keyboard.nextLine();
    				activeComp.addEntries();  //call the competition's add entries method
    				drawWinner = true;  // once finished adding it call draw Winner
    			}
    			
    			else {
    				// Invalid Input
        			System.out.println("There is no active competition. Please create one!");
    			}
    		}
    		
    		else if(menuNum.equalsIgnoreCase("3")) {
    			//Option 3 draw Winner
    			if (currentActive && drawWinner) {
    				// if there is a active competition and draw Winner is true
    				activeComp.status = false;  //set the active status to false
    				
    				compID = compArray.get(compArray.size()-1).getId();
    				compName = compArray.get(compArray.size()-1).getName();
    				
    				System.out.print("Competition ID: "+compID+","+
    				" Competition Name: "+compName+",");
    				
    				activeComp.drawWinners();  //invoke draw winner method
    				currentActive = false;
    				drawWinner = false;

    			}
    			
    			else if(currentActive && !drawWinner) {
    				// invalid input no entries yet
    				System.out.println("The current competition has no entries yet!");
    			}
    			
    			else {
    				// invalid input no active competition
    				System.out.println("There is no active competition. Please create one!");
    			}
    		}
    		
    		else if(menuNum.equalsIgnoreCase("4")) {
    			//Option 4 report for all competitions
    			if(compArray.size()!= 0) {
    				//check is there have at least 1 competitions
    				report();
    			}
    			
    			else {
    				// invalid input
    				System.out.println("No competition has been created yet!");
    			}
    			
    		}
    		
    		else if(menuNum.equalsIgnoreCase("5")) {
    			//Option 5 exit and save the file
    			
    			while(true) {
    				
    				System.out.println("Save competitions to file? (Y/N)?");
    				
    				String saveFile = keyboard.next();
    				
    				if(saveFile.equalsIgnoreCase("Y")) {
    					// user want to save file
    					saveFile(); // invoke save file method
    					dp.writeBills();  //invoke data provider's method to update file
    					
    					System.out.println("Competitions have been saved to file.");
    	    			System.out.println("The bill file "
    	    					+ "has also been automatically updated.");
    					System.out.println("Goodbye!");
    					System.exit(0);
    				}
    				
    				else if(saveFile.equalsIgnoreCase("N")) {
    					// user do not want to save file
    					System.out.println("Goodbye!");
    					System.exit(0);
    				}
    				
    				else {
    					// invalid input
    					System.out.println("Unsupported option. Please try again!");
    				}
    					
    			}

    		}
    		else {
    			System.out.println("Unsupported option. Please try again!");
    		}
    		
    	}	
    }
    
    /**
     * This method is used to save File
     * */
    public void saveFile() {
    	
    	System.out.println("File name:");
    	
    	keyboard.nextLine();  //remove \n
    	String fileName = keyboard.nextLine();
    	
    	try {
    		//try to create an output stream
			ObjectOutputStream outputStream = 
					new ObjectOutputStream(new FileOutputStream(fileName));
			
			for(Competition comp : compArray) {
				//write every competition info to the binary file
				outputStream.writeObject(comp);
			}
			outputStream.writeObject(null);
			outputStream.close();  //close
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(0);
		}   	
    }
    
    /**
     * This method is used to input data files
     * */
    public void fileName() {
    	
    	System.out.println("Member file: ");
    	keyboard.nextLine();  //remove \n
    	String memberFileName = keyboard.nextLine();
    	System.out.println("Bill file: ");
    	String billFileName = keyboard.nextLine();
    	
    	try {
    		// try to read and record the file info
		    dp = new DataProvider(memberFileName, billFileName);
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (DataFormatException e) {
			e.printStackTrace();
			System.exit(0);
		}
    }
    
    /**This method is used to display the main menu
     * */
    public void menuSelect() {
    	System.out.println("Please select an option. Type 5 to exit.");
    	System.out.println("1. Create a new competition");
    	System.out.println("2. Add new entries");
    	System.out.println("3. Draw winners");
    	System.out.println("4. Get a summary report");
    	System.out.println("5. Exit");
    }
    
}
