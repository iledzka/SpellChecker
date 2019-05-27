package SpellChecker;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.Test;

public class HTableWordsTest {

	/** testGiveCode1: check if giveCode() method generates expected code */
	@Test
	public void testGiveCode1() {
	
			float maxLF = (float) 0.5;
			HTableWords h = new HTableWords(maxLF);
			String word = "abc";
			int hashCode = h.giveCode(word);
			assertTrue(hashCode == 3);
	}
	
	/** testGiveCode2: check if giveCode() method generates expected code */
	@Test
	public void testGiveCode2() {
	
			float maxLF = (float) 0.5;
			HTableWords h = new HTableWords(maxLF);
			String word = "z";
			int hashCode = h.giveCode(word);
			assertTrue(hashCode == 3);
	}
	
	/** testAdd1Entry1: add an element and number of entries is 1 */
	@Test
	public void testAdd1Entry1() {
		try {
			float maxLF = (float) 0.5;
			HTableWords h = new HTableWords(maxLF);
			String word = "abc";
			h.addWord(word);
			assertTrue(h.nbWords() == 1);
		} catch (WException e) {
			fail();
		}
	}	
		
	@Test
	public void testIterator() {
		try {
			float maxLF = (float) 0.5;
			HTableWords h = new HTableWords(maxLF);
			String word1 = "abc";
			String word2 = "xyz";
			h.addWord(word1);
			h.addWord(word2);
			assertTrue(h.nbWords() == 2);
			Iterator<String> all = h.allWords();
			String w1 = all.next();
			assertTrue(h.wordExists(w1) && all.hasNext());
			String w2 = all.next();
			assertTrue(h.wordExists(w2) && !all.hasNext());
		} catch (WException e) {
			fail();
		} catch (NoSuchElementException e) {
			fail();
		}
	}

	// ...
}
