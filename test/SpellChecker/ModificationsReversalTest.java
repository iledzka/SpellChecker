package SpellChecker;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ModificationsReversalTest {

	@Test
	public void testReversal3() {
		IWords dict = new LListWords();
		try {
			dict.addWord("caturday");
			dict.addWord("like");
			dict.addWord("on");
			dict.addWord("of");
			dict.addWord("to");
			dict.addWord("play");
		} catch (WException e) {
			fail("Error with linked list implementation");
		}
		IWords sugg = SpellChecker.suggestions("caturdya", dict);
		assertTrue(sugg.wordExists("caturday"));
	}
	
	@Test
	public void testReversal4() {
		IWords dict = new LListWords();
		try {
			dict.addWord("cats");
			dict.addWord("likeness");
			dict.addWord("on");
			dict.addWord("of");
			dict.addWord("to");
			dict.addWord("play");
		} catch (WException e) {
			fail("Error with linked list implementation");
		}
		IWords sugg = SpellChecker.suggestions("likenses", dict);
		assertTrue(sugg.wordExists("likeness"));
	}
	
	@Test
	public void testReversal5() {
		IWords dict = new LListWords();
		try {
			dict.addWord("cats");
			dict.addWord("like");
			dict.addWord("on");
			dict.addWord("of");
			dict.addWord("to");
			dict.addWord("played");
		} catch (WException e) {
			fail("Error with linked list implementation");
		}
		IWords sugg = SpellChecker.suggestions("palyed", dict);
		assertTrue(sugg.wordExists("played"));
	}
	
	@Test
	public void testReversal6() {
		IWords dict = new LListWords();
		try {
			dict.addWord("cats");
			dict.addWord("like");
			dict.addWord("on");
			dict.addWord("of");
			dict.addWord("to");
			dict.addWord("played");
		} catch (WException e) {
			fail("Error with linked list implementation");
		}
		IWords sugg = SpellChecker.suggestions("plaeyd", dict);
		assertTrue(sugg.wordExists("played"));
	}
	
	@Test
	public void testReversal7() {
		IWords dict = new LListWords();
		try {
			dict.addWord("cats");
			dict.addWord("like");
			dict.addWord("on");
			dict.addWord("of");
			dict.addWord("to");
			dict.addWord("played");
		} catch (WException e) {
			fail("Error with linked list implementation");
		}
		IWords sugg = SpellChecker.suggestions("fo", dict);
		assertTrue(sugg.wordExists("of"));
	}
	
	@Test
	public void testReversal8() {
		IWords dict = new LListWords();
		try {
			dict.addWord("cats");
			dict.addWord("like");
			dict.addWord("on");
			dict.addWord("of");
			dict.addWord("to");
			dict.addWord("played");
		} catch (WException e) {
			fail("Error with linked list implementation");
		}
		IWords sugg = SpellChecker.suggestions("liek", dict);
		assertTrue(sugg.wordExists("like"));
	}
}
