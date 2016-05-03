package pp.block2.cc;

import java.math.BigInteger;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import pp.block2.cc.antlr.ExprBaseListener;
import pp.block2.cc.antlr.ExprLexer;
import pp.block2.cc.antlr.ExprParser;

public class Calculator extends ExprBaseListener {

	private ParseTreeProperty<BigInteger> results;

	private boolean error;
	
	public Calculator () {
		reset();
	}

	public void reset() {
		results = new ParseTreeProperty<>();
		error = false;
	}
	
	public BigInteger calculate(String input) throws ParseException {
		reset();
		Lexer lexer = new ExprLexer(new ANTLRInputStream(input));
		ExprParser parser = new ExprParser(new CommonTokenStream(lexer));
		ParseTree tree = parser.expr();
		
		System.out.println(tree.toStringTree(parser));
		
		new ParseTreeWalker().walk(this, tree);
		
		if (error) {
			throw new ParseException();
		}
		
		return results.get(tree);
	}	

	//------------ Number ------------
	
	@Override
	public void visitTerminal(TerminalNode node) {
		if (node.getSymbol().getType() == ExprLexer.NUMBER) {
			results.put(node, new BigInteger(node.getText()));
		}
	}

	//------------ Expr ------------
	
	@Override
	public void exitPlusExpr(ExprParser.PlusExprContext ctx) {
		BigInteger left = results.get(ctx.getChild(0));
		BigInteger right = results.get(ctx.getChild(2));	
		results.put(ctx, left.add(right));
	}
	
	@Override
	public void exitMinExpr(ExprParser.MinExprContext ctx) {
		BigInteger left = results.get(ctx.getChild(0));
		BigInteger right = results.get(ctx.getChild(2));	
		results.put(ctx, left.min(right));
	}
	
	@Override
	public void exitTermExpr(ExprParser.TermExprContext ctx) {
		results.put(ctx, results.get(ctx.getChild(0)));
	}
	
	//------------ Term ------------
	
	@Override
	public void exitMultTerm(ExprParser.MultTermContext ctx) {
		BigInteger left = results.get(ctx.getChild(0));
		BigInteger right = results.get(ctx.getChild(2));
		results.put(ctx, left.multiply(right));
	}

	@Override
	public void exitDivTerm(ExprParser.DivTermContext ctx) {
		BigInteger left = results.get(ctx.getChild(0));
		BigInteger right = results.get(ctx.getChild(2));
		results.put(ctx, left.divide(right));
	}
	
	@Override
	public void exitFacTerm(ExprParser.FacTermContext ctx) {
		results.put(ctx, results.get(ctx.getChild(0)));
	}
	
	//------------ Factor ------------
	
	@Override
	public void exitExprFac(ExprParser.ExprFacContext ctx) {
		results.put(ctx, results.get(ctx.getChild(1)));
	}
	
	@Override
	public void exitNegFac(ExprParser.NegFacContext ctx) {
		results.put(ctx, results.get(ctx.getChild(1)).negate());
	}
	
	@Override
	public void exitNumFac(ExprParser.NumFacContext ctx) {
		results.put(ctx, results.get(ctx.getChild(0)));
	}
	
	@Override
	public void exitPowFac(ExprParser.PowFacContext ctx) {
		BigInteger left = results.get(ctx.getChild(0));
		BigInteger right = results.get(ctx.getChild(2));
		results.put(ctx, left.pow(right.intValue()));
	}
	
	//------------ Error ------------
	
	@Override
	public void visitErrorNode(ErrorNode node) {
		error = true;
	}

}
