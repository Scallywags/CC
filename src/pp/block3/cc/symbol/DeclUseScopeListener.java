package pp.block3.cc.symbol;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

public class DeclUseScopeListener extends DeclUseBaseListener {
	
	private final SymbolTable table = new MySymbolTable();
	private final List<String> errors = new ArrayList<>();
	
	@Override
	public void enterSeries(SeriesContext ctx) {
		table.openScope();
	}
	
	@Override
	public void exitSeries(SeriesContext ctx) {
		table.closeScope();
	}
	
	@Override
	public void enterDecl(DeclContext ctx) {
		table.add(ctx.ID().getText());
	}
	
	@Override
	public void enterUse(UseContext ctx) {
		TerminalNode id = ctx.ID();
		if (!table.contains(id.getText())) {
			errors.add("Error at line " + id.getSymbol().getLine() + " at position " + id.getSymbol().getCharPositionInLine()
					+ ". " + id.getText() + " was not declared.");
		}
	}
	
	public List<String> getErrors() {
		return new ArrayList<>(errors);
	}

}
