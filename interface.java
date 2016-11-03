/* This is the file the main program
 * @amotan2
 */
public class Interface {
	
	public static void main(String[] args) {
		int wi = 0;
		boolean quitB = false;
		
		while (quitB == false){
			
			System.out.println("Welcome to the Workout Logger! Select an option below (Eg '1' to Add a workout): \n1) " +
					"add a workout\n2) remove a workout\n3) display all workouts\n4) display " +
					"all workouts with buddy\n5) display workouts for a certain location " +
					"(listing should be ordered by rating - best rated workout to lowest rated " +
					"workout)\n6) Personal Best (displays personal best time for a prompted milage\n7) quit" +
					"\nSelect an option above: ");
			
			wi = TextIO.getlnInt(); 						//wi for Welcome Input
			
			while (wi < 0 || wi > 7){
				TextIO.putln("Please enter value from 1 through 8");
				wi = TextIO.getlnInt();
			}
			
			if (wi == 1) {									//Add workout
				Workout.addAWorkout_helper();
			}
			else if(wi == 2) {								//Remove workout
				if(Workout.head == null)	TextIO.putln("You don't have any saved workouts");
				else Workout.head.removeAWorkout();
			}
			else if (wi == 3){								//Display all workouts.
				if(Workout.head == null)	TextIO.putln("You don't have any saved workouts");
				else Workout.head.displayAllWorkouts();
			}
			//(listing for 'all work outs with buddy' should start with most recent).	Also, edit the wording above as seems best.
			else if (wi == 4){	//Display all W/buddy
				if(Workout.head == null)	TextIO.putln("You don't have any saved workouts");
				else Workout.head.displayAllWorkoutsWithBuddy();
			}
			else if (wi == 5){								//Workouts at Location
				if(Workout.head == null)	TextIO.putln("You don't have any saved workouts");
				else Workout.head.displayWorkoutsAtLocation();
			}
			else if (wi == 6){								//Display Personal Best
				if(Workout.head == null)	TextIO.putln("You don't have any saved workouts");
				else Workout.head.personalBest();
			}
			else if (wi == 7){								//Quit
				TextIO.putln("Are you sure you want to quit? - all your data will be lost.");
				String quit = TextIO.getln();
				if(quit.toLowerCase().equals("y") || quit.toLowerCase().equals("yes")){
					TextIO.putln("Bye!");
					quitB=true;
					return;
				}
			}
			TextIO.putln("--------------------------------------------");

		}//End of while loop
	}//end main

}//end class

