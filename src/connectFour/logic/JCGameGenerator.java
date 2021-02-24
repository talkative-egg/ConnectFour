package connectFour.logic;

/**
 * @author xuesheng (chenji@thayer.org)
 * Date created: Feb 12, 2021
 * Date due: Feb 24, 2021
 * This program simulates the popular board game -- Connect Four
 * This class generates the game and interacts with the board and the players
 */

import connectFour.domain.*;
import javax.swing.JOptionPane;

public class JCGameGenerator {
	
	//private instance variables
	private JCBoard board;
	private JCPlayer red;
	private JCPlayer yellow;
	private JCPlayer currentPlayer; //this variable either references to red or yellow, represents the player about to make a move
	
	
	
	
	//default constructor
	public JCGameGenerator() {
		board = null;
		red = null;
		yellow = null;
		currentPlayer = null;
	}
	
	
	
	
	
	//methods
	
	//returns the identity of red player
	public String getRedPlayer() {
		return red.getPlayer();
	}
	
	//returns the identity of yellow player
	public String getYellowPlayer() {
		return yellow.getPlayer();
	}
	
	//takes in the symbol of the player, sets the player, and returns the player
	private JCPlayer setOnePlayer(char symbol) {
		
		String playerColor = (symbol == 'R')? "Red":"Yellow";
		JCPlayer player = null;
		
		//asks the user if the player is human or computer
		do {
			
			String input = JOptionPane.showInputDialog("Is " + playerColor + " human or computer?");
			
			try {
				input = input.trim().toLowerCase();
			}catch(NullPointerException e) {
				System.exit(0);
			}
			
			if(input.contains("human")) {
				//instantiating a new JCPlayer object using the overloaded constructor
				player = new JCPlayer(symbol, "human");
			}else if(input.contains("computer")){
				//instantiating a new JCPlayer object using the overloaded constructor
				player = new JCPlayer(symbol,"computer");
			}
			
		}while(player == null);
		
		//if player is human, asks the user if it should be timed
		if(player.getPlayer().equals("human")) {
			
			String timed = JOptionPane.showInputDialog("Is " + playerColor + " timed (3 minuted total)? (yes/no)");
			
			try {
				timed = timed.toLowerCase();
			}catch(NullPointerException e) {
				System.exit(0);
			}
			
			if(timed.contains("yes")) {
				player.timePlayer();
			}
			
		}
		
		return player;
		
	}
	
	//this method sets up the players
	private void setPlayers() {
		
		red = setOnePlayer('R');
		yellow = setOnePlayer('Y');
		
	}
	
	//this method sets up the game board
	private void setBoard() {
		
		do {
			
			String input = JOptionPane.showInputDialog("Default or customized board (and connect pieces)?");
			
			try {
				input = input.trim().toLowerCase();
			}catch(NullPointerException e) {
				System.exit(0);
			}
			
			if(input.contains("default")) {
				//instantiating a new JCBoard object using the default constructor
				board = new JCBoard();
			}else if(input.contains("custom")) {
				
				int row = promptNumber("How many rows?", 4, 9);
				int column = promptNumber("How many columns?", 4, 9);
				
				//the connect pieces must be greater than 3 and smaller than or equal to the smaller one of row and column
				int connect = (row < column)? 
						promptNumber("Connect how many pieces?", 3, row):promptNumber("Connect how many pieces?", 3, column);
				
				//instantiating a new JCBoard object using the overloaded constructor
				board = new JCBoard(row, column, connect);
				
			}
			
		}while(board == null);
		
	}
	
	//takes in the message for the prompt, the range the input must be in
	//returns the number inputted by the user
	private int promptNumber(String message, int rangeStart, int rangeEnd) {
		
		String display = message;
		String input;
		
		do {
			
			input = JOptionPane.showInputDialog(display + " [" + rangeStart + ", " + rangeEnd + "]");
			
			if(input == null) {
				System.exit(0);
			}
			
			display = "INVALID INPUT! " + message;
			
		}while(!input.matches("[1-9]") || Integer.parseInt(input) < rangeStart || Integer.parseInt(input) > rangeEnd);
		
		return Integer.parseInt(input);
		
	}
	
	//parameter takes in the statistics object to add player infos to
	//this method sets up the board and the players
	public void startGame(JCGameStatistics stats) {
		
		setBoard();
		setPlayers();
		
		stats.addGamePlayers(red.getPlayer(), yellow.getPlayer());
		
		//red makes the first move
		currentPlayer = red;
		
		board.display();
		
	}
	
	//this method asks the currentPlayer to make a move
	public void nextRound(){
		
		int column;
		
		//we don't know if the column if full or not, set it to false for now
		boolean columnFull = false;
		
		//asks the user to enter column number until the number is valid
		do {
			
			//if the column was full last time and it's a human player, then tell them it was full
			if(columnFull && currentPlayer.getPlayer().equals("human")) {
				System.out.println("THE COLUMN YOU ENTERED WAS FULL");
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
			}
			
			column = currentPlayer.move(board.getColumnNumber());
			
			//if the loop continues, it must mean that the column is full
			columnFull = true;
			
			currentPlayer.checkTimeUp();
			
			//if columns is smaller than 0, then it means that the time is up, then move again (but it is computer now)
			if(column < 0) {
				column = currentPlayer.move(board.getColumnNumber());
			}
			
		}while(!isValidMove(column));
		
		board.addChecker(currentPlayer.getCheckerSymbol(), column);
		
		//delays one second if the player is computer
		if(currentPlayer.getPlayer().contains("computer")) {
			try {
				Thread.sleep(1000);
			}catch(InterruptedException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		
		//if red just made the move, set currentPlayer to yellow so that yellow makes the move next time
		//and vice versa
		currentPlayer = (currentPlayer == red)? yellow : red;
		
		board.display();
		
	}
	
	//return true if the column is not full and false otherwise
	private boolean isValidMove(int column) {
		
		return !board.columnFull(column);
		
	}
	
	//returns true if there are the checkers are in a row or the board is filled (i.e. when the game ends)
	//returns false otherwise
	public boolean gameOver() {
		
		return board.connectedInARow() || board.isFull();
		
	}
	
	//returns 1 if red wins, 2 if yellow wins, 0 if it is a tie, and -1 if the game is not end and there is a glitch
	//this makes it easier for the stats class to know who won
	public int getWinnerForStats() {
		
		if(board.connectedInARow()) { //if this is true, then there is definitely a winner
			
			if(currentPlayer == yellow) { //if the next player is yellow, then it means that red won
				return 1;
			}else if(currentPlayer == red) { //if the next player is red, then is means that yellow won
				return 2;
			}else {
				return -1;
			}
			
		}else if(board.isFull()) { //this means the board is full and there is no winner, it's a tie
			return 0;
		}else {
			return -1;
		}
		
		
	}
	
	//returns the String version of the winner to print out
	public String getWinner() {
		
		if(board.connectedInARow()) { //if this is true, then there is definitely a winner
			
			if(currentPlayer == yellow) { //if the next player is yellow, then it means that red won
				return "Red Won!";
			}else if(currentPlayer == red) { //if the next player is red, then is means that yellow won
				return "Yellow Won!";
			}else {
				throw new IllegalArgumentException();
			}
			
		}else if(board.isFull()) { //this means the board is full and there is no winner, it's a tie
			return "It's a tie!";
		}else {
			throw new IllegalArgumentException();
		}
		
		
	}
	

}
