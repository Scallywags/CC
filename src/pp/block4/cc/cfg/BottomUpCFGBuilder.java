package pp.block4.cc.cfg;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import pp.block4.cc.ErrorListener;
import pp.block4.cc.cfg.FragmentParser.AssignStatContext;
import pp.block4.cc.cfg.FragmentParser.BlockStatContext;
import pp.block4.cc.cfg.FragmentParser.BreakStatContext;
import pp.block4.cc.cfg.FragmentParser.ContStatContext;
import pp.block4.cc.cfg.FragmentParser.DeclStatContext;
import pp.block4.cc.cfg.FragmentParser.ExprContext;
import pp.block4.cc.cfg.FragmentParser.IfStatContext;
import pp.block4.cc.cfg.FragmentParser.PrintStatContext;
import pp.block4.cc.cfg.FragmentParser.ProgramContext;
import pp.block4.cc.cfg.FragmentParser.StatContext;
import pp.block4.cc.cfg.FragmentParser.WhileStatContext;

/** Template bottom-up CFG builder. */
public class BottomUpCFGBuilder extends FragmentBaseListener {
	/** The CFG being built. */
	private Graph graph;
	
	/** The corresponding graph nodes for the parsetree nodes **/
	private ParseTreeProperty<Node> blocks = new ParseTreeProperty<>();

	/** Builds the CFG for a program contained in a given file. */
	public Graph build(File file) {
		Graph result = null;
		ErrorListener listener = new ErrorListener();
		try {
			CharStream chars = new ANTLRInputStream(new FileReader(file));
			Lexer lexer = new FragmentLexer(chars);
			lexer.removeErrorListeners();
			lexer.addErrorListener(listener);
			TokenStream tokens = new CommonTokenStream(lexer);
			FragmentParser parser = new FragmentParser(tokens);
			parser.removeErrorListeners();
			parser.addErrorListener(listener);
			ParseTree tree = parser.program();
			if (listener.hasErrors()) {
				System.out.printf("Parse errors in %s:%n", file.getPath());
				for (String error : listener.getErrors()) {
					System.err.println(error);
				}
			} else {
				result = build(tree);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/** Builds the CFG for a program given as an ANTLR parse tree. */
	public Graph build(ParseTree tree) {
		this.graph = new Graph();
		new ParseTreeWalker().walk(this, tree);
		return graph;
	}
	
	// ---------- add all the listener methods here ----------
	
	public void exitProgram(ProgramContext ctx) {
		List<StatContext> ctxs = ctx.stat();
		
		for (int i = 0; i < ctx.stat().size() - 1; i++) {
			StatContext statCtx = ctxs.get(i);
			
			if (statCtx instanceof BlockStatContext) {
				BlockStatContext blockStatCtx = (BlockStatContext) statCtx;
				List<StatContext> blockStatCtxs = blockStatCtx.stat(); 
				StatContext lastStat  = blockStatCtxs.get(blockStatCtxs.size() - 1);
				StatContext firstStat = blockStatCtxs.get(0);
				blocks.get(blockStatCtx).addEdge(blocks.get(firstStat));
				blocks.get(lastStat).addEdge(blocks.get(ctxs.get(i + 1)));
			} else {
				StatContext next = ctxs.get(i + 1);
				Node node1 = blocks.get(statCtx);
				Node node2 = blocks.get(next);
				node1.addEdge(node2);
			}
		}
	}
	
	@Override
	public void exitDeclStat(DeclStatContext ctx) {
		blocks.put(ctx, addNode(ctx, ctx.getText()));
	}
	
	@Override
	public void exitAssignStat(AssignStatContext ctx) {
		blocks.put(ctx, addNode(ctx, ctx.getText()));
	}
	
	@Override
	public void exitIfStat(IfStatContext ctx) {
		Node ifNode = addNode(ctx, "IF");
		//then
		List<StatContext> statCtxs = ctx.stat();
		Node thenNode = blocks.get(statCtxs.get(0));
		ifNode.addEdge(thenNode);
		thenNode.addEdge(ifNode);
		//else
		if (statCtxs.size() > 1) {
			Node elseNode = blocks.get(statCtxs.get(1));
			ifNode.addEdge(elseNode);
			elseNode.addEdge(ifNode);
		}
		blocks.put(ctx, ifNode);
	}
	
	@Override
	public void exitWhileStat(WhileStatContext ctx) {
		Node whileNode = addNode(ctx, "WHILE");
		
		StatContext statCtx = ctx.stat();
		Node bodyNode = blocks.get(statCtx);
		whileNode.addEdge(bodyNode);
		bodyNode.addEdge(whileNode);
		
		blocks.put(ctx, whileNode);
	}
	
	@Override
	public void exitBlockStat(BlockStatContext ctx) {
		Node blockNode = addNode(ctx, "BLOCK");
		
		List<StatContext> ctxs = ctx.stat();
		ListIterator<StatContext> iterator = ctxs.listIterator();
		StatContext childCtx = iterator.next();
		Node child = blocks.get(childCtx);
		blockNode.addEdge(child);
		while (iterator.hasNext()) {
			Node previous = child;
			childCtx = iterator.next();
			child = blocks.get(childCtx);
			previous.addEdge(child);
		}
		child.addEdge(blockNode);
		
		blocks.put(ctx, blockNode);
	}
	
	@Override
	public void exitPrintStat(PrintStatContext ctx) {
		blocks.put(ctx, addNode(ctx, ctx.getText()));
	}	

	@Override
	public void enterBreakStat(BreakStatContext ctx) {
		throw new IllegalArgumentException("Break not supported");
	}

	@Override
	public void enterContStat(ContStatContext ctx) {
		throw new IllegalArgumentException("Continue not supported");
	}
	
	// -------------------------------------------------------

	/** Adds a node to he CGF, based on a given parse tree node.
	 * Gives the CFG node a meaningful ID, consisting of line number and 
	 * a further indicator.
	 */
	private Node addNode(ParserRuleContext node, String text) {
		return this.graph.addNode(node.getStart().getLine() + ": " + text);
	}

	/** Main method to build and print the CFG of a simple Java program. */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: [filename]+");
			return;
		}
		BottomUpCFGBuilder builder = new BottomUpCFGBuilder();
		for (String filename : args) {
			File file = new File(filename);
			System.out.println(filename);
			System.out.println(builder.build(file));
		}
	}
}
