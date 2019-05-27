package SpellChecker;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModificationsTest {

	@Test
	public void testReversal0() {
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
		IWords sugg = SpellChecker.suggestions("ctas", dict);
		assertTrue(sugg.wordExists("cats"));
	}
	
	@Test
	public void testReversal1() {
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
		IWords sugg = SpellChecker.suggestions("ilke", dict);
		assertTrue(sugg.wordExists("like"));
	}
	
	@Test
	public void testReversal2() {
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
		IWords sugg = SpellChecker.suggestions("plya", dict);
		assertTrue(sugg.wordExists("play"));
	}

	@Test
	public void testInsertion0() {
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
		IWords sugg = SpellChecker.suggestions("cts", dict);
		assertTrue(sugg.wordExists("cats"));
	}

	@Test
	public void testInsertion1() {
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
		IWords sugg = SpellChecker.suggestions("pla", dict);
		assertTrue(sugg.wordExists("play"));
	}
	@Test
	public void testInsertion2() {
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
		IWords sugg = SpellChecker.suggestions("lik", dict);
		assertTrue(sugg.wordExists("like"));
	}
	
	@Test
	public void testInsertion3() {
		IWords dict = new LListWords();
		try {
			dict.addWord("spinach");
			dict.addWord("like");
			dict.addWord("on");
			dict.addWord("of");
			dict.addWord("to");
			dict.addWord("play");
		} catch (WException e) {
			fail("Error with linked list implementation");
		}
		IWords sugg = SpellChecker.suggestions("spinac", dict);
		assertTrue(sugg.wordExists("spinach"));
	}
	
	@Test
	public void testSubstitution0() {
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
		IWords sugg = SpellChecker.suggestions("cets", dict);
		assertTrue(sugg.wordExists("cats"));
	}

	@Test
	public void testSubstitution1() {
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
		IWords sugg = SpellChecker.suggestions("plau", dict);
		assertTrue(sugg.wordExists("play"));
	}
	@Test
	public void testSubstitution2() {
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
		IWords sugg = SpellChecker.suggestions("kike", dict);
		assertTrue(sugg.wordExists("like"));
	}
	// ...

}
