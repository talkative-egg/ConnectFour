package connectFour.logic;

import connectFour.domain.JCList;

public class JCGameStatistics {
	
	private JCList<String> redStatistics;
	private JCList<String> yellowStatistics;
	private int games;
	
	public JCGameStatistics() {
		redStatistics = new JCList<>();
		yellowStatistics = new JCList<>();
		games = 0;
	}
	
	public void addGamePlayers(String redPlayer, String yellowPlayer) {
		
		redStatistics.add(redPlayer + " ");
		yellowStatistics.add(yellowPlayer + " ");
		
	}
	
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