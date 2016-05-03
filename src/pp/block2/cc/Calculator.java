package pp.block2.cc;

import java.math.BigInteger;
import java.util.stream.Stream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import pp.block2.cc.antlr.ExprBaseListener;
import pp.block2.cc.antlr.ExprLexer;
import pp.block2.cc.antlr.ExprParser;

public class Calculator extends ExprBaseListener {

	ParseTreeProperty<BigInteger> results = new ParseTreeProperty<>();
	ParseTreeProperty<Operator> operators = new ParseTreeProperty<>();

	boolean error = false;
	
	public Calculator () {

	}

	public BigInteger calculate(String input) throws ParseException {
		Lexer lexer = new ExprLexer(new ANTLRInputStream(input));
		ExprParser parser = new ExprParser(new CommonTokenStream(lexer));
		ParseTree tree = parser.expr();
		
		System.out.println(tree.toStringTree(parser));
		
		new ParseTreeWalker().walk(this, tree);
		return results.get(tree);
	}	

	@Override
	public void visitTerminal(TerminalNode node) {
		if (node.getSymbol().getType() == ExprLexer.NUMBER) {
			results.put(node, new BigInteger(node.getText()));
		}
	}

	@Override
	public void exitCompExpr(ExprParser.CompExprContext ctx) {
		BigInteger left = results.get(ctx.getChild(0));
		BigInteger right = results.get(ctx.getChild(2));
		Operator op = operators.get(ctx.getChild(1));
		BigInteger result = null;
		switch (op) {
		case PLUS:
		case MINMIN:
			result = left.add(right);
			break;
		case MIN:
			result = left.min(right);
			break;
		case MULT:
			result = left.multiply(right);
			break;
		case DIV:
			result = left.divide(right);
			break;
		case POW:
			result = left.pow(right.intValue());
			break;
		default:
			error = true;
		}
		results.put(ctx, result);
	}
	
	@Override
	public void exitBrackExpr(ExprParser.BrackExprContext ctx) {
		results.put(ctx, results.get(ctx.getChild(1)));
	}
	
	@Override
	public void exitNumExpr(ExprParser.NumExprContext ctx) {
		results.put(ctx, results.get(ctx.getChild(0)));
	}
	
	@Override
	public void enterPlusOp(ExprParser.PlusOpContext ctx) {
		operators.put(ctx, Operator.PLUS);
	}
	public void enterMinOp(ExprParser.MinOpContext ctx) {
		operators.put(ctx, Operator.MIN);
	}
	public void enterMultOp(ExprParser.MultOpContext ctx) {
		operators.put(ctx, Operator.MULT);
	}
	public void enterDivOp(ExprParser.DivOpContext ctx) {
		operators.put(ctx, Operator.DIV);
	}
	public void enterPowOp(ExprParser.PowOpContext ctx) {
		operators.put(ctx, Operator.POW);
	}
	
	

	enum Operator {
		PLUS("+"), MIN("-"), MINMIN("--"), MULT("*"), DIV("/"), POW("^");
		
		private String op;
		private Operator(String op) {
			this.op = op;
		}
		
		public static Operator getByOp(String op) {
			return Stream.of(values()).filter(oper -> oper.op.equals(op)).findAny().get();
		}
		
		public String toString() {
			return name();
		}
	}
}
