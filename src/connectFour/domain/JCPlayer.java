package connectFour.domain;

/**
 * @author xuesheng (chenji@thayer.org)
 * Date created: Feb 12, 2021
 * Date due: FEb 24, 2021
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
	
	public void changeToAI() {
		
		if(player.equals("computer")) {
			player = "computerAI";
		}
		
	}
	
	public void checkTimeUp() {
		if(timer != null && timer.timeUp()) {
			player = "computer";
			timer = null;
			
			String name = (symbol == 'R')? "Red":"Yellow";
			System.out.println(name + ", your time is up! Computer will take it from here\n");
		}
	}
	
	public void timePlayer() {
		timer = new JCTimer();
	}
	
	public String getPlayer() {
		return player;
	}
	
	//returns the checker symbol of this player
	public char getCheckerSymbol() {
		return symbol;
	}
	
	//the parameter passes in if the human player should be noticed that the column previously entered was full
	//calls the method humanMove if this player is a human
	//calls the method computerMove if this player is a computer
	//returns the column of which the player should drop the checker
	public int move(boolean previousColumnFull, int rangeEnd, JCBoard board){
		
		if(player.equals("human")) {
			return humanMove(previousColumnFull, rangeEnd);
		}else if(player.equals("computer")) {
			return computerMove(rangeEnd);
		}else if(player.equals("computerAI")){
			return computerAIMove(board);
		}else {
			throw new IllegalArgumentException("What the heck is the player?");
		}
		
	}
	
	//prompts the human player to enter the column of which the checker should be dropped
	//the parameter passes in if the human player should be noticed that the column previously entered was full
	//returns the column of which the human player wants to drop the checker
	private int humanMove(boolean previousColumnFull, int rangeEnd) {
		
		//words that prompts the player to enter column number
		String columnPrompt = (symbol == 'Y')? "Yellow, ":"Red, ";
		columnPrompt = columnPrompt + "enter your column choice (1-" + rangeEnd + ")";
		String display = (previousColumnFull)? ("COLUMN FULL! " + columnPrompt):(columnPrompt);	
		String input;
		boolean timeUp = false;
		
		//prompts the user to enter column nnumber
		//if the given input is not an integer in the range [1, 7], ask again
		do {
			
			String time;
			
			if(timer != null) {
				timer.startTimer();
				int timeLeft = timer.timeLeftInSecond();
				time = "\n" + timeLeft + " seconds left";
				timeUp = timer.timeUp();
			}else {
				time = "";
			}
			
			input = JOptionPane.showInputDialog(display + time);
			
			if(input == null) {
				System.exit(0);
			}
			
			display = "INVALID INPUT! " + columnPrompt;
			
			if(timer != null) {
				timer.endTimer();
			}
			
		}while((!input.matches("[1-9]") || Integer.parseInt(input) > rangeEnd) && !timeUp);
		
		if(timeUp) {
			return -1;
		}
		
		return Integer.parseInt(input);
		
	}
	
	//returns a random column number within the range [1,7] that the computer player should drop the checker
	private int computerMove(int rangeEnd){
		
		int column = (int) (Math.random() * rangeEnd) + 1;
		
		return column;
		
	}
	
	private int computerAIMove(JCBoard board) {
		
		for(int col = 1; col <= board.getColumnNumber(); col++) {
			
			if(board.addChecker(symbol, col)) {
				if(board.connectedInARow()) {
					board.takeFromColumn(col);
					return col;
				}else {
					
					board.takeFromColumn(col);
					
				}
				
			}
			
			
		}
		
		int[] moves = new int[5];
		for(int i = 0; i < moves.length; i++) {
			moves[i] = 1;
		}
		
		char otherSymbol = (symbol == 'R')? 'Y':'R';
		
		int column = (board.getColumnNumber() + 1) / 2;
		
		int maxWin = -100;
		int columnDecision = 0;
		
		for(int i = 1; i <= board.getColumnNumber(); i++) {
			
			//int win = aiAssist(board, moves, symbol, otherSymbol);
			int win = aiAssist(board, column);
			
			System.out.println("column " + column + " wins " + win);
			
			if(win > maxWin) {
				maxWin = win;
				columnDecision = column;
			}
			
			column = (i % 2 == 0)? (column+i) : (column-i);
			
		}
		
		return columnDecision;
		
		
	}
	
	private int aiAssist(JCBoard board, int column, char symbol)
	
	/*private int aiRecursion(JCBoard board, int column, char symbol, int value) {
		
		if(column > board.getColumnNumber()) {
			return 0;
		}else {
			int wins = aiRecursion(board, column + 1, symbol, value);
			if(board.addChecker(symbol, column)) {
				wins = wins + ((board.connectedInARow())? value : 0);
				board.takeFromColumn(column);
			}
			return wins;
		}
		
	}*/
	
	/*private int aiAssist(JCBoard board, int[] moves, char symbol, char otherSymbol) {
		
		boolean finalReturn = true;
		
		for(int i = 1; i < moves.length; i++) {
			if(moves[i] <= 7) {
				finalReturn = false;
			}
		}
		
		if(finalReturn){
			return 0;
		}else{
			
			int round = moves[0];
			int currentMoveColumn = moves[round];
			
			if(currentMoveColumn > board.getColumnNumber()) {
				moves[0] = 1;
				return aiAssist(board, moves, symbol, otherSymbol);
			}else if(round >= moves.length){
				
			}else {
				char dropSymbol = (round % 2 == 0)? symbol:otherSymbol;
				int weight = (round % 2 == 0)? round:(-1)*round;
				weight = (weight >= 0)? (weight * weight) : ((-1) * weight * weight);
				if(board.addChecker(dropSymbol, currentMoveColumn)) {
					if(board.connectedInARow()) {
						board.display();
						System.out.println("Possible scenario");
						moves[0]++;
						board.takeFromColumn(currentMoveColumn);
						return aiAssist(board, moves, symbol, otherSymbol) + weight;
					}
					
					moves[round]++;
					
					int wins = aiAssist(board, moves, symbol, otherSymbol);
					
					board.takeFromColumn(currentMoveColumn);
					
					return wins;
				}else {
					moves[round]++;
					return aiAssist(board, moves, symbol, otherSymbol);
				}
			}
			
		}
		
	}*/
	
	/*private int aiAssist(JCBoard board, int column) {
		
		char otherSymbol = (symbol == 'R')? 'Y':'R';
		
		if(!board.addChecker(symbol, column)) {
			return -100;
		}
		
		int wins = 0;
		
		for(int column1 = 1; column1 <= board.getColumnNumber(); column1++) {
			
			if(board.addChecker(otherSymbol, column1)) {
				
				if(board.connectedInARow()) {
					wins -= 5;
				}else {
					
					for(int column2 = 1; column2 <= board.getColumnNumber(); column2++) {
						
						if(board.addChecker(symbol, column2)) {
							
							if(board.connectedInARow()) {
								wins++;
							}
							
							board.takeFromColumn(column2);
							
						}
						
						
						
					}
					
				}
				
				board.takeFromColumn(column1);
				
			}
			
		}
		
		board.takeFromColumn(column);
		
		return wins;
		
	}*/

}
