package pp.block5.cc.antlr;

import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import pp.block5.cc.ErrorListener;
import pp.block5.cc.ParseException;
import pp.block5.cc.antlr.NumWordParser.NumberContext;
import pp.block5.cc.antlr.NumWordParser.SentenceContext;
import pp.block5.cc.antlr.NumWordParser.WordContext;

/** Prettyprints a (number, word)-sentence and sums up the numbers. */
public class NumWordProcessor extends NumWordBaseVisitor<Integer> {
	
	private StringBuilder builder;
	
	public static void main(String[] args) {
		NumWordProcessor grouper = new NumWordProcessor();
		if (args.length == 0) {
			process(grouper, "1sock2shoes 3 holes");
			process(grouper, "3 strands 10 blocks 11 weeks 15 credits");
			process(grouper, "1 2 3");
		} else {
			for (String text : args) {
				process(grouper, text);
			}
		}
	}

	private static void process(NumWordProcessor grouper, String text) {
		try {
			System.out.printf("Processing '%s':%n", text);
			int result = grouper.group(text);
			System.out.println("Total: " + result);
		} catch (ParseException exc) {
			exc.print();
		}
	}

	/** Groups a given sentence and prints it to stdout.
	 * Returns the sum of the numbers in the sentence.
	 */
	public int group(String text) throws ParseException {
		builder = new StringBuilder();
		CharStream chars = new ANTLRInputStream(text);
		ErrorListener listener = new ErrorListener();
		Lexer lexer = new NumWordLexer(chars);
		lexer.removeErrorListeners();
		lexer.addErrorListener(listener);
		TokenStream tokens = new CommonTokenStream(lexer);
		NumWordParser parser = new NumWordParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(listener);
		ParseTree tree = parser.sentence();
		listener.throwException();
		return visit(tree);
	}

	// Override the visitor methods.
	// Each visitor method should call visit(child)
	// if and when it wants to visit that child node.
	
	@Override
	public Integer visitSentence(SentenceContext ctx) {
		int total = 0;
		
		List<ParseTree> children = ctx.children;
		int size = children.size();
		if (size == 3) {
			//just the single numer and word
			total += Integer.parseInt(children.get(0).getText());
			builder.append(total).append(" ");
			builder.append(children.get(1).getText());
		} else {
			//iterate over almost all of them.
			for (int i = 0; i < size - 3; i++) {
				total += visit(children.get(i));
			}
			String lastNum = children.get(size - 3).getText();
			String lastWord = children.get(size - 2).getText();
			total += Integer.parseInt(lastNum);
			builder.append("and ").append(lastNum);
			builder.append(lastWord);
		}
		
		System.out.println(builder.toString());
		return total;
	}
	
	@Override
	public Integer visitNumber(NumberContext ctx) {
		builder.append(ctx.getText()).append(" ");
		return Integer.parseInt(ctx.getText());
	}
	
	@Override
	public Integer visitWord(WordContext ctx) {
		builder.append(ctx.getText()).append(", ");
		return 0;
	}
	
}
