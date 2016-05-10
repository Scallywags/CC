package pp.block3.cc.symbol;

import java.util.HashMap;
import java.util.List;
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
		scopes.peek().put(id, )
	}

	@Override
	public boolean contains(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}
