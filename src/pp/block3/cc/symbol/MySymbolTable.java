package pp.block3.cc.symbol;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class MySymbolTable implements SymbolTable {
	
	Stack<Set<String>> scopes = new Stack<>();

	@Override
	public void openScope() {
		scopes.add(new HashSet<>());
	}

	@Override
	public void closeScope() {
		scopes.pop();
	}

	@Override
	public boolean add(String id) {
		return scopes.peek().add(id);
	}

	@Override
	public boolean contains(String id) {
		return scopes.stream().anyMatch(set -> set.contains(id));
	}

}
