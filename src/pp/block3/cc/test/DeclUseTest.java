package pp.block3.cc.test;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.junit.Assert;
import org.junit.Test;

import pp.block3.cc.symbol.DeclUseLexer;
import pp.block3.cc.symbol.DeclUseParser;
import pp.block3.cc.symbol.DeclUseScopeListener;

public class DeclUseTest {
	
	List<String> errors;
	
	@Test
	public void testCorrect() throws IOException {
		errors = new ArrayList<>();
		test("DeclUseCorrect");
		Assert.assertTrue(errors.isEmpty());
	}
	
	@Test
	public void testErrors() throws IOException {
		errors = new ArrayList<>();
		test("DeclUseIncorrect");
		Assert.assertEquals("Error at line 1 at position 9. Wim was not declared.", errors.get(0));
		Assert.assertEquals("Error at line 1 at position 73. Mies was not declared.", errors.get(1));
	}
	
	private void test(String filepath) throws IOException {
		DeclUseScopeListener listener = new DeclUseScopeListener();
		DeclUseParser parser = new DeclUseParser(new CommonTokenStream(new DeclUseLexer(new ANTLRFileStream(filepath))));
		ParseTree tree = parser.program();
		new ParseTreeWalker().walk(listener, tree);
		errors.addAll(listener.getErrors());
	}

}
