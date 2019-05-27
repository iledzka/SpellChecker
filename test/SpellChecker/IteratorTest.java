package SpellChecker;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class IteratorTest {

	@Test
	public void test0() {
		String[] array = {"a", "b", "c", "d"};
		String DEFUNCT = "";
		int returnedWords = 0;
		Iterator<String> it = new HTableWordsIterator(array, array.length, DEFUNCT);
		while (it.hasNext()) {
			it.next();
			returnedWords++;
		}
		assertTrue(returnedWords == array.length);
	}
	
	@Test
	public void test1() {
		String[] array = {null, null, null, null};
		String DEFUNCT = "";
		int returnedWords = 0;
		Iterator<String> it = new HTableWordsIterator(array, 0, DEFUNCT);
		while (it.hasNext()) {
			it.next();
			returnedWords++;
		}
		assertTrue(returnedWords == 0);
	}
	
	@Test
	public void test2() {
		String[] array = {null, null, "c", null, null, null, null};
		String DEFUNCT = "";
		int returnedWords = 0;
		Iterator<String> it = new HTableWordsIterator(array, 1, DEFUNCT);
		while (it.hasNext()) {
			it.next();
			returnedWords++;
		}
		assertTrue(returnedWords == 1);
	}
	
	@Test
	public void test3() {
		String DEFUNCT = "";
		String[] array = {null, DEFUNCT, "c", DEFUNCT, "e", null, "f"};
		int returnedWords = 0;
		Iterator<String> it = new HTableWordsIterator(array, 3, DEFUNCT);
		while (it.hasNext()) {
			it.next();
			returnedWords++;
		}
		assertTrue(returnedWords == 3);
	}
	
	@Test
	public void test4() {
		String DEFUNCT = "";
		String[] array = {null, DEFUNCT, "c", DEFUNCT, "e", null, "f"};
		int returnedWords = 0;
		Iterator<String> it = new HTableWordsIterator(array, 3, DEFUNCT);
		while (it.hasNext()) {
			it.next();
			returnedWords++;
		}
		assertFalse(returnedWords == 5);
	}
	
	@Test
	public void test5() {
		String DEFUNCT = "";
		String[] array = {null, "c", "e", null, "f", "j", "k"};
		int returnedWords = 0;
		Iterator<String> it = new HTableWordsIterator(array, 5, DEFUNCT);
		while (it.hasNext()) {
			it.next();
			returnedWords++;
		}
		assertTrue(returnedWords == 5);
	}
	
	@Test
	public void test6() {
		String DEFUNCT = "";
		String[] array = {null, DEFUNCT, null, "c", "e", null, "f", DEFUNCT, DEFUNCT, null, "j", "k", null, null, null, null};
		int returnedWords = 0;
		Iterator<String> it = new HTableWordsIterator(array, 5, DEFUNCT);
		while (it.hasNext()) {
			it.next();
			returnedWords++;
		}
		assertTrue(returnedWords == 5);
	}
	
}