package pp.block1.cc.test;

import org.junit.Test;

import pp.block1.cc.antlr.PLI;

public class PLITest {

	private static LexerTester tester = new LexerTester(PLI.class);
	
	@Test
	public void test() {
		tester.correct("\"alsdjf;klasd\"");
		tester.wrong("\"");
		tester.wrong("a");
		tester.wrong("\"\"abab\"");
		tester.yields("\"\"\" lalala \"\" lalala \"", PLI.STRING);
	}
	
}
