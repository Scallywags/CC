package pp.block4.cc.cfg;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import pp.block4.cc.ErrorListener;
import pp.block4.cc.cfg.FragmentParser.AssignStatContext;
import pp.block4.cc.cfg.FragmentParser.BlockStatContext;
import pp.block4.cc.cfg.FragmentParser.BreakStatContext;
import pp.block4.cc.cfg.FragmentParser.ContStatContext;
import pp.block4.cc.cfg.FragmentParser.DeclStatContext;
import pp.block4.cc.cfg.FragmentParser.IfStatContext;
import pp.block4.cc.cfg.FragmentParser.PrintStatContext;
import pp.block4.cc.cfg.FragmentParser.ProgramContext;
import pp.block4.cc.cfg.FragmentParser.StatContext;
import pp.block4.cc.cfg.FragmentParser.WhileStatContext;

/** Template top-down CFG builder. */
public class TopDownCFGBuilder extends FragmentBaseListener {
	/** The CFG being built. */
	private Graph graph;
	
	private IdentityHashMap<ParseTree, Node> blocks = new IdentityHashMap<>();
	private IdentityHashMap<ParseTree, Node> nexts = new IdentityHashMap<>();

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
	
	@Override
	public void enterProgram(ProgramContext ctx) {		
		List<StatContext> stats = ctx.stat();
		for (int i = 0; i < stats.size(); i++) {
			StatContext stat = stats.get(i);
			Node node = addNode(stat, stat.getText());
			blocks.put(stat, node);
		}
		for (int i = 0; i < stats.size() - 1; i++) {
			Node next = blocks.get(stats.get(i+1));
			if (next != null)
				nexts.put(stats.get(i), next);
		}
	}
	
	@Override
	public void enterDeclStat(DeclStatContext ctx) {
		Node decl = blocks.get(ctx);
		Node next = nexts.get(ctx);	
		if (next != null)
			decl.addEdge(next);
	}
	
	@Override
	public void enterAssignStat(AssignStatContext ctx) {
		Node assign = blocks.get(ctx);
		Node next = nexts.get(ctx);
		if (next != null)
			assign.addEdge(next);
	}
	
	@Override
	public void enterIfStat(IfStatContext ctx) {
		Node ifNode = blocks.get(ctx);
		Node next = nexts.get(ctx);
		
		
		List<StatContext> stats = ctx.stat();
		for (StatContext stat : stats) {
			blocks.put(stat, addNode(stat, stat.getText()));
		}
		
		StatContext thenTree = stats.get(0);
		Node then = blocks.get(thenTree);
		ifNode.addEdge(then);
		nexts.put(thenTree, next);
		
		if (stats.size() > 1) {
			StatContext elseTree = stats.get(1);
			Node elsePart = blocks.get(elseTree);
			ifNode.addEdge(elsePart);
			nexts.put(elseTree, next);
		} else {
			if (next != null)
				ifNode.addEdge(next);
		}
	}
	
	@Override
	public void enterWhileStat(WhileStatContext ctx) {
		Node whileNode = blocks.get(ctx);
		Node next = nexts.get(ctx);
		if (next != null)
			whileNode.addEdge(next);
		
		StatContext bodyTree = ctx.stat();
		Node body = addNode(bodyTree, bodyTree.getText());
		whileNode.addEdge(body);
		
		blocks.put(bodyTree, body);
		nexts.put(bodyTree, whileNode);
	}
	
	@Override
	public void enterBlockStat(BlockStatContext ctx) {
		Node blockNode = blocks.get(ctx);
		Node next = nexts.get(ctx);
		
		List<StatContext> stats = ctx.stat();
		for (StatContext stat : stats) {
			blocks.put(stat, addNode(stat, stat.getText()));
		}
		
		for (int i = 0; i < stats.size() - 1; i++) {
			StatContext stat = stats.get(i);
			nexts.put(stat, blocks.get(stats.get(i + 1)));
		}
		
		blockNode.addEdge(blocks.get(stats.get(0)));
		StatContext stat = stats.get(stats.size() - 1);
		nexts.put(stat, next);
	}
	
	@Override
	public void enterPrintStat(PrintStatContext ctx) {
		Node assign = blocks.get(ctx);
		Node next = nexts.get(ctx);
		if (next != null)
			assign.addEdge(next);
	}

	@Override
	public void enterBreakStat(BreakStatContext ctx) {
		throw new IllegalArgumentException("Break not supported");
	}

	@Override
	public void enterContStat(ContStatContext ctx) {
		throw new IllegalArgumentException("Continue not supported");
	}

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
		TopDownCFGBuilder builder = new TopDownCFGBuilder();
		for (String filename : args) {
			File file = new File(filename);
			System.out.println(filename);
			System.out.println(builder.build(file));
		}
	}
}
