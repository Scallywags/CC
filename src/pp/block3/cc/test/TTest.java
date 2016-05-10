package pp.block3.cc.test;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Assert;
import org.junit.Test;

import pp.block3.cc.antlr.TAttrParser;
import pp.block3.cc.antlr.TGrammarListener;
import pp.block3.cc.antlr.TLexer;
import pp.block3.cc.antlr.Type;

public class TTest {
	
	TGrammarListener listener;
	TAttrParser parser;
	
	@Test
	public void testAll() {
		test(Type.NUM, "3^2");
		test(Type.STR, "\"aString\" ^ 3");
		test(Type.NUM, "2+3");
		test(Type.BOOL, "true + false");
		test(Type.STR, "\"YOLO\" + \"SWAG\"");
		test(Type.BOOL, "3=4");
		test(Type.BOOL, "true=true");
		test(Type.BOOL, "\"YOLO\" = \"SWAG\"");
	}	
	
	public void test(Type expected, String input){
		parser = new TAttrParser(new CommonTokenStream(new TLexer(new ANTLRInputStream(input))));
		listener = new TGrammarListener();
//		System.out.println("parser = " + parser);
//		System.out.println("parser.t() = " + parser.t());
//		System.out.println("parser.t().type = " + parser.t().type);
		Assert.assertEquals(expected, parser.t().type);
		Assert.assertEquals(expected, listener.parse(input));
	
	}

}
