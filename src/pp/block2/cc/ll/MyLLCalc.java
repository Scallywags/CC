package pp.block2.cc.ll;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Set;

import pp.block2.cc.NonTerm;
import pp.block2.cc.Symbol;
import pp.block2.cc.Term;

public class MyLLCalc implements LLCalc {

	private Map<Symbol, Set<Term>> firsts = new HashMap<>();
	private Map<NonTerm, Set<Term>> follows = new HashMap<>();
	private Map<Rule, Set<Term>> firstPlusses = new HashMap<>();

	private Grammar grammar;

	public MyLLCalc(Grammar g) {
		grammar = g;

		calculateFirsts();
		calculateFollows();
		calculateFirstPlusses();
	}

	private void calculateFirsts() {
		grammar.getTerminals().stream()
			.peek(terminal -> firsts.put(terminal, new HashSet<>()))
			.forEach(terminal -> first(terminal).add(terminal));
		firsts.put(Symbol.EMPTY, Collections.singleton(Symbol.EMPTY));
		firsts.put(Symbol.EOF, Collections.singleton(Symbol.EOF));
		grammar.getNonterminals().forEach(nonterminal -> firsts.put(nonterminal, new HashSet<>()));

		boolean setsAreStillChanging = true;
		while (setsAreStillChanging) {
			setsAreStillChanging = false;
			
			for (Rule rule : grammar.getRules()) {

				NonTerm bigA = rule.getLHS();
				List<Symbol> betas = rule.getRHS();

				Set<Term> rhs = first(betas.get(0));
				rhs.remove(Symbol.EMPTY);

				int i = 1;
				int k = betas.size() - 1;
				while (first(betas.get(i - 1)).contains(Symbol.EMPTY) && i  <= k ) {
					rhs.addAll(first(betas.get(i)).stream().filter(term -> term != Symbol.EMPTY).collect(Collectors.toSet()));
					i = i + 1;
				}

				if (i == k && first(betas.get(k)).contains(Symbol.EMPTY)) {
					rhs.add(Symbol.EMPTY);
				}

				setsAreStillChanging |= first(bigA).addAll(rhs);
			} //end for each rule
			
		} //end while sets are still changing
		
	}

	private void calculateFollows() {
		grammar.getNonterminals().forEach(nonterminal -> follows.put(nonterminal, new HashSet<>()));
		grammar.getRules(grammar.getStart()).stream().map(rule -> rule.getLHS()).forEach(s -> follow(s).add(Symbol.EOF));
		
		boolean setsAreStillChanging = true;
		while (setsAreStillChanging) {
			setsAreStillChanging = false;
			
			for (Rule p : grammar.getRules()) {
				NonTerm bigA = p.getLHS();
				List<Symbol> betas = p.getRHS();
				
				Set<Term> trailer = follow(bigA);
				
				for (int i = betas.size() - 1; i >= 0; i--) {
					Symbol bi = betas.get(i);
					if (bi instanceof NonTerm) {
						NonTerm biNT = (NonTerm) bi;
						setsAreStillChanging |= follow(biNT).addAll(trailer);
						if (first(biNT).contains(Symbol.EMPTY)) {
							trailer.addAll(first(biNT).stream().filter(term -> term != Symbol.EMPTY).collect(Collectors.toSet()));
						} else {
							trailer = first(biNT);
						}
					} else {
						trailer = first(bi);
					}					
				}
			}
		}
	}

	private void calculateFirstPlusses() {
		for (Rule rule : grammar.getRules()) {
			Set<Term> firsts = first(rule.getRHS().get(0));
			if (firsts.contains(Symbol.EMPTY)) {
				Set<Term> follows = follow(rule.getLHS());
				firsts.addAll(follows);
			}
			firstPlusses.put(rule, firsts);
		}
	}

	@Override
	public Map<Symbol, Set<Term>> getFirst() {
		return firsts;
	}

	public Set<Term> first(Symbol symbol) {
		return getFirst().get(symbol);
	}

	@Override
	public Map<NonTerm, Set<Term>> getFollow() {
		return follows;
	}

	public Set<Term> follow(NonTerm nonTerm) {
		return getFollow().get(nonTerm);
	}

	@Override
	public Map<Rule, Set<Term>> getFirstp() {
		return firstPlusses;
	}

	public Set<Term> firstPlus(Rule rule) {
		return getFirstp().get(rule);
	}

	@Override
	public boolean isLL1() {
		for (NonTerm nt : grammar.getNonterminals()) {
			List<Rule> rules = grammar.getRules(nt);
			Set<Term> allFirstPlussesForThisNT = new HashSet<>();
			for (Rule rule : rules) {
				for (Term term : firstPlus(rule)) {
					if (!allFirstPlussesForThisNT.add(term)) {
						//overlap!
						return false;
					}
				}
			}
		}
		return true;
	}

}
