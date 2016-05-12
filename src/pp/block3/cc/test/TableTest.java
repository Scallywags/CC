package pp.block3.cc.test;

import java.io.IOException;
import java.io.PrintWriter;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import pp.block3.cc.tabular.MyTabListener;
import pp.block3.cc.tabular.TabLexer;
import pp.block3.cc.tabular.TabParser;
import pp.block3.cc.tabular.TableErrorListener;

public class TableTest {

	public static void main(String[] args) throws IOException {
		for (int i = 1; i <= 5; i++) {
			//System.out.println("parsing table " + i);
			convert("src/pp/block3/cc/tabular/tabular-" + i + ".tex");
		}
	}

	public static void convert(String filePath) throws IOException {
		TableErrorListener errorListener = new TableErrorListener();
		TabLexer lexer = new TabLexer(new ANTLRFileStream(filePath));
		lexer.removeErrorListeners();
		lexer.addErrorListener(errorListener);
		TabParser parser = new TabParser(new CommonTokenStream(lexer));
		parser.removeErrorListeners();
		parser.addErrorListener(errorListener);
		MyTabListener listener = new MyTabListener();
		ParseTree tree = parser.tables();
		
		if (errorListener.getErrors().isEmpty()) {
			new ParseTreeWalker().walk(listener, tree);
			String newFilePath = filePath.replace(".tex", ".html");
			//System.out.println(listener.getHTML());
			PrintWriter writer = new PrintWriter(newFilePath);
			writer.println(listener.getHTML());
			writer.close();
		}

		for (String error : errorListener.getErrors()) {
			System.err.println(error);
		}
	}

}
