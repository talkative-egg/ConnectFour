package connectFour.domain;

/**
 * @author xuesheng (chenji@thayer.org)
 * Date created: Feb 12, 2021
 * Date due: FEb 24, 2021
 * This program simulates the popular board game -- Connect Four
 * This class represents the game board with the checkers
 */

public class JCBoard {

	//private instance variable
	private char[][] board;
	private int connect; //represents how many pieces connected to win
	
	
	
	
	
	//default constructor
	public JCBoard(){
		board = new char[6][7]; //a 6*7 2d array that represents the game board
		connect = 4;
		
		newBoards(); //sets the board
	}
	
	//overloaded constructor
	public JCBoard(int row, int column, int connectCheckers) {
		board = new char[row][column];
		connect = connectCheckers;
		
		newBoards();
	}
	
	
	
	
	//methods
	
	//returns how many columns the board has
	public int getColumnNumber() {
		return board[0].length;
	}
	
	//sets all the values in the boards to 'O' to represent null
	public void newBoards() {
		
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				board[i][j] = 'O';
			}
		}
		
	}
	
	//returns false if the column is full
	//returns true if the checker is successfully dropped into the column specified by the parameter
	//symbol represents the symbol of the player that displays in the board
	//column represents the column to which the checker should be dropped
	public boolean addChecker(char symbol, int column) {
		
		if(columnFull(column)) {
			return false;
		}else {
			dropChecker(symbol, column);
			return true;
		}
		
	}
	
	//helper method
	//drops the given symbol into the column specified by the parameter
	private void dropChecker(char symbol, int column) {
		
		int row = board.length; //starts from the bottom row
		boolean dropped = false; //becomes true when the checker is set in the board
		
		//starts from the bottom row and goes to row 1
		//the checker is set to the first empty spot along the way
		while(!dropped) {
			
			if(board[row - 1][column - 1] == 'O') {
				board[row - 1][column - 1] = symbol;
				dropped = true;
			}
			
			row--;
		}
		
	}
	
	public void takeFromColumn(int column) {
		
		int row = 1; //starts from the bottom row
		boolean taken = false; //becomes true when the checker is set in the board
		
		//starts from the bottom row and goes to row 1
		//the checker is set to the first empty spot along the way
		while(!taken && row <= board.length) {
			
			if(board[row - 1][column - 1] != 'O') {
				board[row - 1][column - 1] = 'O';
				taken = true;
			}
			
			row++;
		}
		
	}
	
	//returns true if the column specified by the parameter is full with checkers
	//returns false otherwise
	private boolean columnFull(int column) {
		
		int count = 0;
		
		for(char[] rows : board) {
			if(rows[column - 1] != 'O') {
				count++;
			}
		}
		
		return count == board.length;
		
	}
	
	//returns true if all spots of the board are filled
	//returns false otherwise
	public boolean isFull() {
		
		for(int col = 1; col <= board[0].length; col++) {
			if(!columnFull(col)) {
				return false;
			}
		}
		
		return true;
		
	}
	
	//returns true if there are ${connect} checkers of the same symbol in a row, which means a player wins
	//returns false otherwise
	public boolean connectedInARow() {
		
		return connectedHorizontal() || connectedVertical() || connectedUpwardDiagonal() || connectedDownwardDiagonal();
		
	}
	
	//returns true if there are ${connect} checkers of the same symbol connected horizontally
	//returns false otherwise
	private boolean connectedHorizontal() {
		
		//goes through the spots that have at least (${connect} - 1) amount of spots on its right
		//for each spot, check if it has a checker and is the same as the (${connect} - 1) spots on its right
		//if there is a spot of such case, return true
		for(char[] row : board) {
			for(int j = 0; j <= board[0].length - connect; j++) {
				
				boolean connectedHorizontal = true;
				
				//checks if the spot has a checker in it
				if(row[j] == 'O') {
					connectedHorizontal = false;
				}
				
				
				//checks the spots on its right
				for(int count = 1; count < connect; count++) {
					if(row[j] != row[j + count]) {
						connectedHorizontal = false;
					}
				}
				
				//returns true immediately if ${connect} amount of checkers are connected horizontally
				if(connectedHorizontal) {
					return true;
				}
				
			}
		}
		
		return false;
		
	}
	
	//returns true if there are ${connect} checkers of the same symbol connected vertically
	//returns false otherwise
	private boolean connectedVertical() {
		
		//goes through the spots that have at least (${connect} - 1) spots above it
		//for each spot, check if it has a checker and is the same as the (${connect} - 1) spots above it
		//if there is a spot of such case, return true
		for(int j = 0; j < board[0].length; j++) {
			for(int i = board.length - 1; i >= connect - 1; i--) {
				
				boolean connectedVertical = true;
				
				//checks if there is a checker in the spot
				if(board[i][j] == 'O') {
					connectedVertical = false;
				}
				
				
				//checks the spots above it
				for(int count = 1; count < connect; count++) {
					if(board[i][j] != board[i - count][j]) {
						connectedVertical = false;
					}
				}
				
				//returns true immediately if ${connect} amount of checkers are connected vertically
				if(connectedVertical) {
					return true;
				}
				
			}
		}
		
		return false;
		
	}
	
	//returns true if there are a specified amount checkers of the same symbol connected diagonally upward
	//returns false otherwise
	private boolean connectedUpwardDiagonal() {
		
		//goes through the left four spots of the bottom three row
		//for each spot, check it is the same as the three spots on its topright
		for(int i = connect - 1; i < board.length; i++) {
			
			for(int j = 0; j <= board[i].length - connect; j++) {
				
				boolean connectedDiagonal = true;
				
				if(board[i][j] == 'O') {
					connectedDiagonal = false;
				}
				
				for(int count = 1; count < connect; count++) {
					if(board[i][j] != board[i - count][j + count]) {
						connectedDiagonal = false;
					}
				}
				
				if(connectedDiagonal) {
					return true;
				}
				
			}
			
		}
		
		return false;
		
	}
	
	//returns true if there are four checkers of the same symbol connected diagonally downward
	//returns false otherwise
	private boolean connectedDownwardDiagonal() {
		
		//goes through the left four spots of the top three row
		//for each spot, check it is the same as the three spots on its bottomright
		for(int i = 0; i <= board.length - connect; i++) {
			
			for(int j = 0; j <= board[i].length - connect; j++) {
				
				boolean connectedDownwardDiagonal = true;
				
				if(board[i][j] == 'O') {
					connectedDownwardDiagonal = false;
				}
				
				for(int count = 1; count < connect; count++) {
					if(board[i][j] != board[i + count][j + count]) {
						connectedDownwardDiagonal = false;
					}
				}
				
				if(connectedDownwardDiagonal) {
					return true;
				}
				
				
			}
			
		}
		
		return false;
		
	}
	
	//prints out the board
	//'O' represents that there is no checker in the spot
	public void display() {
		
		System.out.print("    ");
		
		for(int col = 1; col <= board[0].length; col++) {
			System.out.print("[" + col + "]");
		}
		
		System.out.println();
		
		for(int i = 0; i < board.length; i++) {
			
			System.out.print( "[" + (i+1) + "]" + "  ");
			
			for(int j = 0; j < board[i].length; j++) {
				
				System.out.print(board[i][j] + "  ");
				
			}
			
			System.out.println();
			
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
	}
	
}
