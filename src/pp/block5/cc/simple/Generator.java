package pp.block5.cc.simple;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import pp.block5.cc.pascal.SimplePascalBaseVisitor;
import pp.block5.cc.pascal.SimplePascalParser.*;
import pp.iloc.Simulator;
import pp.iloc.model.Label;
import pp.iloc.model.Num;
import pp.iloc.model.Op;
import pp.iloc.model.OpCode;
import pp.iloc.model.Operand;
import pp.iloc.model.Program;
import pp.iloc.model.Reg;
import pp.iloc.model.Str;
/** Class to generate ILOC code for Simple Pascal. */
public class Generator extends SimplePascalBaseVisitor<Op> {
	/** The representation of the boolean value <code>false</code>. */
	public final static Num FALSE_VALUE = new Num(Simulator.FALSE);
	/** The representation of the boolean value <code>true</code>. */
	public final static Num TRUE_VALUE = new Num(Simulator.TRUE);

	/** The base register. */
	private Reg arp = new Reg("r_arp");
	/** The outcome of the checker phase. */
	private Result checkResult;
	/** Association of statement nodes to labels. */
	private ParseTreeProperty<Label> labels;
	/** The program being built. */
	private Program prog;
	/** Register count, used to generate fresh registers. */
	private int regCount;
	/** Association of expression and target nodes to registers. */
	private ParseTreeProperty<Reg> regs;

	private Map<String, Reg> vars;

	/** Generates ILOC code for a given parse tree,
	 * given a pre-computed checker result.
	 */
	public Program generate(ParseTree tree, Result checkResult) {
		vars = new HashMap<>();

		this.prog = new Program();
		this.checkResult = checkResult;
		this.regs = new ParseTreeProperty<>();
		this.labels = new ParseTreeProperty<>();
		this.regCount = 0;
		tree.accept(this);
		
		System.out.println("PROGRAM\n" + prog.prettyPrint());
		
		return this.prog;
	}
	
	//------------ VISIT METHODS -------------

	@Override
	public Op visitProgram(ProgramContext ctx) {
		return visit(ctx.body());
	}

	@Override
	public Op visitBody(BodyContext ctx) {
		if (ctx.decl().size() > 0) {
			Op declOp = visit(ctx.decl(0));
			for (int i = 1; i < ctx.decl().size(); i++) {
				visit(ctx.decl().get(i));
			}
			visit(ctx.block());
			return declOp;
		} else {
			return visit(ctx.block());
		}
	}

	@Override
	public Op visitVarDecl(VarDeclContext ctx) {
		for (int i = 1; i < ctx.var().size(); i++) {
			visit(ctx.var().get(i));
		}
		return visit(ctx.var(0));
	}

	@Override
	public Op visitVar(VarContext ctx) {
		for (int i = 1; i < ctx.ID().size(); i++) {
			Reg reg = reg(ctx.ID(i));
			vars.put(ctx.ID(i).getText(), reg);
			emit(OpCode.loadI, new Num(0), reg);
		}
		Reg reg = reg(ctx.ID(0));
		vars.put(ctx.ID(0).getText(), reg);
		return emit(OpCode.loadI, new Num(0), reg);
	}

	@Override
	public Op visitBlock(BlockContext ctx) {
		Op op = visit(ctx.stat(0));
		for (int i = 1; i < ctx.stat().size(); i++) {
			visit(ctx.stat(i));
		}
		return op;
	}
	
	//------------- STATEMENTS -------------

	@Override
	public Op visitAssStat(AssStatContext ctx) {
		visit(ctx.expr());
		Reg exprReg = regs.get(ctx.expr());
		Reg targReg = vars.get(ctx.target().getText()); //ID

		return emit(OpCode.i2i, exprReg, targReg);
	}

	@Override
	public Op visitIfStat(IfStatContext ctx) {
		ExprContext expr = ctx.expr();
		Op exprOp = visit(expr);
		Reg r_cmp = reg(expr);

		Op ifOp = emit(OpCode.cbr, r_cmp);

		StatContext then = ctx.stat(0);
		Op thenOp = visit(then);
		Label thenLabel = label(then);
		thenOp.setLabel(thenLabel);

		ifOp.getArgs().add(thenLabel);

		Label endifLabel = new Label("endif_" + label(ctx));
		if (ctx.stat().size() == 1) {
			//NO ELSE
			Op endif = emit(OpCode.nop);
			endif.setLabel(endifLabel);
			ifOp.getArgs().add(endifLabel);
		} else {
			//ELSE
			emit(OpCode.jumpI, endifLabel);		//after de body.

			StatContext elseStat = ctx.stat(1);
			Op elseOp = visit(elseStat);
			Label elseLabel = label(elseStat);
			elseOp.setLabel(elseLabel);
			ifOp.getArgs().add(elseLabel);

			Op endif = emit(OpCode.nop);
			endif.setLabel(endifLabel);
		}

		return exprOp;
	}

