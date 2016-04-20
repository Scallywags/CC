package pp.block1.cc.test;

import org.junit.Test;

import pp.block1.cc.antlr.ID6;

public class ID6Test {

	private static LexerTester tester = new LexerTester(ID6.class);
	
	@Test
	public void startWithLetter() {
		tester.yields("a48674", ID6.ID6);
		tester.yields("b12345 c6789a", ID6.ID6, ID6.ID6);
		tester.wrong("4568a4");
	}
	
	@Test
	public void testLength() {
		tester.correct("abcdef");
		tester.wrong("abcd");
	}
	
}
