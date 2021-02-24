package connectFour;

/**
 * @author xuesheng (chenji@thayer.org)
 * Date created: Feb 12, 2021
 * Date due: Feb 24, 2021
 * This program simulates the popular board game -- Connect Four
 * This is a driver class that sets up the game
 */

import connectFour.logic.*;
import javax.swing.JOptionPane;

public class JCConnectFour {
	
	public static void main(String[] args) {
		
		//instantiating a new JCGameGenerator object using the default constructor
		JCGameGenerator game = new JCGameGenerator();
		
		//instantiating a new JCGameStatistics object using the default constructor
		JCGameStatistics stats = new JCGameStatistics();
		
		//declared to represent the user input
		String input;
		
		//starts a game
		//then starts new games while the user enters "yes"
		do {
			
			game.startGame(stats);
			
			//the players make moves until the game is over
			do {
				game.nextRound();
			}while(!game.gameOver());
			
			System.out.println(game.getWinner());
			
			int winner = game.getWinnerForStats();
			stats.addGameResults(winner);
			
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			
			try {
				input = JOptionPane.showInputDialog("New game? (yes/no)");
				input = input.trim().toLowerCase();
			}catch(NullPointerException e) {
				input = "no";
			}
			
			
		}while(input.contains("yes"));
		
		//prints the statistics of the games
		stats.printStatistics();
		
	}

}
