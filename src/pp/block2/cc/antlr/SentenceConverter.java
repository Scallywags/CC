package pp.block2.cc.antlr;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import pp.block2.cc.AST;
import pp.block2.cc.NonTerm;
import pp.block2.cc.ParseException;
import pp.block2.cc.Parser;
import pp.block2.cc.SymbolFactory;
import pp.block2.cc.Term;
import pp.block2.cc.antlr.SentenceParser.ModifierContext;
import pp.block2.cc.antlr.SentenceParser.ObjectContext;
import pp.block2.cc.antlr.SentenceParser.SentenceContext;
import pp.block2.cc.antlr.SentenceParser.SubjectContext;
import pp.block2.cc.ll.Sentence;

public class SentenceConverter //
		extends SentenceBaseListener implements Parser {
	/** Factory needed to create terminals of the {@link Sentence}
	 * grammar. See {@link pp.block2.cc.ll.SentenceParser} for
	 * example usage. */
	private final SymbolFactory fact;
	
	private final NonTerm sentence = new NonTerm("Sentence");
	private final NonTerm modifier = new NonTerm("Modifier");
	private final NonTerm subject = new NonTerm("Subject");
	private final NonTerm object = new NonTerm("Object");

	private ParseTreeProperty<AST> propertyMap = new ParseTreeProperty<AST>();
	
	private AST ast;
	private boolean shouldThrowParseException;
	
	public SentenceConverter() {
		this.fact = new SymbolFactory(Sentence.class);
	}

	@Override
	public AST parse(Lexer lexer) throws ParseException {
		// Fill in
		SentenceParser parser = new SentenceParser(new CommonTokenStream(lexer));
		ParseTree tree = parser.sentence();
		new ParseTreeWalker().walk(this, tree);
		
		if (shouldThrowParseException) {
			throw new ParseException();
		}
		
		return ast;
	}
	
	// From here on overwrite the listener methods
	// Use an appropriate ParseTreeProperty to
	// store the correspondence from nodes to ASTs
	
	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		System.out.println("Enter every rule " + ctx.getText() + " \n" +
				"AST = " + ast);
	}
	
	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		System.out.println("Exit every rule \n" +
				"AST = " + ast);
	}
	
	@Override
	public void enterSentence(SentenceContext ctx) {
		ast = new AST(sentence);
		propertyMap.put(ctx, ast);
	}
	
	@Override
	public void enterSubject(SubjectContext ctx) {
		System.out.println("Entering subject " + ctx.getText());
		AST sub = new AST(subject);
		propertyMap.put(ctx, sub);
		ast.addChild(sub);
		ast = sub;
	}
	
	@Override
	public void exitSubject(SubjectContext ctx) {
		ast = this.propertyMap.get(ctx.getParent());
	}
	
	@Override
	public void enterObject(ObjectContext ctx) {
		AST obj = new AST(object);
		propertyMap.put(ctx, obj);
		ast.addChild(obj);
		ast = obj;
	}
	
	@Override
	public void exitObject(ObjectContext ctx) {
		ast = this.propertyMap.get(ctx.getParent());
	}
	
	@Override
	public void enterModifier(ModifierContext ctx) {
		AST mod = new AST(modifier);
		propertyMap.put(ctx, mod);
		ast.addChild(mod);
		ast = mod;
	}
	
	@Override
	public void exitModifier(ModifierContext ctx) {
		ast = this.propertyMap.get(ctx.getParent());
	}
	
	@Override
	public void visitTerminal(TerminalNode node) {
		Token token = node.getSymbol();
		Term term = fact.getTerminal(token.getType());
		AST termNode = new AST(term, token);
		propertyMap.put(node, termNode);
		ast.addChild(termNode);
	}
	
	@Override
	public void visitErrorNode(ErrorNode node) {
		shouldThrowParseException = true;		
	}
	
}
