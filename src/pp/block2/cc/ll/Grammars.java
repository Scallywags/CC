/**
 * 
 */
package pp.block2.cc.ll;

import pp.block2.cc.NonTerm;
import pp.block2.cc.SymbolFactory;
import pp.block2.cc.Term;

/**
 * Class containing some example grammars.
 * @author Arend Rensink
 *
 */
public class Grammars {
	/** Returns a grammar for simple English sentences. */
	public static Grammar makeSentence() {
		// Define the non-terminals
		NonTerm sent = new NonTerm("Sentence");
		NonTerm subj = new NonTerm("Subject");
		NonTerm obj = new NonTerm("Object");
		NonTerm mod = new NonTerm("Modifier");
		// Define the terminals, using the Sentence.g4 lexer grammar
		SymbolFactory fact = new SymbolFactory(Sentence.class);
		Term noun = fact.getTerminal(Sentence.NOUN);
		Term verb = fact.getTerminal(Sentence.VERB);
		Term adj = fact.getTerminal(Sentence.ADJECTIVE);
		Term end = fact.getTerminal(Sentence.ENDMARK);
		// Build the context free grammar
		Grammar g = new Grammar(sent);
		g.addRule(sent, subj, verb, obj, end);
		g.addRule(subj, noun);
		g.addRule(subj, mod, subj);
		g.addRule(obj, noun);
		g.addRule(obj, mod, obj);
		g.addRule(mod, adj);
		return g;
	}
	
	public static Grammar makeStat() {
		NonTerm stat = new NonTerm("Stat");
		NonTerm elsepart = new NonTerm("ElsePart");
		
		SymbolFactory fact = new SymbolFactory(Stat.class);
		Term assign = fact.getTerminal(Stat.ASSIGN);
		Term iif = fact.getTerminal(Stat.IF);
		Term expr = fact.getTerminal(Stat.EXPR);
		Term then = fact.getTerminal(Stat.THEN);
		Term eelse = fact.getTerminal(Stat.ELSE);
		Term empty = fact.getTerminal(Stat.EMPTY);
		
		Grammar g = new Grammar(stat);
		g.addRule(stat, assign);
		g.addRule(stat, iif, expr, then, stat, elsepart);
		g.addRule(elsepart, eelse, stat);
		g.addRule(elsepart, empty);
		return g;
	}
	
	public static Grammar makeLRQ() {
		NonTerm l = new NonTerm("L");
		NonTerm r = new NonTerm("R");
		NonTerm q = new NonTerm("Q");
		NonTerm p = new NonTerm("P");
		NonTerm s = new NonTerm("S");
		
		SymbolFactory fact = new SymbolFactory(LRQ.class);
		Term a = fact.getTerminal(LRQ.A);
		Term b = fact.getTerminal(LRQ.B);
		Term c = fact.getTerminal(LRQ.C);
		Term empty = fact.getTerminal(LRQ.EMPTY);
		
		Grammar g = new Grammar(l);
		g.addRule(l, r, a);
		g.addRule(l, q, b, a);
		g.addRule(q, b, p);
		g.addRule(p, b, c);
		g.addRule(p, c);
		g.addRule(r, a, b, a, s);
		g.addRule(r, c, a, b, a, s);
		g.addRule(s, b, c, s);
		g.addRule(s, empty);
		return g;
	}
}
