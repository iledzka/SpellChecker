package SpellChecker;

import java.util.Iterator;

/**
 * This class implements a words set based on hash table,
 * and the IWords, IMonitor and IHashing interfaces.
 * Uses use open addressing with double hashing strategy.
 */
public class HTableWords implements IHashing, IWords, IMonitor {

	private float maxLF;
	private int size = 7;
	private String[] hashTable;
	private String DEFUNCT = "";
	private int nbWords;
	private int numberOfProbes = 0;
	private int numberOfOperations = 0;

	public HTableWords() {
		this.maxLF = 0.5f;
		hashTable = new String[size];
		this.nbWords = 0;
	}
	
	public HTableWords(float maxLF) {
		this.maxLF = maxLF;
		hashTable = new String[size];
		this.nbWords = 0;
	}
	
	/**
	 * Calculates exponent. To prevent overflows, a long type is used.
	 * Based on: https://stackoverflow.com/a/101613
	 * @param base
	 * @param exp
	 * @return
	 */
	private long power(long base, long exp)
	{
	    long result = 1;
	    while (exp != 0)
	    {
	        if ((exp & 1) == 1)
	            result *= base;
	        exp >>= 1;
	        base *= base;
	    }

	    return result;
	}
	
	/**
	 * Computes hash function using polynomial accumulation.
	 * Returns new hash code compressed using compression function to fit the current size of the hash table. 
	 */
	@Override
	public int giveCode(String s) {
		long prime = 33;
		long hashCode = 0;
		StringBuffer sb = new StringBuffer(s);

		for (int i = 0; i < sb.length(); i++) {
			hashCode += (long)sb.charAt(i) * this.power(prime, i);
		}
	
		return (int) (Math.abs(hashCode) % this.size);
		
	}

	/**
	 * Implements double hashing by computing second hash code probeVal and returns next index increased by this value. 
	 * If nextIndex is bigger that the current size of the hash table, nextIndex mod size is returned.
	 * @param index
	 * @return
	 */
	private int doProbe(String word, int index) {
		StringBuffer sb = new StringBuffer(word);
		int wordInt = 0;
		for (int i = 0; i < sb.length(); i++) {
			wordInt += (int)sb.charAt(i);
		}
		int probeVal = 7 - (wordInt % 7);
		int nextIndex = (index + probeVal) % this.size;
		return nextIndex;
	}
	
	/**
	 * Called when the number of elements in the hash table equals or is bigger than the load factor (defaults to 0.5).
	 * Resized the size of hash table array to the next prime that is at least double the size of the current size.
	 * Re-adds all elements with new hash codes that respect new hash table size.
	 */
	private void resizeAndRehash() {
		
			
		
		//find new size
		this.size = this.nextPrime(this.size);
		Iterator<String> iterator = this.allWords();
		
		this.hashTable = new String[this.size];
		this.nbWords = 0;
		
		while(iterator.hasNext()) {
			
			String word = iterator.next();
			try {
				this.addWord(word);
			} catch (WException e) {
				System.err.print(e.getMessage());
				System.err.println("WException thrown. Error when rehashing and adding a duplicate string.");
			}
			
		}
	}
	
	/**
	 * Finds next prime number after doubling the int size.
	 * 
	 * @param size
	 * @return
	 */
	private int nextPrime(int size) {
		int newSize = size * 2;
		
		while (newSize < Math.pow(size, 2)) {
			if (this.isPrime(newSize)) {
				break;
			}
			newSize++;
		}
		return newSize;
		
	}
	
	/**
	 * Checks if given int n ia a prime number. Based on https://www.mkyong.com/java/how-to-determine-a-prime-number-in-java/
	 * @param n
	 * @return
	 */
	private boolean isPrime(int n) {
	    if (n%2==0) {
	    	return false;
	    }
	    for (int i = 3; i*i <= n; i += 2) {
	        if ( n % i == 0)
	            return false;
	    }
	    return true;
	}

