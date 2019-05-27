package SpellChecker;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.Test;

public class HTableWordsProvidedTest {

	/** Test 1: add an element and number of entries is 1 */
	@Test
	public void test1() {
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

	/** Test 2: add and find an element */
	@Test
	public void test2() {
		try {
			float maxLF = (float) 0.5;
			HTableWords h = new HTableWords(maxLF);
			String word = "abc";
			h.addWord(word);
			Iterator<String> all = h.allWords();
			String first = all.next();
			assertTrue(first.equals(word) && !all.hasNext());
		} catch (WException e) {
			fail();
		}
	}

	/** Test 3: look for an inexistent element */
	@Test
	public void test3() {
		float maxLF = (float) 0.5;
		HTableWords h = new HTableWords(maxLF);
		String word1 = "abc";
		String word2 = "xyz";
		try {
			h.addWord(word1);
		} catch (WException e) {
			fail();
		}
		assertFalse(h.wordExists(word2));
	}

	/** Test 4: try to delete a nonexistent element. Should throw an exception. */
	@Test
	public void test4() {
		float maxLF = (float) 0.5;
		HTableWords h = new HTableWords(maxLF);
		String word1 = "abc";
		String word2 = "xyz";
		try {
			h.addWord(word1);
		} catch (WException e1) {
			fail();
		}
		try {
			h.delWord(word2);
			fail();
		} catch (WException e) {
			assertTrue(true);
		}
	}

	/** Test 5: delete an actual element. Should not throw an exception. */
	@Test
	public void test5() {
		try {
			float maxLF = (float) 0.5;
			HTableWords h = new HTableWords(maxLF);
			String word1 = "abc";
			String word2 = "xyz";
			h.addWord(word1);
			System.out.println("Word exists: " + h.wordExists(word1));
			h.addWord(word2);
			h.delWord(word1);
			System.out.println("Word exists: " + h.wordExists(word1));
			assertTrue(h.nbWords() == 1);
			Iterator<String> all = h.allWords();
			String w = all.next();
			assertTrue(w.equals(word2) && !all.hasNext());
		} catch (WException e) {
			fail();
		} catch (NoSuchElementException e) {
			fail();
		}
	}


	/** Test 6: insert a number of different elements into the set and check that all these values are in the set */
	@Test
	public void test6() {
		float maxLF = (float) 0.5;
		HTableWords h = new HTableWords(maxLF);
		String word;
		for (int k = 0; k < 200; k++) {
			word = "w" + k;
			try {
				h.addWord(word);
			} catch (WException e) {
				fail();
			}
		}
		assertEquals(h.nbWords(), 200);
	}

	/** Test 7: Insert and delete a number of entries from the set */
	@Test
	public void test7() {
		try	{
			float maxLF = (float) 0.5;
			HTableWords h = new HTableWords(maxLF);
			String word;
			for (int k = 0; k < 200; ++k) {
				word = "w" + k;
				h.addWord(word);
			}
			assertEquals(h.nbWords(), 200);
			for ( int k = 0; k < 200; ++k )
			{
				word = "w" + k;
				h.delWord(word);
			}
			assertEquals(h.nbWords(), 0);
		} catch (WException e) {
			fail();
		}
	}

	/** Test 8: Get iterator over all elements. */
	@Test
	public void test8() {
		float maxLF = (float) 0.5;
		HTableWords h = new HTableWords(maxLF);
		LinkedList<String> l = new LinkedList<String>();
		String word;
		try {
			for (int k = 0; k < 100; k++) {
				word = "w" + k;
				h.addWord(word);
				l.add(word);
			}
			for (int k = 10; k < 30; k++) {
				word = "w" + k;
				h.delWord(word);
				l.remove(word);
			}
		} catch(WException e) {
			fail();
		}
		Iterator<String> all = h.allWords();
		while (all.hasNext()) {
			assertTrue(l.remove(all.next()));
		}
		assertEquals(l.size(), 0);
	}

}
