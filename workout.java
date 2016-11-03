/**@author amotan2
 * Check the append method: Also, is the Object created in the main of interface a part of this list?
 * All Variables & Getter and setter methods in this file
 * All Actions handled in Workout Link file.
 */


public class Workout {
	//Variable declarations
	
	private String location;					//Location of workout
	private String buddy;						//null if workout was solo
	private double miles;						//miles covered in workout
	private int date;							//Starting date
	private long duration;						//Duration is in milliseconds (Start to end).
	private int rating;							//Rate workout from 1(awesome) to 10(bad).
	public Workout next;						//To next node
	public Workout prev;						//Link to previous Node
	public static int count=0;					//Keeping track of number of links.
	public static Workout head = null;			//head
	public boolean printed = false;				//Specifically for Best rating at Location
//	public boolean updated;						//Updated, for personal Best
	
	//Constructors. First letter of variable + in. (date AND eDate not included yet) 
	
	public Workout(String lin, String bin, double min, int rin, Workout next, int date, long dur){	//Only one used
		setLocation(lin);	setBuddy(bin);	setMiles(min);	setRating(rin); setDuration(dur);
		this.next = next; this.date = date;
	}	
	public Workout() {}	//Empty Constructor
	
	//Getter and Setter methods:
	public void setLocation(String in){this.location = in;}				//Set Location
	public void setBuddy(String in){									//Set Buddy
		if (in == "") this.buddy = null;
		else this.buddy = in;
		}
	public void setMiles(double in){this.miles = in;}					//Set Miles
	public void setDate(int in){										//Sets current date and time.
		this.date = in;
		}
	public void setDuration(long in){this.duration = in;}				//Set Duration: Auto
	public void setRating(int in){										//Set Rating
		if (in > 10){
			setRating(TextIO.getlnInt());
		}
		else this.rating = in;
		}
	
	public String getLocation(){return this.location;}					//Get Methods
	public String getBuddy(){return this.buddy;}						//Get Methods...
	public double getMiles(){return this.miles;}
	public int getDate(){return this.date;}
	public long getDuration(){return this.duration;}
	public int getRating(){return this.rating;}

	
/*	1) add a workout: Prompts all the questions to fill the fields.
	2) remove a workout: Asks while workout to remove
	3) display all workouts (listing should start with most recent)
	4) display all workouts with buddy: will have to do a linear search with the name
	5) display workouts for a certain location (listing should be ordered by rating - best rated
		workout to lowest rated workout)=> linear search or sort maybe.
	7) PB: display personal best time for a prompted location. When ever
	8) quit
*/
	
	//Operation of the Application
	public static Workout addAWorkout(String loc, String bud, int miles, int rate, Workout w, int date, long dur){
		if(w==null) return new Workout(loc,bud,miles,rate,w,date, dur);
		if (w.date > date) return new Workout(loc,bud,miles,rate,w,date, dur);
		else{
			w.next=addAWorkout(loc,bud,miles,rate,w.next,date, dur);
		}
		return w;
	}
	public static void addAWorkout_helper(){					//This get ID, and collects ALL the data required.
//		setID();												//Assign an ID

		TextIO.putln("Adding a new Workout");
		TextIO.putln("What is the location of the workout?");
		String loc = TextIO.getln();
		while (loc.equals("")){
			TextIO.putln("A location is required. Please enter a location");
			loc = TextIO.getln();
		}
		TextIO.putln("Who are you working out with today? Leave blank and press enter if working out solo");
		String bud = TextIO.getln();
		TextIO.putln("Great! Hit return to start workout:");	//Starts Workout
		String tempInput;
		long dur = 0;
		int secondtime=0;
		long timeStart=0;
		while (secondtime < 2){
			tempInput = "";
			tempInput = TextIO.getln();
				if (tempInput.isEmpty() && secondtime == 1){	//Tracking 2nd return
					dur = ((System.nanoTime() - timeStart)/1000000); 	//Sending Duration to Object variable
					secondtime++;
					TextIO.putln("Workout Ended!");
					tempInput = "";
				}
				if (tempInput.isEmpty() && secondtime == 0){	//Checking 1st Return
					timeStart = System.nanoTime();
					TextIO.putln("Workout Started!");
					TextIO.putln("Please hit return to end workout:"); 
					secondtime++;
				}
		}
		TextIO.putln("How many miles did you run today?");
		int mil = TextIO.getlnInt();
		TextIO.putln("Please rate the workout from 1(awesome) to 10(bad)");
		int rate = TextIO.getlnInt();
		TextIO.putln("Please enter today's date (MMDD)");
		int date = TextIO.getlnInt();
		TextIO.putln("Thankyou! Your entry has been added");
		
		Workout.count++; //Add entry to count
		
		head = addAWorkout(loc, bud, mil, rate, head, date, dur);
		
		// We have the link with data, Now add it to the Link.
	}
	
	public void removeAWorkout(){
		TextIO.putln("Please select the number of the workout you wish to remove");
		int n = TextIO.getlnInt();
		removeAWorkout_helper(n,1);
		
	}
	public void removeAWorkout_helper(int n, int count){
		if (n == 0 || n > Workout.count){
			TextIO.putln("Such a workout entry does not exist!");
			return;
		}
		else if (n == 1){
			Workout.count--;
			Workout.head = this.next;
			TextIO.putln("The entry has been removed");
		}
		else if (n == count + 1){
			if (this.next.next == null){
				this.next = null; 
				Workout.count--;
			}
			else{ this.next = this.next.next;	//Links this.next to the next of the Link after it
			Workout.count--;
			TextIO.putln("The entry has been removed");
			}
		}
		else {
			if (this.next.next == null){
				TextIO.putln("This.next.next is null and n = " + n + " and count is " + count);
			}
			else{
			count++;
			this.next.removeAWorkout_helper(n,count);
			}
		}
		
	}//end removeAWorkout_helper
	//toString method works fine.. Tested
	