	@Override
	public Op visitWhileStat(WhileStatContext ctx) {
		Op nop = emit(OpCode.nop);
		
		ExprContext expr = ctx.expr();
		Op exprOp = visit(expr);
		Label whileLabel = label(ctx);
		exprOp.setLabel(whileLabel);
		
		Op compare = emit(OpCode.cbr, reg(expr));	//todo append body and endlabels
		
		StatContext stat = ctx.stat();
		Op body = visit(stat);
		Label bodyLabel = new Label("body_" + whileLabel);
		body.setLabel(bodyLabel);
		
		emit(OpCode.jumpI, whileLabel);
		
		Op endWhile = emit(OpCode.nop);
		Label endWhileLabel = new Label("endwhile_" + whileLabel);
		endWhile.setLabel(endWhileLabel);
		
		compare.getArgs().add(bodyLabel);
		compare.getArgs().add(endWhileLabel);
		
		return nop;
	}

	@Override
	public Op visitBlockStat(BlockStatContext ctx) {
		return visit(ctx.block());
	}

	@Override
	public Op visitInStat(InStatContext ctx) {
		return emit(OpCode.in, new Str(ctx.STR().getText()), vars.get(ctx.target().getText()));
	}

	@Override
	public Op visitOutStat(OutStatContext ctx) {
		visit(ctx.expr());
		return emit(OpCode.out, new Str(ctx.STR().getText()), regs.get(ctx.expr()));
	}

	//------------- EXPRESSIONS ------------

	@Override
	public Op visitPrfExpr(PrfExprContext ctx) {
		Reg reg = reg(ctx);

		visit(ctx.expr());
		Op op = visit(ctx.prfOp());
		
		Reg exprReg = regs.get(ctx.expr());
				
		op.getArgs().set(0, exprReg);
		op.getArgs().set(2, reg);
		
		return op;
	}

	@Override
	public Op visitMultExpr(MultExprContext ctx) {
		Reg reg = reg(ctx);
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		Op op = visit(ctx.multOp());
		op.getArgs().add(regs.get(ctx.expr(0)));
		op.getArgs().add(regs.get(ctx.expr(1)));
		op.getArgs().add(reg);		
		return op;
	}

	@Override
	public Op visitPlusExpr(PlusExprContext ctx) {
		Reg reg = reg(ctx);
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		Op op = visit(ctx.plusOp());
		op.getArgs().add(regs.get(ctx.expr(0)));
		op.getArgs().add(regs.get(ctx.expr(1)));
		op.getArgs().add(reg);
		return op;
	}

	@Override
	public Op visitCompExpr(CompExprContext ctx) {
		Reg reg = reg(ctx);
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		Op op = visit(ctx.compOp());
		op.getArgs().add(regs.get(ctx.expr(0)));
		op.getArgs().add(regs.get(ctx.expr(1)));
		op.getArgs().add(reg);
		return op;
	}

	@Override
	public Op visitBoolExpr(BoolExprContext ctx) {
		Reg reg = reg(ctx);
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		Op op = visit(ctx.boolOp());
		op.getArgs().add(regs.get(ctx.expr(0)));
		op.getArgs().add(regs.get(ctx.expr(1)));
		op.getArgs().add(reg);
		return op;
	}
	
	@Override
	public Op visitParExpr(ParExprContext ctx) {
		Op op = visit(ctx.expr());
		setReg(ctx, regs.get(ctx.expr()));
		return op;
	}
	
	@Override
	public Op visitIdExpr(IdExprContext ctx) {
		Reg reg = vars.get(ctx.getText());
		setReg(ctx, reg);
		
		return null; //TODO use loadI or LoadAO? probably not.
	}
	
	@Override
	public Op visitNumExpr(NumExprContext ctx) {
		Reg reg = reg(ctx);
		return emit(OpCode.loadI, new Num(Integer.parseInt(ctx.NUM().getText())), reg);
	}
	
	@Override
	public Op visitTrueExpr(TrueExprContext ctx) {
		Reg reg = reg(ctx);
		return emit(OpCode.loadI, TRUE_VALUE, reg);
	}
	
	@Override
	public Op visitFalseExpr(FalseExprContext ctx) {
		Reg reg = reg(ctx);
		return emit(OpCode.loadI, FALSE_VALUE, reg);
	}
	
