package connectFour.logic;

/**
 * @author xuesheng (chenji@thayer.org)
 * Date created: Feb 12, 2021
 * Date due: FEb 24, 2021
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
	
	public String getRedPlayer() {
		return red.getPlayer();
	}
	
	public String getYellowPlayer() {
		return yellow.getPlayer();
	}
	
	//this method sets up the players, asks the user whether each player is human or computer
	private void setPlayers() {
		
		do {
			
			String player1 = JOptionPane.showInputDialog("Is red human or computer?");
			
			try {
				player1 = player1.trim().toLowerCase();
			}catch(NullPointerException e) {
				System.exit(0);
			}
			
			if(player1.contains("human")) {
				red = new JCPlayer('R', "human");
			}else if(player1.contains("computer")){
				red = new JCPlayer('R',"computer");
			}
			
		}while(red == null);
		
		if(red.getPlayer().equals("human")) {
			
			String timed = JOptionPane.showInputDialog("Is Red timed (3 minuted total)? (yes/no)");
			
			try {
				timed = timed.toLowerCase();
			}catch(NullPointerException e) {
				System.exit(0);
			}
			
			if(timed.contains("yes")) {
				red.timePlayer();
			}
			
		}else if(red.getPlayer().equals("computer")){
			
			String ai = JOptionPane.showInputDialog("Is Red an AI? (yes/no)");
			
			try {
				ai = ai.toLowerCase();
			}catch(NullPointerException e) {
				System.exit(0);
			}
			
			if(ai.contains("yes") || ai.contains("ai")) {
				red.changeToAI();
			}
			
		}
		
		do {
			
			String player2 = JOptionPane.showInputDialog("Is yellow human or computer?");
			
			try {
				player2 = player2.trim().toLowerCase();
			}catch(NullPointerException e) {
				System.exit(0);
			}
			
			if(player2.contains("human")) {
				yellow = new JCPlayer('Y', "human");
			}else if(player2.contains("computer")){
				yellow = new JCPlayer('Y',"computer");
			}
			
		}while(yellow == null);
		
		if(yellow.getPlayer().equals("human")) {
			
			String timed = JOptionPane.showInputDialog("Is Yellow timed (3 minutes total)? (yes/no)");
			
			try {
				timed = timed.toLowerCase();
			}catch(NullPointerException e) {
				System.exit(0);
			}
			
			if(timed.contains("yes")) {
				yellow.timePlayer();
			}
			
		}else if(yellow.getPlayer().equals("computer")) {
			
			String ai = JOptionPane.showInputDialog("Is Yellow an AI? (yes/no)");
			
			try {
				ai = ai.toLowerCase();
			}catch(NullPointerException e) {
				System.exit(0);
			}
			
			if(ai.contains("yes") || ai.contains("ai")) {
				yellow.changeToAI();
			}
			
		}
		
	}
	
	private void setBoard() {
		
		do {
			
			String input = JOptionPane.showInputDialog("Default or customized board (and connect pieces)?");
			
			try {
				input = input.trim().toLowerCase();
			}catch(NullPointerException e) {
				System.exit(0);
			}
			
			if(input.contains("default")) {
				board = new JCBoard();
			}else if(input.contains("custom")) {
				
				int row = promptNumber("How many rows?", 4, 9);
				int column = promptNumber("How many columns?", 4, 9);
				
				int connect = (row < column)? 
						promptNumber("Connect how many pieces?", 3, row):promptNumber("Connect how many pieces?", 3, column);
				
				board = new JCBoard(row, column, connect);
				
			}
			
		}while(board == null);
		
	}
	
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
		boolean columnFull = false;
		
		do {
			
			column = currentPlayer.move(columnFull, board.getColumnNumber(), board);
			
			columnFull = true;
			
			currentPlayer.checkTimeUp();
			
			if(column < 0) {
				column = currentPlayer.move(columnFull, board.getColumnNumber(), board);
			}
			
		}while(!isValidMove(currentPlayer.getCheckerSymbol(), column));
		
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
	
	//this method checks if the column is valid to drop the checker in
	//if the column is valid, then drop the checker and return true
	//if not, return false
	private boolean isValidMove(char symbol, int column) {
		
		return board.addChecker(symbol, column);
		
	}
	
	//returns true if there are four checkers in a row or the board is filled (i.e. when the game ends)
	//returns false otherwise
	public boolean gameOver() {
		
		return board.connectedInARow() || board.isFull();
		
	}
	
	//returns 1 if red wins, 2 if yellow wins, 0 if it is a tie, and -1 if the game is not end and there is a glitch
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
