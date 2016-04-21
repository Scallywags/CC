package pp.block1.cc.test;

import org.junit.Test;

import pp.block1.cc.antlr.La;

public class LaTest {
	
	private static LexerTester tester = new LexerTester(La.class);
	
	@Test
	public void test() {
		tester.correct("La");
		tester.correct("Laaa   Laaaaa");
		tester.correct("Laaa LaaLa Li");
		tester.yields("LaLaLaLiLaLa", La.LALALALI, La.LALA);
		tester.correct("La La La La La Li");
		tester.wrong("La Laaaa Li");
	}

}
