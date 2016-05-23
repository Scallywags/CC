package pp.block4.cc.iloc;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import pp.block4.cc.ErrorListener;
import pp.iloc.Simulator;
import pp.iloc.model.*;

/** Compiler from Calc.g4 to ILOC. */
public class CalcCompiler extends CalcBaseListener {
	/** Program under construction. */
	private Program prog;
	// Attribute maps and other fields
    private ParseTreeProperty<Reg> registers = new ParseTreeProperty<>();
    
    private Reg result = new Reg("r_result");

	/** Compiles a given expression string into an ILOC program. */
	public Program compile(String text) {
		Program result = null;
		ErrorListener listener = new ErrorListener();
		CharStream chars = new ANTLRInputStream(text);
		Lexer lexer = new CalcLexer(chars);
		lexer.removeErrorListeners();
		lexer.addErrorListener(listener);
		TokenStream tokens = new CommonTokenStream(lexer);
		CalcParser parser = new CalcParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(listener);
		ParseTree tree = parser.complete();
		if (listener.hasErrors()) {
			System.out.printf("Parse errors in %s:%n", text);
			for (String error : listener.getErrors()) {
				System.err.println(error);
			}
		} else {
			result = compile(tree);
		}
		return result;
	}

	/** Compiles a given Calc-parse tree into an ILOC program. */
	public Program compile(ParseTree tree) {
        prog = new Program();
        ParseTreeWalker tw = new ParseTreeWalker();
        tw.walk(this, tree);
        prog.addInstr(new Op(OpCode.out, new Str("output: "), result));        
		return prog;
	}
	
	@Override
	public void exitComplete(CalcParser.CompleteContext ctx) {
		result = registers.get(ctx.expr());
	}

	@Override
	public void enterNumber(CalcParser.NumberContext ctx) {
		int number = Integer.parseInt(ctx.NUMBER().getText());
        Reg reg = new Reg("r_" + number);
		prog.addInstr(new Op(OpCode.loadI, new Num(number), reg));
        registers.put(ctx, reg);
	}

    @Override
    public void exitTimes(CalcParser.TimesContext ctx) {
        Reg a = registers.get(ctx.expr(0));
        Reg b = registers.get(ctx.expr(1));
        Reg result = new Reg(a.getName() + " mult " + b.getName());
        prog.addInstr(new Op(OpCode.mult, a, b, result));
        registers.put(ctx, result);
    }

    @Override
    public void exitMinus(CalcParser.MinusContext ctx) {
        Reg a = registers.get(ctx.expr());
        Reg result = new Reg("minus " + a.getName());
        prog.addInstr(new Op(OpCode.multI,a, new Num(-1), result));
        registers.put(ctx, result);
    }

    @Override
    public void exitPlus(CalcParser.PlusContext ctx) {
        Reg a = registers.get(ctx.expr(0));
        Reg b = registers.get(ctx.expr(1));
        Reg result = new Reg(a.getName() + " plus " + b.getName());
        prog.addInstr(new Op(OpCode.add, a, b, result));
        registers.put(ctx, result);
    }

    @Override
    public void exitPar(CalcParser.ParContext ctx) {
        Reg a = registers.get(ctx.expr());
        registers.put(ctx, a);
    }

	/** Constructs an operation from the parameters 
	 * and adds it to the program under construction. */
	private void emit(OpCode opCode, Operand... args) {
		this.prog.addInstr(new Op(opCode, args));
	}

	/** Calls the compiler, and simulates and prints the compiled program. */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: [expr]+");
			return;
		}
		CalcCompiler compiler = new CalcCompiler();
		for (String expr : args) {
			System.out.println("Processing " + expr);
			Program prog = compiler.compile(expr);
			new Simulator(prog).run();
			System.out.println(prog.prettyPrint());
		}
	}
}
