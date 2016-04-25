package pp.block2.cc.ll;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import pp.block2.cc.NonTerm;
import pp.block2.cc.Symbol;
import pp.block2.cc.Term;

public class MyLLCalc implements LLCalc {
	
	private Map<Symbol, Set<Term>> firsts = new HashMap<>();
	private Map<NonTerm, Set<Term>> follows = new HashMap<>();
	private Map<Rule, Set<Term>> firstPlusses = new HashMap<>();
	
	private Grammar grammar;
	
	List<Rule> startingRules;
	
	public MyLLCalc(Grammar g) {
		grammar = g;
		startingRules = grammar.getRules(grammar.getStart());
		
		calculateFirsts();
		calculateFollows();
		calculateFirstPlusses();
	}
	
	private void calculateFirsts() {
		grammar.getTerminals().forEach(terminal -> firsts.put(terminal, Collections.singleton(terminal)));	
		firsts.put(Symbol.EMPTY, Collections.singleton(Symbol.EMPTY));
		firsts.put(Symbol.EOF, Collections.singleton(Symbol.EOF));
		
		for (Rule rule : grammar.getRules()) {
			NonTerm nonterm = rule.getLHS();
			firsts.put(nonterm, findFirst(nonterm));		
		}
	}
	
	private Set<Term> findFirst(NonTerm nt) {
		List<Rule> rules = grammar.getRules(nt);
		Set<Term> terms = new HashSet<>();
		for (Rule rule : rules) {
			terms.addAll(findRuleFirst(rule));
		}		
		return terms;
	}
	
	private void calculateFollows() {
		grammar.getNonterminals().forEach(nt -> findFollow(nt));
	}
	
	private Set<Term> findFollow(NonTerm nt) {
		Set<Term> follows = new HashSet<>();
		
		for (Rule rule : grammar.getRules()) {
			List<Symbol> rhs = rule.getRHS();
			int index = rhs.indexOf(nt);
			if (index == rhs.size() - 1) {
				//if nt is the last element in any rule.
				boolean isStartingRule = startingRules.contains(rule);
				if (isStartingRule) {
					follows.add(Symbol.EOF);
				} else {
					// add follows from surrounding rules.
					for (Rule surrounding : grammar.getRules()) {
						if (rule.equals(surrounding)) {
							continue;
						}
						List<Symbol> surRhs = surrounding.getRHS();
						int surIndex = surRhs.indexOf(nt);
						if (surIndex != -1) {
							//if the surrounding rule actually has this nt in its right hand side.
							if (surIndex == surRhs.size() - 1) {
								//if the nt actually was the last element in the other rule
								//add as follows all the follows from that other rule
								follows.addAll(findFollow(surrounding.getLHS()));
							} else {
								//if the nt is in the other rule, but not as the last element.
								//add as follows all the terminals of the next symbol of that other rule.
								follows.addAll(first(surRhs.get(index + 1)));
							}
						}						
					}					
				}				
			} else if (index != -1) {
				//if the nt is in the rule, but not in the last posiition
				//add as follows all the terminals of the next symbol
				follows.addAll(first(rhs.get(index + 1)));
			}
		}
		
		return follows;
	}

	private void calculateFirstPlusses() {
		for (Rule rule : grammar.getRules()) {
			Set<Term> firsts = findRuleFirst(rule);
			if (firsts.contains(Symbol.EMPTY)) {
				Set<Term> follows = follow(rule.getLHS());
				firsts.addAll(follows);
			}
			firstPlusses.put(rule, firsts);
		}
	}
	
	private Set<Term> findRuleFirst(Rule rule) {
		Set<Term> terms = new HashSet<>();		
		Symbol first = rule.getRHS().get(0);
		if (rule.getRHS().get(0) instanceof Term) {
			terms.add((Term) first); 
		} else {
			terms.addAll(findFirst((NonTerm) first));
		}		
		return terms;
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