	//------------- OPERATORS -------------
	
	@Override
	public Op visitPrfOp(PrfOpContext ctx) {		
		switch(ctx.getText().toUpperCase()) {
		case "-":
			return emit(OpCode.multI, null, new Num(-1), null);
		case "NOT":
			return emit(OpCode.xorI, null, TRUE_VALUE, null);
		}
		throw new IllegalArgumentException("PrefixOperator has text: \"" + ctx.getText() + "\"");
	}
	
	@Override
	public Op visitMultOp(MultOpContext ctx) {
		switch (ctx.getText()) {
		case "*":
			return emit(OpCode.mult);
		case "/":
			return emit(OpCode.div);
		}
		throw new IllegalArgumentException("MultOperator has text: \"" + ctx.getText() + "\"");
	}
	
	@Override
	public Op visitPlusOp(PlusOpContext ctx) {
		switch (ctx.getText()) {
		case "+":
			return emit(OpCode.add);
		case "-":
			return emit(OpCode.sub);
		}
		throw new IllegalArgumentException("PlusOperator has text: \"" + ctx.getText() + "\"");
	}
	
	@Override
	public Op visitBoolOp(BoolOpContext ctx) {
		switch (ctx.getText().toUpperCase()) {
		case "AND":
			return emit(OpCode.and);
		case "OR":
			return emit(OpCode.or);
		}
		throw new IllegalArgumentException("BoolOperator has text: \"" + ctx.getText() + "\"");
	}
	
	@Override
	public Op visitCompOp(CompOpContext ctx) {
		switch (ctx.getText()) {
		case "<=":
			return emit(OpCode.cmp_LE);
		case "<":
			return emit(OpCode.cmp_LT);
		case ">=":
			return emit(OpCode.cmp_GE);
		case ">":
			return emit(OpCode.cmp_GT);
		case "=":
			return emit(OpCode.cmp_EQ);
		case "<>":
			return emit(OpCode.cmp_NE);
		}
		throw new IllegalArgumentException("CompOperator has text: \"" + ctx.getText() + "\"");
	}

	/** Constructs an operation from the parameters 
	 * and adds it to the program under construction. */
	private Op emit(Label label, OpCode opCode, Operand... args) {
		//bypass ALL OF THE annoying checks
		Op instruction = new Op(label, OpCode.nop);
		instruction.getArgs().addAll(Arrays.asList(args));
		try {
			Field opCodeField = Op.class.getDeclaredField("opCode");
			opCodeField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(opCodeField, opCodeField.getModifiers() & ~Modifier.FINAL);

			opCodeField.set(instruction, opCode);
			
		} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
			//I hope this won't occur.
			e.printStackTrace();
		}


		this.prog.addInstr(instruction);
		return instruction;

		//		-- original --
		//		Op result = new Op(label, opCode, args);
		//		this.prog.addInstr(result);
		//		return result;
	}

	/** Constructs an operation from the parameters 
	 * and adds it to the program under construction. */
	private Op emit(OpCode opCode, Operand... args) {
		return emit(null, opCode, args);
	}

	/** 
	 * Looks up the label for a given parse tree node,
	 * creating it if none has been created before.
	 * The label is actually constructed from the entry node
	 * in the flow graph, as stored in the checker result.
	 */
	private Label label(ParserRuleContext node) {
		Label result = this.labels.get(node);
		if (result == null) {
			ParserRuleContext entry = this.checkResult.getEntry(node);
			result = createLabel(entry, "n");
			this.labels.put(node, result);
		}
		return result;
	}

	/** Creates a label for a given parse tree node and prefix. */
	private Label createLabel(ParserRuleContext node, String prefix) {
		Token token = node.getStart();
		int line = token.getLine();
		int column = token.getCharPositionInLine();
		String result = prefix + "_" + line + "_" + column;
		return new Label(result);
	}

	/** Retrieves the offset of a variable node from the checker result,
	 * wrapped in a {@link Num} operand. */
	private Num offset(ParseTree node) {
		return new Num(this.checkResult.getOffset(node));
	}

	/** Returns a register for a given parse tree node,
	 * creating a fresh register if there is none for that node. */
	private Reg reg(ParseTree node) {
		Reg result = this.regs.get(node);
		if (result == null) {
			result = new Reg("r_" + this.regCount);
			this.regs.put(node, result);
			this.regCount++;
		}
		return result;
	}

	/** Assigns a register to a given parse tree node. */
	private void setReg(ParseTree node, Reg reg) {
		this.regs.put(node, reg);
	}
}