	/**
	 * Adds new word to the hash table. Throws WException if the element already exists.
	 */
	@Override
	public void addWord(String word) throws WException {
		
		this.numberOfOperations++;
		if (!this.wordExists(word)) {
			
			int code = this.giveCode(word);
			if (this.hashTable[code] == null || this.hashTable[code].equals(DEFUNCT)) {
				this.hashTable[code] = word;
			} else {
				int nextIndex = this.doProbe(word, code);
				while (this.hashTable[nextIndex] != null && !this.hashTable[nextIndex].equals(DEFUNCT) && code != nextIndex) {
					nextIndex = this.doProbe(word, nextIndex);
				}
				
				this.hashTable[nextIndex] = word;
				this.numberOfProbes++;
			}
			this.nbWords++;
			
			if (this.loadFactor() > this.maxLoadFactor()) {
				this.resizeAndRehash();
			}
			return;	
				
		} else {
			throw new WException("Word " + word + " already exists in the hash table! ");
		}
		
	}

	/**
	 * Deletes word from the hash table. Throws WException is such element doesn't exist in the structure.
	 */
	@Override
	public void delWord(String word) throws WException {
		this.numberOfOperations++;
		if (!this.wordExists(word)) {
			throw new WException("\nNot possible to remove element " + word + " - doesn't exist!");
		} else {
			this.hashTable[this.findIndexOf(word)] = DEFUNCT;
			this.nbWords--;
		}
		
	}
	
	/**
	 * Called by delWord(String word) function.
	 * Finds index of a given word, if exists, while respecting the current hash table size.
	 * @param word
	 * @return
	 */
	private int findIndexOf(String word) {
		int code = this.giveCode(word);
		if (this.hashTable[code] != null && this.hashTable[code].equals(word)) {
			return code;
		} else {
			int nextIndex = this.doProbe(word, code);
			while (this.hashTable[nextIndex] != null && !this.hashTable[nextIndex].equals(word) && code != nextIndex) {
				nextIndex = this.doProbe(word, nextIndex);
			}
			this.numberOfProbes++;
			return nextIndex;
		}
	}

	/**
	 * Checks whether given word exists in the hash table.
	 * First, computes the hash code for a word using giveCode(String word) function.
	 * If word equals null, returns false.
	 * If word in the hash table equals the word passed to the function, returns true.
	 * Else, the given slot must contain either DEFUNCT object or other word.
	 * Linear probing is executed by calling doProbe(int hashCode) function.
	 * Returned new index is used to check whether the element exists in the hash table.
	 * Probing is executed until either the word or null is found, or all elements in the hash table 
	 * were looked at (new index equals initial index). 
	 */
	@Override
	public boolean wordExists(String word) {
		this.numberOfOperations++;
		int hashCode = this.giveCode(word);
		if (this.hashTable[hashCode] == null) {
			return false;
		} else if (this.hashTable[hashCode].equals(word)) {
			return true;
		} else {
			this.numberOfProbes++;
			int nextIndex = this.doProbe(word, hashCode);
			while (this.hashTable[nextIndex] != null && !this.hashTable[nextIndex].equals(word) && hashCode != nextIndex) {
				
				nextIndex = this.doProbe(word, nextIndex);
			}
			if (this.hashTable[nextIndex] != null && this.hashTable[nextIndex].equals(word)) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Returns current number of elements in the hash table.
	 */
	@Override
	public int nbWords() {
		return this.nbWords;
	}

	/** 
	 * Returns HTableWordsIterator which contains all words in the hash table.
	 */
	@Override
	public Iterator<String> allWords() {
		Iterator<String> iterator = new HTableWordsIterator(this.hashTable, this.nbWords, this.DEFUNCT);
		return iterator;
	}

	/**
	 * Returns maximum load factor.
	 */
	@Override
	public float maxLoadFactor() {
		return this.maxLF;
	}

	/**
	 * Returns the current load factor.
	 */
	@Override
	public float loadFactor() {
		return (float) this.nbWords / size;
	}

	/**
	 * Returns the average number of probes performed so far.
	 */
	@Override
	public float averageProbes() {
		return (float) this.numberOfProbes / this.numberOfOperations;
	}
	
}
