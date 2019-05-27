package SpellChecker;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * HTableWordsIterator is a custom iterator that returns all values that are not null in array of strings (HTableWords).
 * The implementation was inspired by this article http://www.java2s.com/Tutorial/Java/0140__Collections/ImplementsanjavautilIteratoroveranyarray.htm
 * 
 * Usage: create new HTableWordsIterator object with constructor HTableWordsIterator(String[] array, int nbWords).
 * Iterator<String> = new HTableWordsIterator(array, nbWords);
 * 
 * @author iza
 *
 */
public class HTableWordsIterator implements Iterator<String> {

	private String[] array;
	private int nbWords = 0;
	private String DEFUNCT;
	//current iterator index
	private int index = 0;
	
	public HTableWordsIterator(String[] array, int nbWords, String DEFUNCT) {
		this.array = array.clone();
		this.nbWords = nbWords;
		this.DEFUNCT = DEFUNCT;
	}
	
	@Override
	public boolean hasNext() {
		return nbWords > 0;
	}

	@Override
	public String next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		}
		int i = this.index;
		while ( i < this.array.length && (this.array[i] == null || this.array[i].equals(DEFUNCT))) {
			i++;
		}
		if (i == this.array.length) {
			throw new NoSuchElementException();
		}
		nbWords--;
		this.index = i + 1;
		return this.array[i];
	}

}
