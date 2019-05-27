package SpellChecker;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/** Main class for the Spell-Checker program */
public class SpellChecker {

	/** Suggests word modifications for a given word and a given word dictionary. */
	static public IWords suggestions(String word, IWords dict) {

		//contains all letters in English alphabet
		LinkedList<Character> alphabet = createAlphabet();
		//suggestions data structure depends on the type of dictionary provided
		//can be LListWords or HTableWords
		IWords suggestions = getSuggestionsStructure(dict);
		
		try {
			for (Character c : alphabet) {
				
				LinkedList<String> substitutionList = letterSubstitution(c, word);
				for (String s: substitutionList) {
					if (!suggestions.wordExists(s) && dict.wordExists(s)) {
						suggestions.addWord(s);
					}	
				}
				
				LinkedList<String> insertedList = letterInsertion(c, word);
				for (String s: insertedList) {
					if (!suggestions.wordExists(s) && dict.wordExists(s)) {
						
						suggestions.addWord(s);
					}	
				}
			}
			
			for (int i = 0; i < word.length(); i++) {
				String reversedWord = letterReversal(i, word);
				if (reversedWord != null && !suggestions.wordExists(reversedWord) && dict.wordExists(reversedWord) && !reversedWord.equals(word)) {
					suggestions.addWord(reversedWord);
				}
			}
			
			LinkedList<String> omittedList = letterOmission(word);
			for (String s : omittedList) {
				if (!suggestions.wordExists(s) && dict.wordExists(s)) {
					suggestions.addWord(s);
				}
			}
		} catch (WException e) {
			System.out.print(e.getMessage());
			System.err.println("Couldn't find suggestions.");
		}
		
		
		return suggestions;
	}
	
	private static IWords getSuggestionsStructure(IWords dict) {
		IWords suggestions = null;
		//check if the dictionary is instance of a HTableWords or LListWords
		if (dict instanceof LListWords) {
			suggestions = new LListWords();
		} else if (dict instanceof HTableWords) {
			suggestions = new HTableWords();
		}
		return suggestions;
	}
	
	/**
	 * Swaps two adjacent characters in word w (of index i and i + 1). 
	 * Returns null if index i provided equals the length of word w (not possible to swap).
	 * 
	 * @param i
	 * @param w
	 * @return
	 */
	static private String letterReversal(int i, String w) {
		
		StringBuffer word = new StringBuffer(w);
		if (word.length() < i+2) {
			return null;
		}
		String substring = w.substring(i, i+2);
		StringBuffer sub = new StringBuffer(substring);
		sub.reverse();
		return word.replace(i, i+2, sub.toString()).toString();
	}
	
	/**
	 * Inserts given character into string w.
	 * 
	 * @param c
	 * @param w
	 * @return
	 */
	static private LinkedList<String> letterInsertion(Character c, String w) {
		LinkedList<String> allPossibleStrings = new LinkedList<String>();
		StringBuffer word = new StringBuffer(w);
		
		for (int i = 0; i <= word.length(); i++) {
			word.insert(i, c.toString());
			if (!allPossibleStrings.contains(word.toString())) {
				allPossibleStrings.add(word.toString());
			}
			word.delete(i, i+1);
		}
		
		return allPossibleStrings;
	}
	
	/**
	 * Replaces each character of word w by character c. Returns all strings created.
	 * 
	 * @param c
	 * @param w
	 * @return
	 */
	static private LinkedList<String> letterSubstitution(Character c, String w) {
		LinkedList<String> allPossibleStrings = new LinkedList<String>();
		StringBuffer word = new StringBuffer(w);
	
		for (int i = 0; i < word.length(); i++) {
			if (c.equals(word.charAt(i))) {
				continue;
			}
			Character temp = word.charAt(i);
			word.replace(i, i+1, c.toString());
			if (!allPossibleStrings.contains(word.toString())) {
				allPossibleStrings.add(word.toString());
			}
			word.replace(i, i+1, temp.toString());
		}
	
		return allPossibleStrings;
	}
	
	/**
	 * Iterated over string w and removes each consecutive letter. 
	 * Returns list containing all nnewly created words.
	 * 
	 * @param w
	 * @return
	 */
	static private LinkedList<String> letterOmission(String w) {
		LinkedList<String> allPossibleStrings = new LinkedList<String>();
		StringBuffer word = new StringBuffer(w);
	
		for (int i = 0; i < word.length(); i++) {
			Character temp = word.charAt(i);
			word.delete(i, i+1);
			if (!allPossibleStrings.contains(word.toString())) {
				allPossibleStrings.add(word.toString());
			}
			word.insert(i, temp);
			word.replace(i, i+1, temp.toString());
		}
	
		return allPossibleStrings;
	}
	
	/**
	 * Helper function to create alphabet with all letters (26 elements). 
	 */
	static private LinkedList<Character> createAlphabet() {
		LinkedList<Character> alphabet = new LinkedList<Character>();
		Character[] alpha = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		alphabet.addAll(Arrays.asList(alpha));
		return alphabet;
	}