	public void personalBest(){
		TextIO.putln("Please enter a milage you need the personal best time for (eg 5.5)");
		double milage = TextIO.getlnDouble();
		TextIO.putln("Workout # - Location - Miles - Duration - Rating - Date(MMDD)");
		Workout best = personalBest_helper(milage, head);
		if (best == null) TextIO.putln("Sorry! The milage you searched for doesn't match any of our records");
		else TextIO.putln("1\t" + toString(best));			//Prints out the best object
	}
	public Workout personalBest_helper(double milage, Workout best){
		//if next is not null, check head's duration to this.next and add to best... or not
		//else check the last node, update best if needed and return best.
		
		if (this.next != null) {
			if (best.miles != milage && this.miles == milage)
				best = this;
			else if (this.duration < best.duration && this.miles == milage) best = this;
			return this.next.personalBest_helper(milage, best);
		}
		else{
			if (this.duration < best.duration && this.miles == milage) best = this;
			if (best.miles == milage) return best;
			else{
				return  null;
			}
		}
	}//end _helper
	
	public void displayWorkoutsAtLocation(){
		TextIO.putln("Please enter location you like to display workouts for");
		String in = TextIO.getln();
//		displayWorkoutsAtLocation_helper(in, head);
		Workout temp = highestRatedAtLocation(in, head);
		if (temp != null) TextIO.putln(toString(temp));
		if (temp == null) TextIO.putln("The value was not updated");
		
	}
	//Possible solution to returning the 'head' as best when nothing else can get assigned.
	//Make a dummy workout Object and Assign it a rating of Int.MAX_VALUE. Then it will have to get updated by anything
	
	public void displayWorkoutsAtLocation_helper(String in, Workout highest){
		//Display workouts by Location and the Best Rated first.
		/* Every Link will have an boolean. We go through the whole list.
		 * Every time, the recursion goes through the whole thing, it updates to best, 
		 * At the end, the best is sent to the toString and the boolean is changed to true.
		 * And the method runs again.
		 */
		if (this.next !=null){
			//Keep check for the highest one, and update
		}
		
	}
	
	//Returns highest rated workouts in the desired location :: Only from boolean's that still false 
	public Workout highestRatedAtLocation(String location, Workout best){
		//Workout 'best' will always be the 'head' to start with
		if (this.printed == false && this.location.equalsIgnoreCase(location)){
			best = this;	//Assigns first 'NON printed' object to best.
		}
		if (this.next == null){
			if (this.location.equalsIgnoreCase(location) && this.rating < best.rating && printed == false) {
				best = this;
			}
				best.printed = true;
				return best;
		}
		if (this.printed == true) {
			return this.next.highestRatedAtLocation(location, best);
		}
		if (this.next != null){
			if (this.location.equalsIgnoreCase(location) && this.rating < best.rating){	//At this point, only non-printed entries evaluated
				best = this;
			}
			return this.next.highestRatedAtLocation(location, best);
		}
//		else{
//			if (this.location.equalsIgnoreCase(location) && this.rating < head.rating && printed == false) best = this;
//			best.printed = true;
//			return best;						//Once it runs out of the String, it prints the best rated one out
//		}
		return best;
	} //end sub _helper
	
	public void displayAllWorkoutsWithBuddy(){
		TextIO.putln("Please specify who you want to display workouts with");
		String buddyName = TextIO.getln();
		displayAllWorkoutsWithBuddy_helper(buddyName, 1);
	}
	
	public void displayAllWorkoutsWithBuddy_helper(String buddyName, int n){
		if (this.next !=null){
			if (this.buddy.equalsIgnoreCase(buddyName)){			//Ignore's case
				TextIO.putln(n +"\t"+ toString(this));
				n++;
			}
			this.next.displayAllWorkoutsWithBuddy_helper(buddyName, n);
		}
		else{
			if (this.buddy.equalsIgnoreCase(buddyName)){			//Ignore's case
				TextIO.putln(n +"\t"+ toString(this));
				n++;
			}
		}
		
	}//end _helper

	/*
	 * private String location;					//Location of workout
	private String buddy;						//null if workout was solo
	private double miles;						//miles covered in workout
	private Date date;							//Starting date 
	private long duration;						//Duration is in milliseconds (Start to end).
	private int rating;							//Rate workout from 1(awesome) to 10(bad).
	public Workout next;						//To next node
	public Workout prev;						//Link to previous Node
	private int ID;			
	 */
	
	public void displayAllWorkouts(){
		TextIO.putln("Here is a list of all the Workouts");
		TextIO.putln("Workout # - Location - Buddy - Miles - Duration - Rating - Date");
		displayAllWorkous_helper(1);
		}
	public void displayAllWorkous_helper(int n){
		if (this.next != null){
			TextIO.putln(n+"\t"+toString(this));
			n++;
			this.next.displayAllWorkous_helper(n);
		}
		else {
			System.out.println(n+"\t"+toString(this));
			return;
		}
	}
	
	public String toString(Workout in){
		return "    " +in.location + " , " +in.buddy + " , " + in.miles+ " , "+ in.duration + " , " +in.rating + " , " + in.date;
	}
	
}//end class

