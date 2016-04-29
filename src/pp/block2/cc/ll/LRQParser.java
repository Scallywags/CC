package pp.block2.cc.ll;

import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

import pp.block2.cc.AST;
import pp.block2.cc.NonTerm;
import pp.block2.cc.ParseException;
import pp.block2.cc.Parser;
import pp.block2.cc.SymbolFactory;

public class LRQParser implements Parser {

	private static final NonTerm L = new NonTerm("L");
	private static final NonTerm R = new NonTerm("R");
	private static final NonTerm Q = new NonTerm("Q");
	private static final NonTerm P = new NonTerm("P");
	private static final NonTerm S = new NonTerm("S");

	private SymbolFactory fact;
	private List<? extends Token> tokens;
	private int index;

	public LRQParser() {
		fact = new SymbolFactory(LRQ.class);
	}

	@Override
	public AST parse(Lexer lexer) throws ParseException {
		this.tokens = lexer.getAllTokens();
		this.index = 0;
		return parseL();
	}

	private AST parseL() {
		AST ast = new AST(L);
		switch (next().getType()) {
		case LRQ.R:
			ast.addChild(parseR()); //TODO
			ast.addChild(parseToken(LRQ.A));
			break;
		case LRQ.Q:
			ast.addChild(parseQ()); //TODO
			ast.addChild(parseToken(LRQ.B));
		}
		return ast;
	}

	/** Returns the next token, without moving the token index. */
	private Token peek() throws ParseException {
		if (this.index >= this.tokens.size()) {
			throw new ParseException("Reading beyond end of input");
		}
		return this.tokens.get(this.index);
	}

	/** Returns the next token and moves up the token index. */
	private Token next() throws ParseException {
		Token result = peek();
		this.index++;
		return result;
	}

	private AST parseToken(int tokenType) throws ParseException {
		Token next = next();
		if (next.getType() != tokenType) {
			throw new ParseException(String.format(
					"Line %d:%d - expected token '%s' but found '%s'",
					next.getLine(), next.getCharPositionInLine(),
					this.fact.get(tokenType), this.fact.get(next.getType())));
		}
		return new AST(this.fact.getTerminal(tokenType), next);
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: [text]+");
		} else {
			for (String text : args) {
				CharStream stream = new ANTLRInputStream(text);
				Lexer lexer = new Sentence(stream);
				try {
					System.out.printf("Parse tree: %n%s%n",
							new LRQParser().parse(lexer));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
