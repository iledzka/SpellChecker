package SpellChecker;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModificationsProvidedTest {

	@Test
	public void testOmission0() {

		IWords dict = new LListWords();
		try {
			dict.addWord("cats");
			dict.addWord("like");
			dict.addWord("on");
			dict.addWord("of");
			dict.addWord("to");
			dict.addWord("play");
		} catch (WException e) {
			fail("Error with linked list implementation");
		}
		IWords sugg = SpellChecker.suggestions("catts", dict);
		assertTrue(sugg.wordExists("cats"));
	}

	@Test
	public void testOmission1() {

		IWords dict = new LListWords();
		try {
			dict.addWord("cats");
			dict.addWord("like");
			dict.addWord("on");
			dict.addWord("of");
			dict.addWord("to");
			dict.addWord("play");
		} catch (WException e) {
			fail("Error with linked list implementation");
		}
		
		IWords sugg = SpellChecker.suggestions("likke", dict);
		assertTrue(sugg.wordExists("like"));
		sugg = SpellChecker.suggestions("plaey", dict);
		assertTrue(sugg.wordExists("play"));
	}
	
	@Test
	public void testOmission2() {

		IWords dict = new LListWords();
		try {
			dict.addWord("cats");
			dict.addWord("like");
			dict.addWord("on");
			dict.addWord("of");
			dict.addWord("to");
			dict.addWord("play");
		} catch (WException e) {
			fail("Error with linked list implementation");
		}
		
		IWords sugg = SpellChecker.suggestions("oplay", dict);
		assertTrue(sugg.wordExists("play"));
	}
}
