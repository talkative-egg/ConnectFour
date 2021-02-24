package connectFour.domain;

/**
 * @author xuesheng (chenji@thayer.org)
 * Date created: Feb 12, 2021
 * Date due: Feb 24, 2021
 * This program simulates the popular board game -- Connect Four
 * This class calculates the total time used by a human player
 */

public class JCTimer {
	
	//private instance variables
	private long totalTimeMil; //total time used in milliseconds
	private long startTimeMil; //starting time of one move
	
	
	
	//default constructor
	public JCTimer() {
		totalTimeMil = 0;
		startTimeMil = 0;
	}
	
	
	
	
	
	//methods
	
	//convert total time in to seconds and return it
	public int getTotalTimeInSecond() {
		return (int)(totalTimeMil / 1000);
	}
	
	//starts the timer of one move
	public void startTimer() {
		startTimeMil = System.currentTimeMillis();
	}
	
	//ends the timer of one move
	public void endTimer() {
		totalTimeMil = totalTimeMil + System.currentTimeMillis() - startTimeMil;
	}
	
	//returns true if time is up, returns false otherwise
	public boolean timeUp() {
		return timeLeftInSecond() <= 0;
	}
	
	//one player has 3 minutes in total for all their moves
	//this method returns how many seconds a player has left to make the rest of their moves
	public int timeLeftInSecond() {
		return 180 - (int)(totalTimeMil / 1000);
	}

}
