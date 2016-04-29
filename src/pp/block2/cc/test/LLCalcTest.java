package pp.block2.cc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import pp.block2.cc.NonTerm;
import pp.block2.cc.Symbol;
import pp.block2.cc.Term;
import pp.block2.cc.ll.Grammar;
import pp.block2.cc.ll.Grammars;
import pp.block2.cc.ll.If;
import pp.block2.cc.ll.LLCalc;
import pp.block2.cc.ll.LRQ;
import pp.block2.cc.ll.MyLLCalc;
import pp.block2.cc.ll.Rule;
import pp.block2.cc.ll.Sentence;
import pp.block2.cc.ll.Stat;

public class LLCalcTest {
	/** Tests the LL-calculator for the Sentence grammar. */
	@Test
	public void testSentenceOrig() {
		Grammar g = Grammars.makeSentence();
		// Without the last (recursive) rule, the grammar is LL-1
		assertTrue(createCalc(g).isLL1());
	}

	@Test
	public void testSentenceExtended() {
		Grammar g = Grammars.makeSentence();
		// Without the last (recursive) rule, the grammar is LL-1
		assertTrue(createCalc(g).isLL1());
		// Now add the last rule, causing the grammar to fail
		// Define the non-terminals
		NonTerm subj = g.getNonterminal("Subject");
		NonTerm obj = g.getNonterminal("Object");
		NonTerm sent = g.getNonterminal("Sentence");
		NonTerm mod = g.getNonterminal("Modifier");
		g.addRule(mod, mod, mod);
		// Define the terminals
		Term adj = g.getTerminal(Sentence.ADJECTIVE);
		Term noun = g.getTerminal(Sentence.NOUN);
		Term verb = g.getTerminal(Sentence.VERB);
		Term end = g.getTerminal(Sentence.ENDMARK);
		LLCalc calc = createCalc(g);
		// FIRST sets
		Map<Symbol, Set<Term>> first = calc.getFirst();
		assertEquals(set(adj, noun), first.get(sent));
		assertEquals(set(adj, noun), first.get(subj));
		assertEquals(set(adj, noun), first.get(obj));
		assertEquals(set(adj), first.get(mod));
		// FOLLOW sets
		Map<NonTerm, Set<Term>> follow = calc.getFollow();
		assertEquals(set(Symbol.EOF), follow.get(sent));
		assertEquals(set(verb), follow.get(subj));
		assertEquals(set(end), follow.get(obj));
		assertEquals(set(noun, adj), follow.get(mod));
		// FIRST+ sets: test per rule
		Map<Rule, Set<Term>> firstp = calc.getFirstp();
		List<Rule> subjRules = g.getRules(subj);
		assertEquals(set(noun), firstp.get(subjRules.get(0)));
		assertEquals(set(adj), firstp.get(subjRules.get(1)));
		// is-LL1-test
		assertFalse(calc.isLL1());
	}
	
	@Test
	public void testStat() {
		Grammar g = Grammars.makeStat();
		LLCalc calc = createCalc(g);
		
		NonTerm stat = g.getNonterminal("Stat");
		NonTerm elsePart = g.getNonterminal("ElsePart");
		
		Term assign = g.getTerminal(Stat.ASSIGN);
		Term iif = g.getTerminal(Stat.IF);
		Term expr = g.getTerminal(Stat.EXPR);
		Term then = g.getTerminal(Stat.THEN);
		Term eelse = g.getTerminal(Stat.ELSE);
		Term epsilon = g.getTerminal(Stat.EMPTY);
		
		//test first sets
		Map<Symbol, Set<Term>> first = calc.getFirst();
		assertEquals(set(assign, iif), first.get(stat));
		assertEquals(set(eelse, epsilon), first.get(elsePart));
		
		//test follow sets
		Map<NonTerm, Set<Term>> follow = calc.getFollow();
		assertEquals(set(eelse, Symbol.EOF), follow.get(stat));
		assertEquals(set(eelse, Symbol.EOF), follow.get(elsePart));
		
		//test first plus
		Map<Rule, Set<Term>> firstP = calc.getFirstp();
		List<Rule> statRules = g.getRules(stat);
		assertEquals(set(assign), firstP.get(statRules.get(0)));
		assertEquals(set(iif), firstP.get(statRules.get(1)));
		
		List<Rule> elsePartRules = g.getRules(elsePart);
		assertEquals(set(eelse), firstP.get(elsePartRules.get(0)));
		assertEquals(set(epsilon, Symbol.EOF, eelse), firstP.get(elsePartRules.get(1)));
		
		assertFalse(calc.isLL1());
	}
	
	@Test
	public void testLRQ() {
		Grammar g = Grammars.makeLRQ();
		LLCalc calc = createCalc(g);
		
		NonTerm l = g.getNonterminal("L");
		NonTerm r = g.getNonterminal("R");
		NonTerm q = g.getNonterminal("Q");
		NonTerm p = g.getNonterminal("P");
		NonTerm s = g.getNonterminal("S");
		
		Term a = g.getTerminal(LRQ.A);
		Term b = g.getTerminal(LRQ.B);
		Term c = g.getTerminal(LRQ.C);
		Term empty = g.getTerminal(LRQ.EMPTY);
		
		//first sets
		Map<Symbol, Set<Term>> first = calc.getFirst();
		assertEquals(set(a, b, c), first.get(l));
		assertEquals(set(b), first.get(q));
		assertEquals(set(b, c), first.get(p));
		assertEquals(set(a, c), first.get(r));
		assertEquals(set(b, empty), first.get(s));
		
		//follow sets
		Map<NonTerm, Set<Term>> follow = calc.getFollow();
		assertEquals(set(Symbol.EOF), follow.get(l));
		assertEquals(set(b), follow.get(q));
		assertEquals(set(b), follow.get(p));
		assertEquals(set(a), follow.get(r));
		assertEquals(set(a), follow.get(s));
		
		//first plus sets
		Map<Rule, Set<Term>> firstp = calc.getFirstp();
		//l rules
		List<Rule> lRules = g.getRules(l);
		assertEquals(set(a, c), firstp.get(lRules.get(0)));
		assertEquals(set(b), firstp.get(lRules.get(1)));
		//q rules
		List<Rule> qRules = g.getRules(q);
		assertEquals(set(b), firstp.get(qRules.get(0)));
		//p rules
		List<Rule> pRules = g.getRules(p);
		assertEquals(set(b), firstp.get(pRules.get(0)));
		assertEquals(set(c), firstp.get(pRules.get(1)));
		//r rules
		List<Rule> rRules = g.getRules(r);
		assertEquals(set(a), firstp.get(rRules.get(0)));
		assertEquals(set(c), firstp.get(rRules.get(1)));
		//s rules
		List<Rule> sRules = g.getRules(s);
		assertEquals(set(b), firstp.get(sRules.get(0)));
		assertEquals(set(a, empty), firstp.get(sRules.get(1)));
		
		assertTrue(calc.isLL1());
	}

	/** Creates an LL1-calculator for a given grammar. */
	private LLCalc createCalc(Grammar g) {
		return new MyLLCalc(g); // your implementation of LLCalc (Ex. 2-CC.5)
	}

	@SuppressWarnings("unchecked")
	private <T> Set<T> set(T... elements) {
		return new HashSet<>(Arrays.asList(elements));
	}
}
