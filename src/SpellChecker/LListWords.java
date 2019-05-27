package SpellChecker;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Linked list implementation the of dictionary.
 * @author iza
 *
 */
public class LListWords implements IWords{

	private List<String> list;
	
	public LListWords() {
		this.list = new LinkedList<String>();
	}
	
	@Override
	public void addWord(String word) throws WException {
		
		if (this.wordExists(word)) {
			throw new WException("Couldn't add duplicate element: " + word + "\n");
		} else {
			this.list.add(word);
		}
		
	}

	@Override
	public void delWord(String word) throws WException {
		if (!this.wordExists(word)) {
			throw new WException("Couldn't remove non-existent element: " + word + "\n");
		} else {
			list.remove(word);
		}
		
	}

	@Override
	public boolean wordExists(String word) {
		return this.list.contains(word);
	}

	@Override
	public int nbWords() {
		return this.list.size();
	}

	/**
	 * Returns Iterator specified in Collection interface.
	 */
	@Override
	public Iterator<String> allWords() {
		return this.list.iterator();
	}

}
