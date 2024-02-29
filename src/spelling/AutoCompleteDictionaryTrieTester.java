/**
 * 
 */
package spelling;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * @author UC San Diego MOOC team
 *
 */
public class AutoCompleteDictionaryTrieTester {

	private String dictFile = "data/words.small.txt"; 

	AutoCompleteDictionaryTrie emptyDict; 
	AutoCompleteDictionaryTrie smallDict;
	AutoCompleteDictionaryTrie largeDict;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception 
	{
		emptyDict = new AutoCompleteDictionaryTrie();
		smallDict = new AutoCompleteDictionaryTrie();
		largeDict = new AutoCompleteDictionaryTrie();

		smallDict.addWord("Hello");
		smallDict.addWord("HElLo");
		smallDict.addWord("help");
		smallDict.addWord("he");
		smallDict.addWord("hem");
		smallDict.addWord("hot");
		smallDict.addWord("hey");
		smallDict.addWord("a");
		smallDict.addWord("subsequent");
		
		DictionaryLoader.loadDictionary(largeDict, dictFile);
	}

	
	/** Test if the size method is working correctly.
	 */
	@Test
	public void testSize()
	{
		assertEquals(0, emptyDict.size(),"Testing size for empty dict");
		assertEquals( 8, smallDict.size(),"Testing size for small dict");
		assertEquals(4438, largeDict.size(),"Testing size for large dict" );
	}
	
	/** Test the isWord method */
	@Test
	public void testIsWord()
	{
		assertEquals(false, emptyDict.isWord("Hello"),"Testing isWord on empty: Hello" );
		assertEquals(true, smallDict.isWord("Hello"),"Testing isWord on small: Hello");
		assertEquals(true, largeDict.isWord("Hello"),"Testing isWord on large: Hello");
		
		assertEquals(true, smallDict.isWord("hello"),"Testing isWord on small: hello" );
		assertEquals(true, largeDict.isWord("hello"),"Testing isWord on large: hello");

		assertEquals(false, smallDict.isWord("hellow"),"Testing isWord on small: hellow");
		assertEquals(false, largeDict.isWord("hellow"),"Testing isWord on large: hellow");
		
		assertEquals(false, emptyDict.isWord(""),"Testing isWord on empty: empty string");
		assertEquals(false, smallDict.isWord(""),"Testing isWord on small: empty string");
		assertEquals(false, largeDict.isWord(""),"Testing isWord on large: empty string");
		
		assertEquals(false, smallDict.isWord("no"),"Testing isWord on small: no");
		assertEquals(true, largeDict.isWord("no"),"Testing isWord on large: no");
		
		assertEquals(true, smallDict.isWord("subsequent"),"Testing isWord on small: subsequent");
		assertEquals(true, largeDict.isWord("subsequent"),"Testing isWord on large: subsequent");
		
		
	}
	
	/** Test the addWord method */
	@Test
	public void testAddWord()
	{
		
		
		assertEquals(false, emptyDict.isWord("hellow"),"Asserting hellow is not in empty dict");
		assertEquals(false, smallDict.isWord("hellow"),"Asserting hellow is not in small dict");
		assertEquals(false, largeDict.isWord("hellow"),"Asserting hellow is not in large dict");
		
		emptyDict.addWord("hellow");
		smallDict.addWord("hellow");
		largeDict.addWord("hellow");

		assertEquals(true, emptyDict.isWord("hellow"),"Asserting hellow is in empty dict");
		assertEquals(true, smallDict.isWord("hellow"),"Asserting hellow is in small dict");
		assertEquals(true, largeDict.isWord("hellow"),"Asserting hellow is in large dict");

		assertEquals(false, emptyDict.isWord("xyzabc"),"Asserting xyzabc is not in empty dict");
		assertEquals(false, smallDict.isWord("xyzabc"),"Asserting xyzabc is not in small dict");
		assertEquals(false, largeDict.isWord("xyzabc"),"Asserting xyzabc is in large dict");

		
		emptyDict.addWord("XYZAbC");
		smallDict.addWord("XYZAbC");
		largeDict.addWord("XYZAbC");

		assertEquals(true, emptyDict.isWord("xyzabc"),"Asserting xyzabc is in empty dict");
		assertEquals(true, smallDict.isWord("xyzabc"),"Asserting xyzabc is in small dict");
		assertEquals(true, largeDict.isWord("xyzabc"),"Asserting xyzabc is large dict");
		
		
		assertEquals(false, emptyDict.isWord(""),"Testing isWord on empty: empty string");
		assertEquals(false, smallDict.isWord(""),"Testing isWord on small: empty string");
		assertEquals(false, largeDict.isWord(""),"Testing isWord on large: empty string");
		
		assertEquals(false, smallDict.isWord("no"),"Testing isWord on small: no");
		assertEquals(true, largeDict.isWord("no"),"Testing isWord on large: no");
		
		assertEquals(true, smallDict.isWord("subsequent"),"Testing isWord on small: subsequent");
		assertEquals(true, largeDict.isWord("subsequent"),"Testing isWord on large: subsequent");
		
		
	}
	
	@Test
	public void testPredictCompletions()
	{
		List<String> completions;
		completions = smallDict.predictCompletions("", 0);
		assertEquals(0, completions.size());
		
		completions = smallDict.predictCompletions("",  4);
		assertEquals(4, completions.size());
		assertTrue(completions.contains("a"));
		assertTrue(completions.contains("he"));
		boolean twoOfThree = completions.contains("hey") && completions.contains("hot") ||
				             completions.contains("hey") && completions.contains("hem") ||
				             completions.contains("hot") && completions.contains("hem");
		assertTrue(twoOfThree);
		
		completions = smallDict.predictCompletions("he", 2);
		boolean allIn = completions.contains("he") && 
				(completions.contains("hem") || completions.contains("hey"));
		assertEquals(2, completions.size());
		assertTrue(allIn);
		
		completions = smallDict.predictCompletions("hel", 10);
		assertEquals(2, completions.size());
		allIn = completions.contains("hello") && completions.contains("help");
		assertTrue(allIn);
	
		completions = smallDict.predictCompletions("x", 5);
		assertEquals(0, completions.size());
	}
	
	
	
	
}
