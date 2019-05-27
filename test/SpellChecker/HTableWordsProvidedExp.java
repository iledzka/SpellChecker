package SpellChecker;

public class HTableWordsProvidedExp {

	public static void main(String[] args) {
		runDifferentLoadFactors();
	}

	/** Runs the hash table at different load factors and print out the average probe numbers versus the running time.
	 * The average probe number should go up as the max load factor goes up.
	 */
	private static void runDifferentLoadFactors() {
		System.out.println("Runs the hash table at different load factors and print out the average probe numbers versus the running time.");
		System.out.println("The average probe number should go up as the max load factor goes up.");

		float maxLF = (float) 0.5;
		HTableWords h = new HTableWords(maxLF);
		long startTime,finishTime;
		double time;
		String word;

		while (maxLF < 0.99 ){
			startTime = System.currentTimeMillis();
			h = new HTableWords(maxLF);			
			try{
				for (int k = 0; k < 10000; ++k) {
					word = "w" + k;
					h.addWord(word);
				}
				for ( int k = 0; k < 10000; ++k ){
					word = "w" + k;
					h.delWord(word);
				}
				finishTime = System.currentTimeMillis();
				time = finishTime - startTime;
				System.out.println(String.format("For load factor %9f, average num. of  probes is %9f time in milseconds is %9f",maxLF,h.averageProbes(),time));
				maxLF = maxLF+ (float) 0.05;
			}
			catch (WException e) {
				System.err.print("Failure");
			}
		}
	}

}
