package pp.block1.cc.dfa;

import java.util.ArrayList;
import java.util.List;

public class MyScanner implements Scanner {

	@Override
	public List<String> scan(State dfa, String text) {
		State start = dfa;
		List<String> tokens = new ArrayList<>();
		
		while (!text.isEmpty()) {
			StringBuilder token = new StringBuilder();
			while (dfa.hasNext(text.charAt(0))) {
				token.append(text.charAt(0));
				dfa = dfa.getNext(text.charAt(0));
				text = text.substring(1);
				if (text.isEmpty()) {
					break;
				}
			}
			if (dfa.isAccepting()) {
				tokens.add(token.toString());
			} else {
				return null;
			}
			dfa = start;
		}
		return tokens;
	}

}
