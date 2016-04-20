package pp.block1.cc.dfa;

public class MyChecker implements Checker {

	@Override
	public boolean accepts(State start, String word) {
		if (word.isEmpty()) {
			return start.isAccepting();
		} else {
			char firstChar = word.charAt(0);
			if (!start.hasNext(firstChar)) {
				return false;
			} else {
				return accepts(start.getNext(firstChar), word.substring(1));
			}
		}
	}	

}
