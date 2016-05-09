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
	
	private ParseTreeProperty<Type> types;
	
	public TGrammarListener() {
		reset();
	}
	
	public void reset() {
		types = new ParseTreeProperty<>();
	}
	
	public Type parse(String input) {
		reset();
		Lexer lexer = new TLexer(new ANTLRInputStream(input));
		TParser parser = new TParser(new CommonTokenStream(lexer));
		ParseTree tree = parser.t();
		
		new ParseTreeWalker().walk(this, tree);
		return types.get(tree);
	}
	
	@Override
	public void exitHat(HatContext ctx) {
		Type left = types.get(ctx.t().get(0));
		Type right = types.get(ctx.t().get(1));
		types.put(ctx, right == Type.NUM ? (left != Type.BOOL ? left : Type.ERR) : Type.ERR);
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
		types.put(node, Type.valueOf(node.getText().toUpperCase()));
	}
	
	@Override
	public void visitErrorNode(ErrorNode node) {
		types.put(node, Type.ERR);
	}

}
