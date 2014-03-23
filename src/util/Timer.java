package util;

public class Timer {	
	private long m_startTime;
	private long m_elapsedTime;
	
	public Timer() {
		startTimer();
	}

	public void startTimer() {
		m_startTime = System.nanoTime();
	}
	
	public void stopTimer() {
		m_elapsedTime = System.nanoTime() - m_startTime;
	}
	
	public long getNanoTime() {
		return m_elapsedTime;
	}
	
	public double getTimeMS() {
		return m_elapsedTime / 1000000.0;
	}
}
