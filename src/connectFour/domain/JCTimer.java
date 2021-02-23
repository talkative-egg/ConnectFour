package connectFour.domain;

public class JCTimer {
	
	private long totalTimeMil;
	private long startTimeMil;
	
	public JCTimer() {
		totalTimeMil = 0;
	}
	
	public int getTotalTimeInSecond() {
		return (int)(totalTimeMil / 1000);
	}
	
	public void startTimer() {
		startTimeMil = System.currentTimeMillis();
	}
	
	public void endTimer() {
		totalTimeMil = totalTimeMil + System.currentTimeMillis() - startTimeMil;
	}
	
	public boolean timeUp() {
		return timeLeftInSecond() <= 0;
	}
	
	public int timeLeftInSecond() {
		return 180 - (int)(totalTimeMil / 1000);
	}

}
