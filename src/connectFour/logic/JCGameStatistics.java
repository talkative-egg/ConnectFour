package connectFour.logic;

/**
 * @author xuesheng (chenji@thayer.org)
 * Date created: Feb 12, 2021
 * Date due: FEb 24, 2021
 * This program simulates the popular board game -- Connect Four
 * This class tracks the statistics of multiple games
 */

import connectFour.domain.JCList;

public class JCGameStatistics {

	//private instance variables
	private JCList<String> redStatistics;
	private JCList<String> yellowStatistics;
	private int games;
	
	
	
	
	//default constructor
	public JCGameStatistics() {
		
		//instantiating a new JCList class using the default constructor
		redStatistics = new JCList<String>();
		//instantiating a new JCList class using the default constructor
		yellowStatistics = new JCList<String>();
		games = 0;
		
	}
	
	
	
	
	
	//methods
	
	//adds the info of the players to the lists
	public void addGamePlayers(String redPlayer, String yellowPlayer) {
		
		redStatistics.add(redPlayer + " ");
		yellowStatistics.add(yellowPlayer + " ");
		
	}
	
	//adds the results of the game to the lists
	public void addGameResults(int winner) {
		
		if(winner == 0) {
			redStatistics.replace(games, redStatistics.get(games) + "TIE");
			yellowStatistics.replace(games, yellowStatistics.get(games) + "TIE");
		}else if(winner == 1) {
			redStatistics.replace(games, redStatistics.get(games) + "WIN");
			yellowStatistics.replace(games, yellowStatistics.get(games) + "LOSE");
		}else if(winner == 2) {
			redStatistics.replace(games, redStatistics.get(games) + "LOSE");
			yellowStatistics.replace(games, yellowStatistics.get(games) + "WIN");
		}else {
			throw new IllegalArgumentException("Who won???");
		}
		
		games++;
		
	}
	
	//prints out the statistics
	public void printStatistics() {
		
		System.out.println("Game Statistics\n");
		
		for(int i = 0; i < games; i++) {
			
			System.out.println("Game " + (i+1));
			
			System.out.println("Red: " + redStatistics.get(i));
			System.out.println("Yellow: " + yellowStatistics.get(i));
			
			System.out.println();
			
		}
		
	}

}