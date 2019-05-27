package SpellChecker;

/**
 * Measure time of execution in milliseconds.
 * @author Iza Ledzka
 *
 */
public class Timer {

	private long start;
	private long end;
	
	public Timer() {
		this.start = 0;
		this.end = 0;
	}
	
	/**
	 * Start measuring time.
	 */
	public void start() {
		this.start = System.currentTimeMillis();
	}
	
	/**
	 * Stop measuring time.
	 */
	public void stop() {
		this.end = System.currentTimeMillis();
	}
	
	/**
	 * Returns measured time between start and stop. 
	 * Returned value is in milliseconds.
	 * @return
	 */
	public long getMeasuredTime() {
		return this.end - this.start;
	}
}
