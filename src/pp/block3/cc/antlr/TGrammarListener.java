package pp.block3.cc.antlr;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import pp.block3.cc.antlr.TParser.EqContext;
import pp.block3.cc.antlr.TParser.HatContext;
import pp.block3.cc.antlr.TParser.ParContext;
import pp.block3.cc.antlr.TParser.PlusContext;

public class TGrammarListener extends TBaseListener {
	
	private ParseTreeProperty<Type> types = new ParseTreeProperty<>();
	
	public Type parse(String input) {
		Lexer lexer = new TLexer(new ANTLRInputStream(input));
		TParser parser = new TParser(new CommonTokenStream(lexer));
		ParseTree tree = parser.t();
		
		new ParseTreeWalker().walk(this, tree);
		return types.get(tree);
	}
	
	@Override
	public void exitHat(HatContext ctx) {
		System.out.println("exitHat left child: " + ctx.t().get(0).getText());
		System.out.println("exitHat right child: " + ctx.t().get(1).getText());
		Type left = types.get(ctx.getChild(0));
		Type right = types.get(ctx.getChild(2));
		Type result = right == Type.NUM ? (left != Type.BOOL ? left : Type.ERR) : Type.ERR;
		System.out.println("exitHat left: " + left);
		System.out.println("exitHat right: " + right);
		System.out.println("exitHat result: " + result);
		types.put(ctx, result);
	}
	
	@Override
	public void exitPlus(PlusContext ctx) {
		Type left = types.get(ctx.t().get(0));
		Type right = types.get(ctx.t().get(1));
		types.put(ctx, left == right? left : Type.ERR);
	}
	
	@Override
	public void exitEq(EqContext ctx) {
		Type left = types.get(ctx.t().get(0));
		Type right = types.get(ctx.t().get(1));
		types.put(ctx, left == right ? Type.BOOL : Type.ERR);
	}
	
	@Override
	public void exitPar(ParContext ctx) {
		types.put(ctx, types.get(ctx.t()));
	}
	
	@Override
	public void visitTerminal(TerminalNode node) {
		int tokenType = node.getSymbol().getType();
		System.out.println("terminal token type = " + tokenType);
		Type type = null;
		switch(tokenType) {
		case TParser.NUM:
			type = Type.NUM;
			break;
		case TParser.BOOL:
			type = Type.BOOL;
			break;
		case TParser.STR:
			type = Type.STR;
			break;
		}
		System.out.println("terminal node = " + node + " type = " + type);
		types.put(node, type);
	}
	
	@Override
	public void visitErrorNode(ErrorNode node) {
		types.put(node, Type.ERR);
	}

}