	/**
	 * Main method for the Spell-Checker program. The program takes two input
	 * filenames in the command line: the word dictionary file and the file
	 * containing the words to spell-check. .
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: SpellChecker dictionaryFile.txt inputFile.txt ");
			System.exit(1);
		}
		
		//Timers used to measure time of execution of linked list and hash table implementations.
		Timer llWordsTimer = new Timer();
		Timer hTableWordsTimer = new Timer();
		
		System.out.println("Dictionary used: " + args[0]);
		System.out.println("Text to check used: " + args[1]);
		System.out.println();
		
		System.out.println("LINKED LIST IMPLEMENTATION: Reading dictionary file into a linked list...");
		LListWords llistWordsSetW = new LListWords();
		IWords returnedSuggestions = new LListWords();
		LListWords allSuggestions = new LListWords();
		
		try {
			BufferedInputStream dict, file;

			dict = new BufferedInputStream(new FileInputStream(args[0]));

			FileWordRead readWords = new FileWordRead(dict);
			
			//Start measuring time of inserting words into the dictionary
			llWordsTimer.start();
			
			while ( readWords.hasNextWord() ) {
				String nextWord = readWords.nextWord();
				try {
					llistWordsSetW.addWord(nextWord);
				} catch (WException e) {
					System.err.print(e.getMessage());
					System.err.println("WException thrown.");
				}
			}
			
			//Stop measuring time
			llWordsTimer.stop();
			
			dict.close();

			file = new BufferedInputStream(new FileInputStream(args[1]));
			
			FileWordRead readTextToCheck = new FileWordRead(file);
			while ( readTextToCheck.hasNextWord() ) {
				String nextWord = readTextToCheck.nextWord();
				if (!llistWordsSetW.wordExists(nextWord)) {
					returnedSuggestions = SpellChecker.suggestions(nextWord, llistWordsSetW);
					Iterator<String> iterator = returnedSuggestions.allWords();
					while (iterator.hasNext()) {
						try {
							allSuggestions.addWord(iterator.next());
						} catch (WException e) {
							System.err.print(e.getMessage());
							System.err.println("WException thrown.");
						}
					}
				}
			}
			
			file.close();

		} catch (IOException e) { // catch exceptions caused by file input/output errors
			System.out.print(e.getMessage());
			System.err.println("Missing input file, check your filenames");
			System.exit(1);
		}
		
		
		Iterator<String> iterator = allSuggestions.allWords();
		System.out.print("\nLINKED LIST IMPLEMENTATION:  Now printing all possible suggestions as a result of the spellchecking...\n");
		while (iterator.hasNext()) {
			String next = iterator.next();
			System.out.print("\n" + next + "\n");
		}
		System.out.println();
		
		System.out.println("HASH TABLE IMPLEMENTATION: Reading dictionary file into a hash table...");
		HTableWords hashTableSetW = new HTableWords(0.4f);
		IWords suggestionsHTable = new HTableWords(0.4f);
		
		try {
			BufferedInputStream dict, file;

			dict = new BufferedInputStream(new FileInputStream(args[0]));

			FileWordRead readWords = new FileWordRead(dict);
			
			//Start measuring time of inserting words into the dictionary
			hTableWordsTimer.start();
			
			while ( readWords.hasNextWord() ) {
				String nextWord = readWords.nextWord();
				try {
					hashTableSetW.addWord(nextWord);
				} catch (WException e) {
					System.err.print(e.getMessage());
					System.err.println("WException thrown.");
				}
			}
			//Stop measuring time
			hTableWordsTimer.stop();
			dict.close();
			
			file = new BufferedInputStream(new FileInputStream(args[1]));
			
			FileWordRead readTextToCheck = new FileWordRead(file);
			while ( readTextToCheck.hasNextWord() ) {
				String nextWord = readTextToCheck.nextWord();
				if (!hashTableSetW.wordExists(nextWord)) {
					IWords rtrndSuggestions = SpellChecker.suggestions(nextWord, hashTableSetW);
					Iterator<String> it = rtrndSuggestions.allWords();
					while (it.hasNext()) {
						try {
							suggestionsHTable.addWord(it.next());
						} catch (WException e) {
							System.err.print(e.getMessage());
							System.err.println("WException thrown.");
						}
						
					}
				}
			}

			
			
			file.close();

		} catch (IOException e) { // catch exceptions caused by file input/output errors
			System.out.print(e.getMessage());
			System.err.println("Missing input file, check your filenames");
			System.exit(1);
		}
		
		
		Iterator<String> hTableIterator = suggestionsHTable.allWords();
		System.out.print("\nHASH TABLE IMPLEMENTATION:  Now printing all possible suggestions in a hash table, as a result of the spellchecking...\n");
		while (hTableIterator.hasNext()) {
			String next = hTableIterator.next();
			System.out.print(next + "\n");
		}
		System.out.println();
		System.out.println("Average probes in hash table dictionary: " + hashTableSetW.averageProbes());
		System.out.println("Current load factor in hash table dictionary: " + hashTableSetW.loadFactor());
		System.out.println("Max load factor in hash table dictionary: " + hashTableSetW.maxLoadFactor());
		System.out.println();
		System.out.println();
		System.out.println("Hash Table vs. Linked List Implementation Comparison:");
		System.out.println("Linked List implementation recorded " + llistWordsSetW.nbWords() + " words.");
		System.out.println("Hash Table implementation recorded " + hashTableSetW.nbWords() + " words.");
		System.out.println();
		System.out.println("Running time of the Linked List implementation in milliseconds: " + llWordsTimer.getMeasuredTime());
		System.out.println("Running time of the HashTable implementation in milliseconds: " + hTableWordsTimer.getMeasuredTime());
		System.out.println("\nProgram will exit.");
		System.out.println();
	}

}
