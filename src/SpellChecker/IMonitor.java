package SpellChecker;

/**
 * Interface your hash table has to implement.
 */
public interface IMonitor {

	/**
	 * Returns the maximum authorised load factor.
	 */
	public float maxLoadFactor();

	/**
	 * Returns the current load factor.
	 */
	public float loadFactor();

	/**
	 * Returns the average number of probes.
	 */
	public float averageProbes();

}
