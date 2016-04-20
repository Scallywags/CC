package pp.block1.cc.dfa;

public class MyChecker implements Checker {

	@Override
	public boolean accepts(State start, String word) {
		if (word.isEmpty()) {
			return start.isAccepting();
		} else {
			State next = start.getNext(word.charAt(0));
			if (next == null) {
				return false;
			} else {
				return accepts(next, word.substring(1));
			}
		}
	}	

}
