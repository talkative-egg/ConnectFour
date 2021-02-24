package connectFour.domain;

/**
 * @author xuesheng (chenji@thayer.org)
 * Date created: Feb 12, 2021
 * Date due: Feb 24, 2021
 * This program simulates the popular board game -- Connect Four
 * This class represents a player of the game
 */

import javax.swing.JOptionPane;

public class JCPlayer {
	
	//private instance variables
	private char symbol;
	private String player; //could be "human" or "computer"
	private JCTimer timer;
	
	
	
	
	
	//default constructor
	public JCPlayer() {
		symbol = 'Y';
		player = "computer";
		timer = null;
	}
	
	//overloaded constructor
	public JCPlayer(char checkerSymbol, String gamePlayer) {
		symbol = checkerSymbol;
		player = gamePlayer;
		timer = null;
	}
	
	
	
	
	
	
	//methods
	
	//this method checks if time is up
	//if time is up, then let the computer take over the player's moves
	public void checkTimeUp() {
		if(timer != null && timer.timeUp()) {
			player = "computer";
			timer = null;
			
			String name = (symbol == 'R')? "Red":"Yellow";
			System.out.println(name + ", your time is up! Computer will take it from here\n");
		}
	}
	
	//this method generates a timer for this player
	public void timePlayer() {
		//instantiating a new JCTimer object using the default constructor
		timer = new JCTimer();
	}
	
	//returns the player, either "human" or "computer"
	public String getPlayer() {
		return player;
	}
	
	//returns the checker symbol of this player
	public char getCheckerSymbol() {
		return symbol;
	}
	
	//the parameter passes in the number of columns in the board
	//calls the method humanMove if this player is a human
	//calls the method computerMove if this player is a computer
	//returns the column of which the player should drop the checker
	public int move(int rangeEnd){
		
		if(player.equals("human")) {
			return humanMove(rangeEnd);
		}else if(player.equals("computer")) {
			return computerMove(rangeEnd);
		}else {
			throw new IllegalArgumentException("What the heck is the player?");
		}
		
	}
	
	//prompts the human player to enter the column of which the checker should be dropped
	//the parameter passes in the number of columns in the baord
	//returns the column of which the human player wants to drop the checker
	private int humanMove(int rangeEnd) {
		
		//words that prompt the player to enter column number
		String columnPrompt = (symbol == 'Y')? "Yellow, ":"Red, ";
		columnPrompt = columnPrompt + "enter your column choice (1-" + rangeEnd + ")";
		
		//the display on the input dialog
		String display = columnPrompt;
		
		//the input the player enters later
		String input;
		
		//we don't know yet if the time is up, so set it to false for now
		boolean timeUp = false;
		
		//prompts the user to enter column nnumber
		//if the given input is not an integer in the range [1, 7], ask again
		do {
			
			//how much time the player has left in String form
			String time;
			
			//start timer if the player is timed
			if(timer != null) {
				timer.startTimer();
				int timeLeft = timer.timeLeftInSecond();
				time = "\n" + timeLeft + " seconds left";
				timeUp = timer.timeUp();
			}else {
				time = "";
			}
			
			//prompts column
			input = JOptionPane.showInputDialog(display + time);
			
			//ends the program if user clicks cancel
			if(input == null) {
				System.exit(0);
			}
			
			//add "invalid input" to display because if the display is shown again, the player must have entered something weird
			display = "INVALID INPUT! " + columnPrompt;
			
			//ends the timer if there is one
			if(timer != null) {
				timer.endTimer();
			}
			
			//make sure the input is in numbers before converting it to an integer
			//ends the loop if the time is up or the user entered a valid input
		}while((!input.matches("[1-9]") || Integer.parseInt(input) > rangeEnd) && !timeUp);
		
		//if time is up, then return -1 to signal that the time is up
		if(timeUp) {
			return -1;
		}
		
		return Integer.parseInt(input);
		
	}
	
	//returns a random column number within the range [1,rangeEnd] that the computer player should drop the checker
	private int computerMove(int rangeEnd){
		
		int column = (int) (Math.random() * rangeEnd) + 1;
		
		return column;
		
	}

}
